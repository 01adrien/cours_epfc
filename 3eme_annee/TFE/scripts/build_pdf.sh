#!/bin/bash
set -e

# Assemble les parties du TFE (dossiers 01-, 02-, 03-, 04-...) en un seul PDF
# avec sommaire et pagination automatiques. Toute image référencée dans un
# .md et pointant vers un .puml (ex: ![Légende](assets/mcd.puml)) est rendue
# automatiquement en PNG et insérée dans le PDF, via plantuml_filter.py.
# Les tableaux sont rendus avec un quadrillage complet (lignes + colonnes),
# via table_grid_filter.lua, plutôt que le style épuré par défaut de pandoc.
# Chaque partie (dossier) démarre sur une nouvelle page.
#
# Attendu : ce script vit dans scripts/, à un niveau sous la racine du projet
# (qui contient les dossiers 01-, 02-, ... et plantuml.jar).
#
# Usage : ./scripts/build.sh   (ou via le Makefile à la racine : make pdf)
# Prérequis : pandoc, texlive-xetex, python3, java, pandocfilters
#   pip install pandocfilters --break-system-packages

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
OUTPUT="$ROOT_DIR/tfe.pdf"
IMG_FILTER="$SCRIPT_DIR/plantuml_filter.py"
TABLE_FILTER="$SCRIPT_DIR/table_grid_filter.lua"
COMBINED_MD="$ROOT_DIR/.combined_tmp.md"
HEADER_TEX="$SCRIPT_DIR/.table_preamble.tex"

export PLANTUML_JAR="$ROOT_DIR/plantuml.jar"

# Paquets LaTeX requis : longtable (tableaux en quadrillage complet) et
# tcolorbox (badge gris arrondi pour le code inline, plutôt que le rendu
# \texttt brut par défaut de LaTeX, plus vieillot).
cat > "$HEADER_TEX" << 'PREAMBLE_EOF'
\usepackage{longtable}
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
PREAMBLE_EOF

echo "==> Collecte des fichiers Markdown, dans l'ordre des dossiers"
MD_FILES=()
PART_DIRS=()
for dir in "$ROOT_DIR"/[0-9][0-9]-*/; do
    md=$(find "$dir" -maxdepth 1 -name "*.md" | head -n 1)
    if [ -n "$md" ]; then
        echo "    $md"
        MD_FILES+=("$md")
        PART_DIRS+=("${dir%/}")
    fi
done

if [ ${#MD_FILES[@]} -eq 0 ]; then
    echo "Aucun fichier Markdown trouvé — vérifier la structure des dossiers."
    exit 1
fi

# Liste des dossiers de parties, pour que le filtre puisse résoudre un
# chemin d'image écrit relatif au dossier de la partie (ex: assets/x.puml
# dans 04-realisation/partie-4.md), peu importe d'où pandoc est lancé.
PUML_SEARCH_DIRS_STR=$(IFS=:; echo "${PART_DIRS[*]}")
export PUML_SEARCH_DIRS="$PUML_SEARCH_DIRS_STR"

echo "==> Concaténation avec saut de page entre chaque partie"
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
pandoc "$COMBINED_MD" \
    -o "$OUTPUT" \
    --toc --toc-depth=2 \
    --pdf-engine=xelatex \
    --filter="$IMG_FILTER" \
    --lua-filter="$TABLE_FILTER" \
    --include-in-header="$HEADER_TEX" \
    -V lang=fr \
    -V geometry:margin=2.5cm \
    -V fontsize=11pt \
    --highlight-style=tango \
    --standalone

rm -f "$COMBINED_MD" "$HEADER_TEX"
echo "==> Terminé : $OUTPUT"
