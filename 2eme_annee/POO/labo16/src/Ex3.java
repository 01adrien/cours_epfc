
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;

public class Ex3 {
    public static void copy(String sourcePath, String destinationPath) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(sourcePath));
                PrintWriter out = new PrintWriter(new File(destinationPath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                out.println(line);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // copy("hamlet.txt", "hamlet_copy.txt");
            // copy("fichierInexistant.txt", "test.txt");
            copy("hamlet.txt", null);
        } catch (NullPointerException e) {
            System.out.println("Nom de fichier null");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
