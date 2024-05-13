import eu.epfc.prm2.*;

public class RecapObject2 {

    public class Player {
        public int score;
        public String playerId;

        public Player(int score, String playerId) {
            this.score = score;
            this.playerId = playerId;
        }

        public int compareTo(Player p) {
            int length = Math.min(this.playerId.length(), p.playerId.length());
            int res = 0;
            int i = 0;
            while (res == 0 && i < length) {
                char p1 = Character.toLowerCase(this.playerId.charAt(i));
                char p2 = Character.toLowerCase(p.playerId.charAt(i));
                if (p1 != p2) {
                    res = (int) p1 - (int) p2;
                }
                i++;
            }
            return res;
        }
    }

    public int findPlayer(Array<Player> players, String playerId) {
        int res = -1;
        int i = 0;
        boolean found = false;
        while (!found && i < players.size()) {
            if (players.get(i).playerId.equals(playerId)) {
                found = true;
                res = i;
            } else {
                i++;
            }
        }
        return res;
    }

    public void swap(Array<Player> players, int a, int b) {
        Player temp = players.get(a);
        players.set(a, players.get(b));
        players.set(b, temp);
    }

    public void addPlayer(Array<Player> players, Player p) {
        players.add(p);
        int i = players.size() - 2;
        while (i != -1 && players.get(i + 1).compareTo(players.get(i)) < 0) {
            swap(players, i + 1, i);
            i--;
        }
    }

    public void deletePlayer(Array<Player> players, int pos) {
        for (int i = pos; i < players.size() - 1; i++) {
            swap(players, i, i + 1);
        }
        players.reduceTo(players.size() - 1);
    }

    public void gestion(Array<Player> players, String playerId, int score) {
        int posPlayer = findPlayer(players, playerId);
        if (posPlayer == -1) {
            addPlayer(players, new Player(score, playerId));
        } else {
            Player p = players.get(posPlayer);
            if (p.score > score) {
                deletePlayer(players, posPlayer);
            } else {
                p.score = score;
            }
        }
    }

    public void printPlayers(Array<Player> players) {
        for (Player p : players) {
            System.out.println(p.playerId + " " + p.score);
        }
    }

    public void run() {
        Array<Player> players = new Array<Player>();
        gestion(players, "zoe", 20);
        gestion(players, "matt", 23);
        gestion(players, "adrien", 56);
        gestion(players, "henry", 36);
        // gestion(players, "adrien", 20);l
        printPlayers(players);
    }

}
