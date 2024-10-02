package tgpr.tuto.model;

import tgpr.framework.Model;
import tgpr.framework.Params;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static tgpr.framework.Tools.hash;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Member extends Model {
    public enum Fields {
        Pseudo, Password, Profile, Admin, BirthDate
    }

    public enum RelationshipType {
        Unrelated("This member is not related to you."),
        Followee("You are following this member."),
        Follower("This member is following you."),
        Mutual("This member and you are mutual friends."),
        Yourself("This is you!");

        private final String text;

        RelationshipType(final String text) {
            this.text = text;
        }

        public String toText() {
            return text;
        }

        // retourne les valeurs de l'enum triées par ordre alphabétique
        public static List<String> getSortedStrings() {
            return Arrays.stream(Member.RelationshipType.values())
                    .sorted(Comparator.comparing(Enum::toString))
                    .map(Enum::toString)
                    .collect(Collectors.toList());
        }
    }
    private String pseudo;
    private String password;
    private String profile;
    private LocalDate birthdate;
    private boolean admin;

    public Member() {
    }

    public Member(String pseudo, String password, boolean admin) {
        this.pseudo = pseudo;
        this.password = password;
        this.admin = admin;
    }

    public Member(String pseudo, String password, String profile, LocalDate birthdate, boolean admin) {
        this.pseudo = pseudo;
        this.password = password;
        this.profile = profile;
        this.admin = admin;
        this.birthdate = birthdate;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
        public String toString() {
        return "tgpr.tuto.model.Member{" +
                "pseudo='" + pseudo + '\'' +
                ", profile='" + profile + '\'' +
                ", birthdate=" + birthdate +
                ", admin=" + admin +
                '}';
    }

    protected void mapper(ResultSet resultSet) throws SQLException {
        pseudo = resultSet.getString("pseudo");
        password = resultSet.getString("password");
        profile = resultSet.getString("profile");
        admin = resultSet.getBoolean("admin");
        birthdate = resultSet.getObject("birthdate", LocalDate.class);
    }

    @Override
    public void reload() {
        reload("select * from members where pseudo=:pseudo", new Params("pseudo", pseudo));
    }

    public static List<Member> getAll() {
        return queryList(Member.class, "select * from members order by pseudo");
    }

    public static Member getByPseudo(String pseudo) {
        return queryOne(Member.class, "select * from members where pseudo=:pseudo", new Params("pseudo", pseudo));
    }

    public boolean save() {
        int c;
        Member m = getByPseudo(pseudo);
        String sql;
        if (m == null)
            sql = "insert into members (pseudo, password, profile, admin, birthdate) " +
                    "values (:pseudo,:password,:profile,:admin,:birthdate)";
        else if (password == null || password.isBlank())
            sql = "update members set profile=:profile, admin=:admin, " +
                    "birthdate=:birthdate where pseudo=:pseudo";
        else
            sql = "update members set password=:password, profile=:profile, admin=:admin, " +
                    "birthdate=:birthdate where pseudo=:pseudo";
        c = execute(sql, new Params()
                .add("pseudo", pseudo)
                .add("password", password)
                .add("profile", profile)
                .add("admin", admin)
                .add("birthdate", birthdate));
        return c == 1;
    }

    public boolean delete() {
        execute("delete from follows where follower=:pseudo or followee=:pseudo", new Params("pseudo", pseudo));
        int c = execute("delete from members where pseudo=:pseudo", new Params("pseudo", pseudo));
        return c == 1;
    }

    public static Member checkCredentials(String pseudo, String password) {
        var member = Member.getByPseudo(pseudo);
        if (member != null && member.password.equals(hash(password)))
            return member;
        return null;
    }

    public List<Member> getFollowers() {
        return queryList(Member.class,
                "select m.* from follows join members m on follows.follower = m.pseudo where followee=:pseudo",
                new Params("pseudo", pseudo));
    }

    public List<Member> getFollowees() {
        return queryList(Member.class,
                "select m.* from follows join members m on follows.followee = m.pseudo where follower=:pseudo",
                new Params("pseudo", pseudo));
    }

    public boolean follow(Member other) {
        if (other.equals(this))
            return false;
        if (!getFollowees().contains(other)) {
            int c = execute("insert into follows values (:follower,:followee)",
                    new Params("follower", pseudo)
                            .add("followee", other.pseudo));
            return c == 1;
        }
        return false;
    }

    public boolean unfollow(Member other) {
        if (other.equals(this))
            return false;
        int c = execute("delete from follows where follower=:follower and followee=:followee",
                new Params("follower", pseudo)
                        .add("followee", other.pseudo));
        return c == 1;
    }

    public RelationshipType getRelationshipType(Member otherMember) {
        if (otherMember == null)
            return RelationshipType.Unrelated;
        else if (otherMember.equals(this))
            return RelationshipType.Yourself;
        var followee = getFollowees().contains(otherMember);
        var follower = getFollowers().contains(otherMember);
        if (followee && follower)
            return RelationshipType.Mutual;
        else if (followee)
            return RelationshipType.Followee;
        else if (follower)
            return RelationshipType.Follower;
        else
            return RelationshipType.Unrelated;
    }

    public void toggleFollowUnfollow(Member otherMember) {
        if (otherMember == null) return;
        switch (getRelationshipType(otherMember)) {
            case Mutual, Followee -> unfollow(otherMember);
            default -> follow(otherMember);
        }
    }

    @Override
    public boolean equals(Object o) {
        // s'il s'agit du même objet en mémoire, retourne vrai
        if (this == o) return true;
        // si l'objet à comparer est null ou n'est pas issu de la même classe que l'objet courant, retourne faux
        if (o == null || getClass() != o.getClass()) return false;
        // transtype l'objet reçu en Member
        Member member = (Member) o;
        // retourne vrai si les deux objets ont le même pseudo
        // remarque : cela veut dire que les deux objets sont considérés comme identiques s'ils on le même pseudo
        //            ce qui a du sens car c'est la clef primaire de la table. Attention cependant car cela signifie
        //            que si d'autres attributs sont différents, les objets seront malgré tout considérés égaux.
        return pseudo.equals(member.pseudo);
    }

    @Override
    public int hashCode() {
        // on retourne le hash code du pseudo qui est "unique" puisqu'il correspond à la clef primaire
        return Objects.hash(pseudo);
    }
}
