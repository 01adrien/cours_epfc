class Q4 {
    public void run() {
        System.out.println(lastIndexOf('a', "baaab")); // 3
        System.out.println(lastIndexOf('z', "baaab")); // -1
    }

    public int lastIndexOf(char c, String str) {
        return lastIndexOfRec(c, str, -1, 0);
    }

    public int lastIndexOfRec(char c, String str, int retPos, int index) {
        if (index == str.length() - 1) {
            return retPos;
        }

        else if (str.charAt(index) == c) {
            retPos = index;
        }

        return lastIndexOfRec(c, str, retPos, index + 1);

    }
}
