# Découvre automatiquement les dossiers de parties (01-introduction, etc.)
PARTS := $(notdir $(wildcard [0-9][0-9]-*))

.PHONY: pdf preview odt clean $(PARTS)

# Document complet
pdf:
	./scripts/build.sh

# Une seule partie : make 04-realisation -> génère 04-realisation.pdf à la racine
$(PARTS):
	bash scripts/pdf.sh $@

preview:
	bash scripts/preview.sh

odt:
	bash scripts/odt.sh

clean:
	rm -f build/*.*
	find . -type f -name "*.png" -path "*/assets/*" -delete
