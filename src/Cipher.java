import java.util.*;

public class Cipher {
    private char[] alphabet;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String text, int shift) {
        // Логика шифрования
        String resultStr = "";
        HashMap<Integer, Character> alphabetMap = new HashMap<>(((int) (alphabet.length / 7.5)) + 1);
        for (int i = 0; i < alphabet.length; i++) {
            alphabetMap.put(i, alphabet[i]);
        }

        for (char el : text.toLowerCase().toCharArray()) {
            if (alphabetMap.containsValue(el)) {
                int nePpoz, poz = -1;
                for (Integer key : alphabetMap.keySet()) {
                    if (alphabetMap.get(key) == el) {
                        poz = key;
                        break;
                    }
                }
                int newPoz = (poz + shift) % alphabet.length;
                resultStr += alphabetMap.get(newPoz);
            }
        }
        return resultStr;
    }

    public String decrypt(String encryptedText, int shift) {
        // Логика расшифровки
        return "Hello";
    }
}
