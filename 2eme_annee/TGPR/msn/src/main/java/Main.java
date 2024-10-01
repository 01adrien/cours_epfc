public class Main {
    public static void main(String[] args) {

        System.out.println("\nListe des membres :");
        var members = Member.getAll();
        for (var m : members)
            System.out.println(m);

        System.out.println("\nMembre 'ben' :");
        var ben = Member.getByPseudo("ben");
        System.out.println(ben);

        System.out.println("\nNouveau membre 'test'");
        var test = new Member("test", "test", false);
        // sauvergarde
        boolean res = test.save();
        assert res;
        // relecture en BD
        test = Member.getByPseudo("test");
        assert test != null;
        System.out.println(test);
        // suppression
        res = test.delete();
        assert res;
        assert Member.getByPseudo("test") == null;
    }
}
