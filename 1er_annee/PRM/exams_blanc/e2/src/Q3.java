import eu.epfc.prm2.*;

class Q3 {

	public class RendezVous {
		public String titre;
		public int heureDebut;
		public int heureFin;

		public RendezVous(String titre, int heureDebut, int heureFin) {
			if (heureDebut < 0 || heureDebut > 23 || heureFin < 0 || heureFin > 23
					|| heureDebut > heureFin)
				throw new RuntimeException("RendezVous impossible");
			this.heureDebut = heureDebut;
			this.heureFin = heureFin;
			this.titre = titre;
		}

		public boolean chevauche(RendezVous autre) {
			return !(autre.heureFin <= this.heureDebut || this.heureFin <= autre.heureDebut);
		}

		public String toString() {
			return titre + " de " + heureDebut + "h à " + heureFin + "h";
		}
	}


	public void sendToEnd(Array<RendezVous> rdvs, int index) {
		for (int i = index; i < rdvs.size() - 1; i++) {
			swap(rdvs, i, i + 1);
		}
	}

	public void ajouter(Array<RendezVous> rdvs, String titre, int heureDebut, int heureFin) {
		RendezVous rdv = new RendezVous(titre, heureDebut, heureFin);
		int deleteCount = 0;
		if (rdvs.size() == 0) {
			rdvs.add(rdv);
		} else {
			rdvs.add(rdv);
			for (int i = rdvs.size() - 1; i > 0; i--) {
				if (rdv.chevauche(rdvs.get(i - 1))) {
					sendToEnd(rdvs, i - 1);
					deleteCount++;
				} else if (rdv.heureDebut < rdvs.get(i - 1).heureDebut) {
					swap(rdvs, i, i - 1);
				}
			}
			rdvs.reduceTo(rdvs.size() - deleteCount);
		}


	}

	public void swap(Array<RendezVous> rdvs, int a, int b) {
		RendezVous temp = rdvs.get(a);
		rdvs.set(a, rdvs.get(b));
		rdvs.set(b, temp);
	}


	public void run() {
		Array<RendezVous> rdvs = new Array<>();
		System.out.println(rdvs);
		ajouter(rdvs, "RDV1", 16, 18);
		System.out.println(rdvs);
		ajouter(rdvs, "RDV2", 8, 10);
		System.out.println(rdvs);
		ajouter(rdvs, "RDV3", 12, 14);
		System.out.println(rdvs);
		ajouter(rdvs, "RDV4", 13, 19); // chevauchement avec RDV3 et RDV1
		System.out.println(rdvs);
		ajouter(rdvs, "RDV5", 8, 10); // chevauchement avec RDV2
		System.out.println(rdvs);
		ajouter(rdvs, "RDV6", 12, 13);
		System.out.println(rdvs);
		ajouter(rdvs, "RDV7", 9, 15); // chevauchement avec RDV5, RDV6 et RDV4
		System.out.println(rdvs);
	}


}
