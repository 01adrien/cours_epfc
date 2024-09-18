import java.util.Random;
import java.util.Scanner;

class Q1 {

	public Scanner scan = new Scanner(System.in);
	public Random generateur = new Random();

	public void run() {
		System.out.println("Bienvenue ! Vous avez 10 jetons.");
		System.out.println();
		int score = jouer();
		System.out.println();
		System.out.println("Au revoir. Vous repartez avec " + score + " jetons.");
	}

	public int lance() {
		return generateur.nextInt(1, 7);
	}

	public int jouer() {
		int lancesNum = getLancesNum();
		int jetons = 10;
		while (lancesNum != 0 && jetons >= 2) {
			int diceTotal = 0;
			int diceNum = 0;
			boolean game = true;
			int sumToReach = getSumToReach(lancesNum);
			while (game) {
				int l = lance();
				diceTotal += l;
				diceNum++;
				System.out.println(
						"Lancer numero " + diceNum + " : " + l + " , somme : " + diceTotal);
				if (diceTotal == sumToReach) {
					jetons += 10;
					System.out.println("Partie gagnee - Il vous reste " + jetons + " jetons");
					game = false;
				} else if (diceTotal > sumToReach
						|| (lancesNum == diceNum && diceTotal < sumToReach)) {
					jetons -= 2;
					System.out.println("Partie Perdue - Il vous reste " + jetons + " jetons");
					game = false;
				}
			}
			if (jetons >= 2) {
				System.out.println();
				lancesNum = getLancesNum();
			}
		}
		return jetons;

	}

	public boolean isValidSum(int lancesNum, int sumToReach) {
		return (sumToReach >= lancesNum) && (sumToReach <= (lancesNum * 6));
	}

	public int getLancesNum() {
		int res = -1;
		do {
			System.out.printf("Combien de lancers ? (0 pour arreter) :");
			res = scan.nextInt();
		} while (res < 0);
		return res;
	}

	public int getSumToReach(int lancesNum) {
		int min = lancesNum;
		int max = lancesNum * 6;
		int sum = -1;
		do {
			System.out.printf("Quelle somme ? (doit etre >= " + min + " et <= " + max + " ) :");
			sum = scan.nextInt();
		} while (!isValidSum(lancesNum, sum));
		return sum;
	}

}
