public class ex_12 {
    public static void main(String[] args) {

        int hourStart = 11;
        int minStart = 0;
        int hourEnd = 14;
        int minEnd = 15;

        int hour1 = hourStart * 60 + minStart;
        int hour2 = hourEnd * 60 + minEnd;

        int time = hour2 - hour1;

        int hour = time / 60;
        int min = time % 60;

        System.out.println(hour + ":" + min);
    }
}
