public class App {

    // EX 1
    public static boolean isLower(char c) {
        return (c >= 'a' && c <= 'z');
    }

    // EX 2
    public static char toUpper(char c) {
        return (isLower(c) ? (char) (c - 'a' + 'A') : c);
    }

    // EX 3
    public static boolean isAllChar(String str) {
        int i = 0;
        while (i < str.length() && isLower(str.charAt(i))) {
            i++;
        }
        return i == str.length();
    }

    // EX 4
    public static String stringToUpper(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            newStr += toUpper(str.charAt(i));
        }
        return newStr;
    }

    // EX 6
    public static String reverse(String str) {
        String newStr = "";
        int j = str.length() - 1;
        while (j >= 0) {
            newStr += str.charAt(j--);
        }
        return newStr;
    }

    // EX 7
    public static boolean isPalindrome(String str) {
        int i = 0;
        while (i < str.length() / 2) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1)) {
                return false;
            }
            i++;
        }
        return true;
    }

    // EX 10
    public static boolean StrEqual(String str1, String str2) {
        boolean lenMatch = false;
        boolean stringMatch = false;
        if (str1.length() == str2.length()) {
            lenMatch = true;
        }
        if (lenMatch) {
            int i = 0;
            while (i < str1.length() && str1.charAt(i) == str2.charAt(i)) {
                i++;
            }
            stringMatch = (i == str1.length());
        }
        return lenMatch && stringMatch;

    }

    // EX 12
    public static int strCompare(String str1, String str2) {
        return 0;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(strCompare("test", "test") ? "equal" : "not equal");
    }
}