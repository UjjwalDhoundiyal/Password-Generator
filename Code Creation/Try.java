import java.util.Scanner;

class Try {
        public static void main(String args[]) {
                int total_length, digit_length, alpha_length, symbol_length;

                // //ASCII CODE for Symbols
                // int symbol1=34, symbol2=58, symbol3=91, symbol4=123;
                // int symbol1_end=47, symbol2_end=64, symbol3_end=95, symbol4_end=125;

                // //ASCII Code for Digits
                // int min_digit =48, max_digit=57;

                // //ASCII Code for Alphabets
                // int min_alpha1=65, min_alpha2=97;
                // int max_alpha1=90, max_alpha2=122;

                int min = 27, max = 125;
                int range = max - min + 1;
                Scanner sc = new Scanner(System.in);

                System.out.println("Enter the password length:- ");
                total_length = sc.nextInt();

                System.out.println("Enter the total Alphabets you want in your Password:- ");
                alpha_length = sc.nextInt();

                System.out.println("Enter the total Digit you want in your Password:- ");
                digit_length = sc.nextInt();

                System.out.println("Enter the total symbols you want in your password:-");
                symbol_length = sc.nextInt();

                int length_checker = alpha_length + digit_length + symbol_length;

                if (length_checker <= total_length) {
                        for (int i = 0; i < alpha_length; i++) {
                                int rand = (int) (Math.random() * range) + min;
                                System.out.print(rand + " ");
                        }
                        for (int i = 0; i < digit_length; i++) {
                                int rand = (int) (Math.random() * range) + min;
                                System.out.print(rand + " ");
                        }
                        for (int i = 0; i < symbol_length; i++) {
                                int rand = (int) (Math.random() * range) + min;
                                System.out.print(rand + " ");
                        }
                } else {
                        System.out.println("The Total Length exceed please recheck.");
                }
        }
}