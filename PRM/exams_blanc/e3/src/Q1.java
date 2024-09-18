import java.util.Scanner;

class Q1 {

	public Scanner scanner = new Scanner(System.in);

	public void run() {
		int t1 = saisieTemp();
		int t2 = saisieTemp();
		int t3 = saisieTemp();
		afficheVariationsTemp(t1, t2, t3);
	}

	int saisieTemp() {
		System.out.printf("Entrez une temperature : ");
		int temp = scanner.nextInt();
		while (temp < -40 || temp > 50) {
			System.out.printf("Temperature invalide (doit etre comprise entre -40 et 50) : ");
			temp = scanner.nextInt();
		}
		return temp;
	}

	public void afficheVariationsTemp(int t1, int t2, int t3) {
		int ecart1 = calculVariationJournaliere(t1, t2);
		int ecart2 = calculVariationJournaliere(t2, t3);
		int variations = 0;
		System.out.println("les ecarts sont de " + ecart1 + " et " + ecart2);
		if (t1 < t2) {
			variations++;
		}
		if (t2 < t3) {
			variations++;
		}
	}

	public int calculVariationJournaliere(int t1, int t2) {
		return (t1 - t2) * -1;
	}

}
