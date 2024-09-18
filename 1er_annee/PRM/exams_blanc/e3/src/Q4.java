class Q4 {
	public void run() {
		System.out.println(func(123456789));
	}

	public int func(int n) {
		if (n < 10) {
			return n % 2 == 0 ? 0 : n;
		}
		int num = (n % 10) % 2 == 0 ? 0 : n % 10;
		return num + func(n / 10);
	}
}
