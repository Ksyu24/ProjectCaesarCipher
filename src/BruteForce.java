import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BruteForce {

    public String decryptByBruteForce(String encryptedText, char[] alphabet) {
        // Логика brute force
        int maxPoint = 0, currentPoint = 0;
        String mostSuccesLine = null, currentLine = null;
        Cipher cipheBrute = new Cipher(alphabet);

        if(!encryptedText.isEmpty()) {
            for (int i = 1; i < alphabet.length; i++) {
                currentLine = cipheBrute.decrypt(encryptedText, i);
                if (currentLine == null)
                    return currentLine;
                currentPoint = pointFrequentlyMentionedSymbols(currentLine) + pointLengthWords(currentLine) + pointPunctuation(currentLine) + pointReadableWords(currentLine);
                if (currentPoint > maxPoint) {
                    maxPoint = currentPoint;
                    mostSuccesLine = currentLine;
                    currentPoint = 0;
                    if (maxPoint == 4)
                        break;
                }
            }
        }

        return mostSuccesLine;
    }


    private int pointFrequentlyMentionedSymbols(String text) {
        Map<Character, Integer> symolsFrequentlyMap = new HashMap<>();

        for (char symbol : text.toCharArray()) {
            symolsFrequentlyMap.put(symbol, symolsFrequentlyMap.getOrDefault(symbol, 0) + 1);
        }

        if ((symolsFrequentlyMap.containsKey('о') && ((double) symolsFrequentlyMap.get('о') / (double) text.length() * 100 >= 25)) ||
                (symolsFrequentlyMap.containsKey('а') && ((double) symolsFrequentlyMap.get('а') / (double) text.length() * 100 >= 25)) ||
                (symolsFrequentlyMap.containsKey('е') && ((double) symolsFrequentlyMap.get('е') / (double) text.length() * 100 >= 25)) ||
                (symolsFrequentlyMap.containsKey('и') && ((double) symolsFrequentlyMap.get('и') / (double) text.length() * 100 >= 25)) ||
                (symolsFrequentlyMap.containsKey('н') && ((double) symolsFrequentlyMap.get('н') / (double) text.length() * 100 >= 25)) ||
                (symolsFrequentlyMap.containsKey(' ') && ((double) symolsFrequentlyMap.get(' ') / (double) text.length() * 100 >= 25)))
            return 1;

        return 0;
    }

    private int pointLengthWords(String text) {
        String[] arrayStr = text.trim().split(" ");
        for (String word : arrayStr) {
            if (word.length() > 35)
                return 0;
        }
        return 1;
    }

    private int pointPunctuation(String text) {
        char[] arrayChar = text.toCharArray();
        if (arrayChar[arrayChar.length - 1] == '.' || arrayChar[arrayChar.length - 1] == '?' || arrayChar[arrayChar.length - 1] == '!') {
            for (int i = 0; i < arrayChar.length - 2; i++) {
                if (((arrayChar[i] == ':' || arrayChar[i] == '.' || arrayChar[i] == ',' || arrayChar[i] == '?' || arrayChar[i] == '!') && arrayChar[i + 1] != ' '))
                    return 0;
                if (arrayChar[i] == ' ' && arrayChar[i + 1] == ' ')
                    return 0;
                return 1;
            }
        }
        return 0;
    }

    private int pointReadableWords(String text) {
        String[] arrayStr = text.trim().split(" ");
        for (int i = 0; i < arrayStr.length; i++) {
            arrayStr[i] = arrayStr[i].replace(".", "");
            arrayStr[i] = arrayStr[i].replace("-", "");
            arrayStr[i] = arrayStr[i].replace(",", "");
            arrayStr[i] = arrayStr[i].replace("\\?", "");
            arrayStr[i] = arrayStr[i].replace("\"", "");
            arrayStr[i] = arrayStr[i].replace("\'", "");
            arrayStr[i] = arrayStr[i].replace(":", "");
            arrayStr[i] = arrayStr[i].replace("!", "");
        }
        HashSet<String> setStr = new HashSet<>(Arrays.stream(arrayStr).toList());
        HashSet<String> dictionary = getDictionarySet();
        int count = 0;
        for (String wordArray : setStr) {
            if (dictionary.contains(wordArray)) {
                count++;
                if (count == 4)
                    return 1;
            }
        }
        return 0;
    }

    private HashSet<String> getDictionarySet() {
        Path pathToDictionary = Path.of("sorceFiles/dictionary");
        try {
            HashSet<String> dictionary = new HashSet<>(Files.readAllLines(pathToDictionary));
            return dictionary;
        } catch (Exception e) {
            String logPath = "sorceFiles/logs";
            if (Files.exists(Path.of(logPath)))
            {
                try {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    Path path = Path.of(logPath);
                    StringBuilder message = new StringBuilder(dtf.format(LocalDateTime.now())+"\n"+e.getMessage()+"\n");
                    for (StackTraceElement el : e.getStackTrace())
                    {
                        message.append(el.toString()+"\n");
                    }
                    Files.writeString(path, message+"\n", StandardOpenOption.APPEND);
                }
                catch (IOException e1) {}
            }
            HashSet<String> dictionary = new HashSet<>();
            dictionary.add("и");
            dictionary.add("в");
            dictionary.add("не");
            dictionary.add("на");
            dictionary.add("я");
            dictionary.add("что");
            dictionary.add("с");
            dictionary.add("он");
            dictionary.add("как");
            dictionary.add("это");
        }
        return null;
    }
}
