import javax.management.RuntimeErrorException;

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
        boolean lenMatch = (str1.length() == str2.length());
        boolean stringMatch = false;
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
        int j = Math.min(str1.length(), str2.length());
        int s1, s2;
        for (int i = 0; i < j; i++) {
            s1 = (int) toUpper(str1.charAt(i));
            s2 = (int) toUpper(str2.charAt(i));
            if (s1 != s2) {
                return s2 - s1;
            }
        }
        return str2.length() - str1.length();
    }

    // EX 14
    public static String posIntTostring(int n) throws Exception {
        String str = "";
        if (n < 0) {
            throw new Exception("n < 0");
        }
        while (n > 0) {
            str += (char) n % 10;
            n /= 10;
        }
        return reverse(str);

    }

    // EX 16

    // public static Ar

    public static void main(String[] args) throws Exception {
        System.out.println(posIntTostring(456));
    }
}