package views;

import dao.UserDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

public class Welcome {
    public Welcome() {
    }

    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the app");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to SignUp");
        System.out.println("Press 0 to exit");
        int choice = 0;

        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        switch (choice) {
            case 0:
                System.exit(0);
                break;
            case 1:
                this.login();
                break;
            case 2:
                this.signUp();
                break;
            default:
                System.out.println("Enter a valid choice");
        }

    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name : ");
        String name = sc.nextLine();
        System.out.println("Enter Email : ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the otp");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0:
                    System.out.println("User already registered.");
                    break;
                case 1:
                    System.out.println("user registered.");
            }
        } else {
            System.out.println("wrong otp");
        }

    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Email : ");
        String email = sc.nextLine();

        try {
            if (UserDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the otp");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)) {
                    (new views.UserView(email)).home();
                } else {
                    System.out.println("wrong otp");
                }
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }
}
