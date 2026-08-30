-- Transforme tout bloc Markdown délimité par ::: annexe ... ::: en un petit
-- encadré coloré (renvoi vers une annexe), plutôt qu'un simple paragraphe.
--
-- Usage dans le Markdown :
--   ::: annexe
--   Schéma relationnel complet des tables décrites ci-dessus — cf. annexe 10.2.
--   :::
--
-- Nécessite \newtcbox{\annexebox}... défini dans le préambule LaTeX (voir build.sh).

function Div(el)
    if el.classes:includes("annexe") then
        local doc = pandoc.Pandoc(el.content)
        local latex = pandoc.write(doc, "latex")
        latex = latex:gsub("^%s+", ""):gsub("%s+$", "")
        return pandoc.RawBlock("latex",
            "\\begin{annexebox}\n" .. latex .. "\n\\end{annexebox}")
    end
end
