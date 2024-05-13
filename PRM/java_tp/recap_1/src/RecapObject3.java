
import java.util.Random;

import eu.epfc.prm2.*;

public class RecapObject3 {

    public class Card {
        public int value, color;

        public Card(int value, int color) {
            if (value < 1 || value > 13 || color < 0 || color > 3) {
                throw new Error();
            }
            this.value = value;
            this.color = color;
        }

        public int compareTo(Card c) {
            int diff = 0;
            if (this.value == c.value) {
                diff = this.color - c.color;
            } else {
                diff = this.value - c.value;
            }
            return diff;
        }

        @Override
        public String toString() {
            Array<String> valueArray = new Array<String>(
                    "as", "2", "3", "4", "5", "6", "7",
                    "8", "9", "10", "valet", "dame", "roi");
            Array<String> colorArray = new Array<String>("trefle", "carreau", "coeur", "pique");
            return valueArray.get(this.value - 1) + " de " + colorArray.get(this.color);
        }

        public boolean equal(Card c) {
            return this.color == c.color && this.value == c.value;
        }

    }

    public void swap(Array<Card> cards, int a, int b) {
        Card temp = cards.get(a);
        cards.set(a, cards.get(b));
        cards.set(b, temp);
    }

    public int isBrelan(Array<Card> cards, Card c) {
        int posBrelan = -1;
        if (cards.size() > 1) {
            int count, i;
            i = count = 0;
            boolean brelan = false;
            while (i < cards.size() && !brelan) {
                if (cards.get(i).value == c.value) {
                    count++;
                }
                if (count == 2) {
                    posBrelan = i - 1;
                    brelan = true;
                }
                i++;
            }
        }
        return posBrelan;
    }

    public void removeBrelan(Array<Card> cards, int posBrelan) {
        for (int i = posBrelan; i < cards.size() - 2; i += 2) {
            swap(cards, i, i + 2);
            if (i + 3 < cards.size()) {
                swap(cards, i + 1, i + 3);
            }
        }
        cards.reduceTo(cards.size() - 2);
    }

    public void addCard(Array<Card> cards, Card c) {
        cards.add(c);
        int i = cards.size() - 2;
        while (i >= 0 && c.compareTo(cards.get(i)) < 0) {
            swap(cards, i, i + 1);
            i--;
        }
    }

    public boolean inCards(Array<Card> cards, Card c) {
        int i = 0;
        boolean found = false;
        while (i < cards.size() && !found) {
            if (c.equal(cards.get(i))) {
                found = true;
            }
            i++;
        }
        return found;
    }

    public void play(Array<Card> cards, Card c) {
        int posBrelan = isBrelan(cards, c);
        if (posBrelan != -1) {
            System.out.println("Brelan");
            System.out.println(c);
            for (int i = posBrelan; i < posBrelan + 2; i++) {
                System.out.println(cards.get(i));
            }
            removeBrelan(cards, posBrelan);
        } else if (!inCards(cards, c)) {
            addCard(cards, c);
        }
    }

    public void printCards(Array<Card> cards, String msg) {
        System.out.println(msg);
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    public void run() {

        Array<Card> cards = new Array<Card>();
        // Random r = new Random();
        // while (cards.size() != 6) {
        // Card c = new Card(r.nextInt(12) + 1, r.nextInt(3));
        // System.out.println(c);
        // addCard(cards, c);
        // }
        play(cards, new Card(2, 3));
        play(cards, new Card(3, 3));
        play(cards, new Card(2, 2));
        play(cards, new Card(6, 0));
        play(cards, new Card(12, 0));
        play(cards, new Card(9, 3));
        play(cards, new Card(4, 0));
        play(cards, new Card(2, 1));
        System.out.println();
        printCards(cards, "test");
    }

}
