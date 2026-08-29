.PHONY: pdf clean
 
pdf:
	bash scripts/build_pdf.sh

odt:
	bash scripts/build_odt.sh

clean:
	rm -f tfe.odt
	rm -f tfe.pdf
	find . -type f -name "*.png" -path "*/assets/*" -delete
 
