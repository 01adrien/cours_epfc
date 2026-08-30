#!/bin/bash
set -e

# Assemble les parties du TFE (dossiers 01-, 02-, 03-, 04-...) en un seul
# fichier .odt, ouvrable directement dans LibreOffice, avec sommaire.
#
# Usage : ./scripts/build_odt.sh
# Prérequis : pandoc

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
OUTPUT="$ROOT_DIR/build/tfe.odt"

echo "==> Collecte des fichiers Markdown, dans l'ordre des dossiers"
MD_FILES=()
for dir in "$ROOT_DIR"/[0-9][0-9]-*/; do
    md=$(find "$dir" -maxdepth 1 -name "*.md" | head -n 1)
    if [ -n "$md" ]; then
        echo "    $md"
        MD_FILES+=("$md")
    fi
done

if [ ${#MD_FILES[@]} -eq 0 ]; then
    echo "Aucun fichier Markdown trouvé — vérifier la structure des dossiers."
    exit 1
fi

echo "==> Génération du .odt"
pandoc "${MD_FILES[@]}" \
    -o "$OUTPUT" \
    --toc --toc-depth=2 \
    --standalone

echo "==> Terminé : $OUTPUT"
echo "    Les titres Markdown deviennent les styles \"Titre 1\"/\"Titre 2\" de LibreOffice :"
echo "    le sommaire (Insertion > Sommaire) s'y accroche et se met à jour automatiquement."
