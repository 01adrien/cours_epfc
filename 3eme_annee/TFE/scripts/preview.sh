#!/bin/bash
set -e

# Assemble les parties du TFE en un PDF "aperçu Markdown" (style GitHub/VSCode)
# plutôt qu'un document académique mis en page — pratique pour relire vite
# sans attendre un rendu LaTeX complet. Les diagrammes .puml référencés sont
# tout de même rendus en image (même filtre que build.sh), pour un aperçu fidèle.
#
# Usage : ./scripts/build_preview.sh
# Prérequis : pandoc, weasyprint, python3, java, pandocfilters
#   pip install weasyprint pandocfilters --break-system-packages
#
# (wkhtmltopdf n'est plus disponible dans les dépôts Debian/Ubuntu récents ;
# weasyprint est utilisé à la place, appelé directement par pandoc comme
# moteur PDF — pas d'étape HTML intermédiaire à gérer soi-même.)

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
CSS="$SCRIPT_DIR/preview.css"
IMG_FILTER="$SCRIPT_DIR/plantuml_filter.py"
OUTPUT="$ROOT_DIR/build/tfe_preview.pdf"

export PLANTUML_JAR="$ROOT_DIR/plantuml.jar"

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

PUML_SEARCH_DIRS_STR=$(IFS=:; echo "${PART_DIRS[*]}")
export PUML_SEARCH_DIRS="$PUML_SEARCH_DIRS_STR"

echo "==> Génération du PDF (weasyprint, CSS façon aperçu Markdown)"
pandoc "${MD_FILES[@]}" \
    -o "$OUTPUT" \
    --pdf-engine=weasyprint \
    --toc --toc-depth=2 \
    --css="$CSS" \
    --filter="$IMG_FILTER" \
    --metadata title="TFE" \
    -V lang=fr

echo "==> Terminé : $OUTPUT"
