#!/usr/bin/env python3
"""
Filtre pandoc : convertit toute image Markdown pointant vers un .puml
en diagramme rendu (PNG), avant que pandoc ne produise le PDF.

Usage dans le Markdown :
    ![Légende du diagramme](assets/mcd.puml)

Le filtre le remplace automatiquement par le PNG rendu (généré à côté
du .puml, dans le même dossier), sans que le PDF final ne montre du
code PlantUML brut ni un lien mort.

Ce script est appelé automatiquement par pandoc via --filter, pas besoin
de l'exécuter à la main.
"""

import os
import subprocess
import sys

from pandocfilters import toJSONFilter, Image

PLANTUML_JAR = os.environ.get(
    "PLANTUML_JAR",
    os.path.join(os.path.dirname(os.path.abspath(__file__)), "plantuml.jar"),
)

# Dossiers dans lesquels chercher un .puml si le chemin écrit dans le
# Markdown ne se résout pas tel quel depuis le répertoire courant.
# Renseigné par build.sh (un dossier de partie par entrée, séparés par ':').
SEARCH_DIRS = [d for d in os.environ.get("PUML_SEARCH_DIRS", "").split(":") if d]


def resolve_puml_path(url):
    """Retrouve le chemin réel d'un .puml, qu'il soit écrit relatif au
    répertoire courant (cwd) ou relatif au dossier de la partie qui le
    référence (résolu via PUML_SEARCH_DIRS)."""
    candidate = os.path.normpath(url)
    if os.path.exists(candidate):
        return candidate

    for search_dir in SEARCH_DIRS:
        candidate = os.path.normpath(os.path.join(search_dir, url))
        if os.path.exists(candidate):
            return candidate

    return None


def render_puml(puml_path):
    """Rend un .puml en .png à côté du fichier source, si pas déjà fait
    ou si le .puml a été modifié depuis le dernier rendu.

    Utilise le mode -pipe de PlantUML pour forcer le nom du PNG de sortie
    à correspondre au nom du fichier .puml, plutôt que de dépendre du nom
    donné après @startuml à l'intérieur du fichier (qui peut différer).

    Écrit d'abord dans un fichier temporaire, et ne le déplace vers le
    nom final qu'en cas de succès : en cas d'échec (jar introuvable,
    erreur de syntaxe PlantUML...), aucun PNG vide/corrompu n'est laissé
    sur le disque — sinon un prochain build le prendrait à tort pour un
    rendu déjà à jour et l'embarquerait tel quel, vide, dans le PDF."""
    png_path = os.path.splitext(puml_path)[0] + ".png"

    # Un fichier existant mais vide (0 octet) est nécessairement le reliquat
    # d'un échec précédent : on ne lui fait jamais confiance, même si sa
    # date de modification est postérieure au .puml source.
    if (
        os.path.exists(png_path)
        and os.path.getsize(png_path) > 0
        and os.path.getmtime(png_path) >= os.path.getmtime(puml_path)
    ):
        return png_path  # déjà à jour, pas besoin de re-render

    tmp_png_path = png_path + ".tmp"
    try:
        with open(puml_path, "rb") as puml_in, open(tmp_png_path, "wb") as png_out:
            result = subprocess.run(
                ["java", "-jar", PLANTUML_JAR, "-tpng", "-pipe"],
                stdin=puml_in,
                stdout=png_out,
                stderr=subprocess.PIPE,
            )

        if result.returncode != 0 or os.path.getsize(tmp_png_path) == 0:
            print(
                f"[plantuml-filter] Erreur en rendant {puml_path} :\n{result.stderr.decode()}",
                file=sys.stderr,
            )
            return puml_path  # on laisse tel quel plutôt que de planter tout le build

        os.replace(tmp_png_path, png_path)  # déplacement atomique, seulement si succès
        return png_path
    finally:
        if os.path.exists(tmp_png_path):
            os.remove(tmp_png_path)


def action(key, value, format_, meta):
    if key == "Image":
        attrs, caption, (url, title) = value
        if url.lower().endswith(".puml"):
            puml_path = resolve_puml_path(url)
            if puml_path:
                png_path = render_puml(puml_path)
                return Image(attrs, caption, [png_path, title])
            else:
                print(f"[plantuml-filter] Fichier introuvable : {url} (cherché dans le cwd et {SEARCH_DIRS})", file=sys.stderr)


if __name__ == "__main__":
    toJSONFilter(action)