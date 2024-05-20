import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Try2 {
    public static void main(String[] args) {
        int total_length, digit_length, alpha_length, symbol_length;

        int[][] symbolRanges = { { 33, 47 }, { 58, 64 }, { 91, 96 }, { 123, 126 } };
        int min_digit = 48, max_digit = 57;
        int[][] alphaRanges = { { 65, 90 }, { 97, 122 } };

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the password length:- ");
        total_length = sc.nextInt();

        System.out.println("Enter the total Alphabets you want in your Password:- ");
        alpha_length = sc.nextInt();

        System.out.println("Enter the total Digits you want in your Password:- ");
        digit_length = sc.nextInt();

        System.out.println("Enter the total symbols you want in your password:- ");
        symbol_length = sc.nextInt();

        int length_checker = alpha_length + digit_length + symbol_length;

        if (length_checker > total_length) {
            System.out.println("The total length exceeds the specified length. Please recheck.");
        } 
        else if (length_checker < total_length) {
            System.out.println("The total length is less than the specified length. Please recheck.");
        } 
        else {
            List<Character> password = new ArrayList<>();

            for (int i = 0; i < alpha_length; i++) {
                int[] range = alphaRanges[(int)(Math.random() * alphaRanges.length)];
                int rand = (int)(Math.random() * (range[1] - range[0] + 1)) + range[0];
                password.add((char)rand);
            }

            for (int i = 0; i < digit_length; i++) {
                int rand = (int)(Math.random() * (max_digit - min_digit + 1)) + min_digit;
                password.add((char)rand);
            }

            for (int i = 0; i < symbol_length; i++) {
                int[] range = symbolRanges[(int)(Math.random() * symbolRanges.length)];
                int rand = (int)(Math.random() * (range[1] - range[0] + 1)) + range[0];
                password.add((char)rand);
            }

            System.out.println("Password Before shuffle:- "+password);

            Collections.shuffle(password);

            System.out.print("This is your Password:- ");
            for (char ch : password) {
                System.out.print(ch);
            }
        }
    }
}
