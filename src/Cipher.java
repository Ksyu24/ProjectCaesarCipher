import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Cipher {
    private char[] alphabet;


    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String text, int shift) {
        // Логика шифрования
        HashMap<Character, Character> alphabetMap = getAlphabetMapWithShift(shift); //ключ - изначальный элемент алфавита, значение - элемент со сдвигом
        String resultStr = "";

        Validator validator = new Validator();

        for (char el : text.toLowerCase().toCharArray()) {
            if (validator.isElementExists(el, null, alphabetMap)) {
                resultStr += alphabetMap.get(el);
            }
            else {
                resultStr=null;
                break;
            }
        }
        return resultStr;


    }

    public String decrypt(String encryptedText, int shift) {
        // Логика расшифровки
        HashMap<Character, Character> alphabetMap = getAlphabetMapWithShift(shift); //ключ - изначальный элемент алфавита, значение - элемент со сдвигом
        String resultStr = "";

        Validator validator = new Validator();

        for (char el : encryptedText.toLowerCase().toCharArray()) {
            if(validator.isElementExists(null, el, alphabetMap)) {
                for (Character keyEl : alphabetMap.keySet()) {
                    if (alphabetMap.get(keyEl) == el) {
                        resultStr += keyEl;
                    }

                }
            }
            else {
                resultStr=null;
                break;
            }
        }
        return resultStr;
    }

    public char decrypt(char encryptedChar, int shift) /*throws NotFoundElementException*/ {
        // Логика расшифровки
        HashMap<Character, Character> alphabetMap = getAlphabetMapWithShift(shift); //ключ - изначальный элемент алфавита, значение - элемент со сдвигом
        Validator validator = new Validator();

        if (validator.isElementExists(null, encryptedChar, alphabetMap)) {
            for (Character keyEl : alphabetMap.keySet()) {
                if (alphabetMap.get(keyEl) == encryptedChar) {
                    return keyEl;
                }
            }
        }
        return '\uFFFD';
    }

    private HashMap<Character, Character> getAlphabetMapWithShift(int shift) {
        HashMap<Character, Character> alphabetMap = new HashMap<>(((int) (alphabet.length / 7.5)) + 1);
        for (int i = 0; i < alphabet.length; i++) {
            alphabetMap.put(alphabet[i], alphabet[(i + shift) % alphabet.length]);
        }
        return alphabetMap;
    }
}
