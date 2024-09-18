import eu.epfc.prm2.*;


class Q3 {
	public void run() {

		Array<Character> text = new Array<>(' ', ' ', 'l', 'a', ' ', 'v', 'i', 'e', ' ', ' ', ' ',
				' ', ' ', 'e', 's', 't', ' ', ' ', 'b', 'e', 'l', 'l', 'e', ' ', ' ');
		System.out.println(text);
		space2tab(text);
		System.out.println(text);
	}

	public void swap(Array<Character> text, int a, int b) {
		char temp = text.get(a);
		text.set(a, text.get(b));
		text.set(b, temp);
	}

	public void swapTo(Array<Character> text, int from, int to) {
		for (int i = from; i < to; i++) {
			swap(text, i, i + 1);
		}
	}

	public void space2tab(Array<Character> text) {
		int start = 0;
		int end = text.size() - 1;
		while (start < end - 1) {
			int t = start;
			if (text.get(start) == ' ') {
				while (text.get(start + 1) == ' ') {
					swapTo(text, start + 1, end);
					start++;
					end--;
				}
				text.set(t, 'T');
			} else {
				start++;
			}
		}
		text.reduceTo(end);
	}
}
