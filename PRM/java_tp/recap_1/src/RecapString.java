import eu.epfc.prm2.*;

public class RecapString {

    /*
     * A = a , b , c. , d , e. , f , g.
     * c = countParagraphes(A);
     * 
     * write = A.size() - 1
     * read = write - c
     * 
     * while (write != read)
     * 
     * if (isEndParagraphe(A.get(read)) {write --;}
     * A.set(write, A.get(read))
     * 
     * A = a , b , c. , d , e. , f , g., "", "", ""
     *
     */

    public int countParagraphes(Array<String> text) {
        int count = 0;
        for (String s : text) {
            if (this.isEndParagraphe(s)) {
                count++;
            }
        }
        return count;
    }

    public boolean isEndParagraphe(String str) {
        return str.charAt(str.length() - 1) == '.';
    }

    public void splitParagraphes(Array<String> text) {
        int countP = this.countParagraphes(text);
        text.extend(countP, "");
        int write = text.size() - 1;
        int read = write - countP;
        while (write != read) {
            if (this.isEndParagraphe(text.get(read))) {
                text.set(write--, "");
            }
            text.set(write--, text.get(read--));
        }
    }

    public void affiche(Array<String> text) {
        for (String s : text) {
            System.out.println(s);
        }
        System.out.println("===========================================");
    }

    public void run() {
        Array<String> text = new Array<>(
                "Question 4) Imaginons qu'un text soit memorise",
                "dans un tableau de strings. Chaque string du",
                "tableau correspond a une ligne du text.",
                "Les traitements de text disposent en general d'une fonctionnalite",
                "qui, pour ameliorer la lisibilite, insere de l'espace vertical",
                "entre deux paragraphes. Pour nos besoins, la fin d'un paragraphe",
                "sera definie comme une ligne se terminant par un point ('.').",
                "Ecrivez une fonction separerParagraphes(Array<String> text)",
                "qui recoit un Array de String et qui insere un string vide",
                "apres chaque string se terminant par un point.");
        this.affiche(text);
        this.splitParagraphes(text);
        this.affiche(text);
    }

}