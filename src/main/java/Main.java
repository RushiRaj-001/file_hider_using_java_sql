import views.Welcome;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Welcome w = new Welcome();

        while (true) {
            w.welcomeScreen();
        }
    }
}
