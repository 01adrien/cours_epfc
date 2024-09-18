
import eu.epfc.prm2.*;

class Q4 {
	public void run() {
		Array<Integer> tab = new Array<>(5, 3, 7, 8);
		System.out.println(estTrie(tab)); // false
		tab = new Array<Integer>(8, 9, 9, 10);
		System.out.println(estTrie(tab));
	}



	public boolean estTrie(Array<Integer> tab) {
		return estTrieRec(tab, tab.size() - 1);
	}

	public boolean estTrieRec(Array<Integer> tab, int pos) {
		if (pos == 1) {
			return tab.get(1) > tab.get(0);
		} else if (tab.get(pos) < tab.get(pos - 1)) {
			return false;
		}
		return estTrieRec(tab, pos - 1);
	}

}
