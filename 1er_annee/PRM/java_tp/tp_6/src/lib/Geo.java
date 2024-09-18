package lib;

public class Geo {

    int h;

    public Geo(int h) {
        this.h = h;
    }

    public void a() {
        System.out.println();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < this.h; j++) {
                System.out.print("X");
            }
            System.out.println();
        }
    }

    public void b() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (i == 0 || i == this.h - 1) {
                    System.out.print("X");
                } else if (j == 0 || j == this.h - 1) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void c() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (j == i || j == this.h - i - 1) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void d() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (j == i || j == this.h - i - 1) {
                    System.out.print(" ");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    public void e() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (j <= i) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void f() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (j < h - i) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void g() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (j >= h - i - 1) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void h() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (j >= i) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void i() {
        System.out.println();
        for (int i = 0; i < ((this.h + 1) / 2); i++) {
            for (int j = 0; j < this.h; j++) {
                if (j >= (h / 2) - i && j <= (h / 2) + i) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void j() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (i < (this.h / 2)) {
                    if (j >= i && j <= (this.h - i - 1)) {
                        System.out.print("X");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    if (j <= i && j >= (this.h - i - 1)) {
                        System.out.print("X");
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();

        }
    }

    public void k() {
        System.out.println();
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.h; j++) {
                if (i < (this.h / 2)) {
                    if (j <= i || j >= (h - i - 1)) {
                        System.out.print("X");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    if (j >= i || j <= (h - i - 1)) {
                        System.out.print("X");
                    } else {
                        System.out.print(" ");
                    }
                }

            }
            System.out.println();
        }
    }

}
