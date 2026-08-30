#!/bin/bash
set -e

# Assemble les parties du TFE (dossiers 01-, 02-, 03-, 04-...) en un seul PDF
# avec sommaire et pagination automatiques — ou une seule partie si demandé.
# Toute image référencée dans un .md et pointant vers un .puml (ex:
# ![Légende](assets/mcd.puml)) est rendue automatiquement en PNG et insérée
# dans le PDF, via plantuml_filter.py. Les tableaux sont rendus avec un
# quadrillage complet (lignes + colonnes), via table_grid_filter.lua.
#
# Usage :
#   ./scripts/build.sh                -> tout le document -> tfe.pdf
#   ./scripts/build.sh 04-realisation -> une seule partie -> 04-realisation.pdf
#   (ou via le Makefile : make pdf / make 04-realisation)
#
# Style du code inline figé sur "modern" (badge gris arrondi) : pas de choix,
# volontairement retiré pour simplifier.
#
# Attendu : ce script vit dans scripts/, à un niveau sous la racine du projet
# (qui contient les dossiers 01-, 02-, ... et plantuml.jar).
#
# Prérequis : pandoc, texlive-xetex, texlive-latex-extra (pour tcolorbox,
# seulement nécessaire en mode "modern"), python3, java, pandocfilters
#   pip install pandocfilters --break-system-packages

STYLE="modern"
PART="${1:-}"

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
IMG_FILTER="$SCRIPT_DIR/plantuml_filter.py"
TABLE_FILTER="$SCRIPT_DIR/table_grid_filter.lua"
ANNEXE_FILTER="$SCRIPT_DIR/annexe_filter.lua"
COMBINED_MD="$ROOT_DIR/.combined_tmp.md"
HEADER_TEX="$SCRIPT_DIR/.table_preamble.tex"

if [ -n "$PART" ]; then
    if [ ! -d "$ROOT_DIR/$PART" ]; then
        echo "Dossier de partie introuvable : $ROOT_DIR/$PART"
        exit 1
    fi
    OUTPUT="$ROOT_DIR/build/$PART.pdf"
else
    OUTPUT="$ROOT_DIR/build/tfe.pdf"
fi

export PLANTUML_JAR="$ROOT_DIR/plantuml.jar"

echo "==> Style : $STYLE"
[ -n "$PART" ] && echo "==> Partie unique : $PART"

# Paquet requis dans tous les cas : longtable (tableaux en quadrillage complet)
cat > "$HEADER_TEX" << 'FLOAT_EOF'
\usepackage{longtable}
\usepackage{float}
\floatplacement{figure}{H}
FLOAT_EOF

if [ "$STYLE" = "modern" ]; then
    # Badge gris arrondi pour le code inline, plutôt que le rendu \texttt
    # brut par défaut de LaTeX (plus vieillot, façon papier académique).
    # + encadré bleu discret pour les renvois vers une annexe (::: annexe ::: ).
    cat >> "$HEADER_TEX" << 'PREAMBLE_EOF'
\usepackage{xcolor}
\usepackage[most]{tcolorbox}

\newtcbox{\inlinecode}{
    on line,
    boxrule=0pt,
    colback=black!6,
    colframe=black!6,
    top=1pt, bottom=1pt, left=3pt, right=3pt,
    arc=3pt,
    boxsep=0pt,
    fontupper=\ttfamily\small
}

\renewcommand{\texttt}[1]{\inlinecode{#1}}

\newtcolorbox{annexebox}{
    enhanced,
    colback=blue!3,
    frame hidden,
    borderline west={2.5pt}{0pt}{blue!40!black},
    left=14pt, right=10pt, top=7pt, bottom=7pt,
    boxrule=0pt,
    arc=0pt,
    before skip=10pt, after skip=10pt,
    fontupper=\small\itshape,
}
PREAMBLE_EOF
fi
# En mode "classic", on ne touche à rien de plus : \texttt garde son
# rendu LaTeX standard.

echo "==> Collecte des fichiers Markdown"
MD_FILES=()
PART_DIRS=()
if [ -n "$PART" ]; then
    md=$(find "$ROOT_DIR/$PART" -maxdepth 1 -name "*.md" | head -n 1)
    if [ -z "$md" ]; then
        echo "Aucun fichier Markdown trouvé dans $PART."
        exit 1
    fi
    echo "    $md"
    MD_FILES+=("$md")
    PART_DIRS+=("$ROOT_DIR/$PART")
else
    for dir in "$ROOT_DIR"/[0-9][0-9]-*/; do
        md=$(find "$dir" -maxdepth 1 -name "*.md" | head -n 1)
        if [ -n "$md" ]; then
            echo "    $md"
            MD_FILES+=("$md")
            PART_DIRS+=("${dir%/}")
        fi
    done
fi

if [ ${#MD_FILES[@]} -eq 0 ]; then
    echo "Aucun fichier Markdown trouvé — vérifier la structure des dossiers."
    exit 1
fi

# Liste des dossiers de parties, pour que le filtre puisse résoudre un
# chemin d'image écrit relatif au dossier de la partie (ex: assets/x.puml
# dans 04-realisation/partie-4.md), peu importe d'où pandoc est lancé.
PUML_SEARCH_DIRS_STR=$(IFS=:; echo "${PART_DIRS[*]}")
export PUML_SEARCH_DIRS="$PUML_SEARCH_DIRS_STR"

echo "==> Concaténation"
> "$COMBINED_MD"
for i in "${!MD_FILES[@]}"; do
    if [ "$i" -gt 0 ]; then
        # Saut de page LaTeX brut, reconnu par pandoc via un bloc raw
        printf '\n```{=latex}\n\\newpage\n```\n\n' >> "$COMBINED_MD"
    fi
    cat "${MD_FILES[$i]}" >> "$COMBINED_MD"
    printf '\n' >> "$COMBINED_MD"
done

echo "==> Génération du PDF"
# Sommaire uniquement pour le document complet — inutile sur une seule partie
TOC_FLAGS=()
if [ -z "$PART" ]; then
    TOC_FLAGS=(--toc --toc-depth=2)
fi

pandoc "$COMBINED_MD" \
    -o "$OUTPUT" \
    "${TOC_FLAGS[@]}" \
    --pdf-engine=xelatex \
    --filter="$IMG_FILTER" \
    --lua-filter="$TABLE_FILTER" \
    --lua-filter="$ANNEXE_FILTER" \
    --include-in-header="$HEADER_TEX" \
    -V lang=fr \
    -V geometry:margin=2.5cm \
    -V fontsize=11pt \
    --highlight-style=tango \
    --standalone

rm -f "$COMBINED_MD" "$HEADER_TEX"
echo "==> Terminé : $OUTPUT"
