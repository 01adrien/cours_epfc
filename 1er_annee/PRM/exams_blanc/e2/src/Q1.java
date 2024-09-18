import java.util.Scanner;

public class Q1 {

	public Scanner scanner = new Scanner(System.in);

	public void run() {
		System.out.printf("Introduisez un mot de passe valide :");
		String pwd = motDePasse();
		System.out.println();
		boolean valid = allChecks(pwd);
		while (!valid) {
			System.out.printf("Recommencez ! :");
			pwd = motDePasse();
			valid = allChecks(pwd);
		}
		System.out.println("Votre mot de passe respectant les règles est : " + pwd);
	}


	public boolean estMaj(char c) {
		return c >= 'A' && c <= 'Z';
	}

	public boolean estMin(char c) {
		return c >= 'a' && c <= 'z';
	}

	public boolean estLettre(char c) {
		return estMaj(c) || estMin(c);
	}

	public boolean estChiffre(char c) {
		return c >= '0' && c <= '9';
	}

	public boolean checkLength(String pwd) {
		return pwd.length() >= 8;
	}

	public String motDePasse() {
		return scanner.nextLine();
	}

	public boolean allChecks(String pwd) {
		boolean lengthRule = false;
		boolean majRule = false;
		int numRule = 0;
		boolean specialCharRule = false;

		lengthRule = checkLength(pwd);

		for (char c : pwd.toCharArray()) {
			if (!majRule && estMaj(c)) {
				majRule = true;
			}
			if (estChiffre(c)) {
				numRule++;
			}
			if (!specialCharRule && !estLettre(c) && !estChiffre(c)) {
				specialCharRule = true;
			}
		}

		// Errors msgs

		if (!lengthRule) {
			System.out.println("Longueur invalide");
		}
		if (!majRule) {
			System.out.println("Pas de majuscule");
		}
		if (numRule < 2) {
			System.out.println("Pas assez de chiffres");
		}
		if (!specialCharRule) {
			System.out.println("Pas de character special");
		}

		return lengthRule && majRule && (numRule > 1) && specialCharRule;
	}

}
