package tgpr.tuto.model;

public abstract class Security {
    private static Member loggedUser = null;

    public static Member getLoggedUser() {
        return loggedUser;
    }

    public static void login(Member user) {
        loggedUser = user;
    }

    public static boolean isLogged() {
        return loggedUser != null;
    }

    public static boolean isLoggedUser(Member user) {
        return loggedUser.equals(user);
    }

    public static void logout() {
        login(null);
    }

    public static boolean isAdmin() {
        return loggedUser != null && loggedUser.isAdmin();
    }
}

