package eu.epfc.anc3.spreadsheet.tools;
public class ExcelConverter {
    public static int[] excelToRowCol(String reference) {
        if (!reference.matches("[A-Z]+[0-9]+")) {
            throw new IllegalArgumentException("Format invalide. Attendu: lettres suivies de chiffres");
        }

        String letters = reference.replaceAll("[0-9]", "");
        String numbers = reference.replaceAll("[A-Z]", "");

        int col = 0;
        for (int i = 0; i < letters.length(); i++) {
            col = col * 26 + (letters.charAt(i) - 'A' + 1);
        }
        col--; // Ajustement pour que A=0

        int row = Integer.parseInt(numbers) - 1;
        return new int[]{row, col};
    }

    public static String rowColToExcel(int row, int col) {
        StringBuilder colStr = new StringBuilder();
        col++; // Ajustement pour que 0=A

        while (col > 0) {
            col--;
            colStr.insert(0, (char)('A' + (col % 26)));
            col = col / 26;
        }

        return colStr.toString() + (row + 1);
    }

    public static void main(String[] args) {
        String[] tests = {"B15", "AB1", "AA1"};

        for (String test : tests) {
            int[] coords = excelToRowCol(test);
            System.out.println(test + " -> ligne=" + coords[0] + ", colonne=" + coords[1]);
            System.out.println("Retour: " + rowColToExcel(coords[0], coords[1]));
        }
    }
}