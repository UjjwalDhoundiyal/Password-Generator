public class encrypt {
        public static String xorEncryptDecrypt(String input, String key) {
            char[] inputChars = input.toCharArray();
            char[] keyChars = key.toCharArray();
            
            char[] result = new char[inputChars.length];
    
            for (int i = 0; i < inputChars.length; i++) {
                result[i] = (char) (inputChars[i] ^ keyChars[i % keyChars.length]);
            }
    
            return new String(result);
        }
    
        public static void main(String[] args) {
            String plaintext = "This is just a example to check how it works";
            String key = "short";
            
            // Encrypt
            String encrypted = xorEncryptDecrypt(plaintext, key);
            System.out.println("Encrypted: " + encrypted);
            
            // Decrypt
            String decrypted = xorEncryptDecrypt(encrypted, key);
            System.out.println("Decrypted: " + decrypted);
        }
    }
    