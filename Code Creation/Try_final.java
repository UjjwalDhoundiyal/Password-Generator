import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Try_final {
    public static void main(String args[]) {
        int total_length, digit_length, alpha_length, symbol_length;

        // ASCII Code range for symbols
        int[][] symbolRange = { { 33, 47 }, { 58, 64 }, { 91, 96 }, { 123, 126 } };

        // ASCII Code range for digits
        int min_digit = 48, max_digit = 57;

        // ASCII Code range for alphabets
        int[][] alphaRange = { { 65, 90 }, { 97, 122 } };

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the password length:- ");
        total_length = sc.nextInt();

        System.out.print("\nEnter the total Alphabets you want in your Password:- ");
        alpha_length = sc.nextInt();

        System.out.print("\nEnter the total Digits you want in your Password:- ");
        digit_length = sc.nextInt();

        System.out.print("\nEnter the total symbols you want in your password:- ");
        symbol_length = sc.nextInt();

        int length_checker = alpha_length + digit_length + symbol_length;

        if (length_checker > total_length) {
            System.out.println("The total length exceeds the specified length. Please recheck.");
        } else {
            List<Character> password = new ArrayList<>();

            for (int i = 0; i < alpha_length; i++) {
                int[] range = alphaRange[(int) (Math.random() * alphaRange.length)];
                int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
                password.add((char) rand);
            }

            for (int i = 0; i < digit_length; i++) {
                int rand = (int) (Math.random() * (max_digit - min_digit + 1)) + min_digit;
                password.add((char) rand);
            }

            for (int i = 0; i < symbol_length; i++) {
                int[] range = symbolRange[(int) (Math.random() * symbolRange.length)];
                int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
                password.add((char) rand);
            }

            // Fill the remaining length with random characters
            while (password.size() < total_length) {
                double choice = Math.random();

                if (choice < 0.33) {
                    int[] range = alphaRange[(int) (Math.random() * alphaRange.length)];
                    int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
                    password.add((char) rand);
                }

                else if (choice < 0.66) {
                    int rand = (int) (Math.random() * (max_digit - min_digit + 1)) + min_digit;
                    password.add((char) rand);
                }

                else {
                    int[] range = symbolRange[(int) (Math.random() * symbolRange.length)];
                    int rand = (int) (Math.random() * (range[1] - range[0] + 1)) + range[0];
                    password.add((char) rand);
                }
            }

            Collections.shuffle(password);

            System.out.print("\nHere is your Password:- ");
            for (char ch : password) {
                System.out.print(ch);
            }
        }
    }
}
