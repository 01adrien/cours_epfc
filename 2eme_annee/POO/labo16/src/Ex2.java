
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Ex2 {

    public static void wordCount(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        Map<String, Integer> hashMap = new HashMap<>();
        scanner.useDelimiter("[\\p{Punct}\\s]+");
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (hashMap.containsKey(word)) {
                hashMap.replace(word, hashMap.get(word) + 1);
            } else {
                hashMap.put(word, 1);
            }
        }
        scanner.close();
        // hashMap.forEach((key, value) -> System.out.println(key + " " + value));
        TreeMap<String, Integer> sorted = new TreeMap<>(hashMap);
        System.out.println(sorted);
    }

    public static void main(String[] args) {
        try {
            wordCount("hamlet.txt");
        } catch (FileNotFoundException e) {
            System.out.println("file not found  ");
        }
    }

}
