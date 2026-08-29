-- Convertit chaque tableau Markdown en tableau LaTeX avec un quadrillage
-- complet (bordures horizontales ET verticales), au lieu du style
-- "booktabs" épuré utilisé par défaut par pandoc (qui n'a que 3 lignes
-- horizontales et aucune ligne verticale).
--
-- Les colonnes sont de largeur égale et le texte y retourne à la ligne
-- automatiquement (colonnes p{...} plutôt que l/c/r, qui ne wrappent pas).

local function cell_to_latex(cell)
    local doc = pandoc.Pandoc(cell.contents)
    local latex = pandoc.write(doc, "latex")
    latex = latex:gsub("\n+", " "):gsub("^%s+", ""):gsub("%s+$", "")
    return latex
end

function Table(tbl)
    local n = #tbl.colspecs
    if n == 0 then return nil end

    -- Colonnes de largeur égale, avec retour à la ligne automatique du texte
    local col_width = string.format("%.3f", 0.92 / n)
    local col = "p{" .. col_width .. "\\linewidth}"
    local colspec_str = "|"
    for _ = 1, n do
        colspec_str = colspec_str .. col .. "|"
    end

    local lines = {}
    table.insert(lines, "\\renewcommand{\\arraystretch}{1.3}")
    table.insert(lines, "\\begin{longtable}{" .. colspec_str .. "}")
    table.insert(lines, "\\hline")

    if tbl.head and #tbl.head.rows > 0 then
        for _, row in ipairs(tbl.head.rows) do
            local cells = {}
            for _, cell in ipairs(row.cells) do
                cells[#cells + 1] = "\\textbf{" .. cell_to_latex(cell) .. "}"
            end
            table.insert(lines, table.concat(cells, " & ") .. " \\\\")
            table.insert(lines, "\\hline")
            table.insert(lines, "\\endhead")
        end
    end

    for _, body in ipairs(tbl.bodies) do
        for _, row in ipairs(body.body) do
            local cells = {}
            for _, cell in ipairs(row.cells) do
                cells[#cells + 1] = cell_to_latex(cell)
            end
            table.insert(lines, table.concat(cells, " & ") .. " \\\\")
            table.insert(lines, "\\hline")
        end
    end

    table.insert(lines, "\\end{longtable}")
    table.insert(lines, "\\renewcommand{\\arraystretch}{1}")

    return pandoc.RawBlock("latex", table.concat(lines, "\n"))
end
