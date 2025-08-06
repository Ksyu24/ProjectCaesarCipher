import java.util.*;

public class StatisticalAnalyzer {
    public int findMostLikelyShift(String encryptedText,
                                   char[] alphabet, String representativeText) {

        List<Character> encryptedCharRating = findMostFrequentlySymbols(encryptedText);
        List<Character> representativeCharRating = findMostFrequentlySymbols(representativeText);


        int otklon = encryptedCharRating.size();
        int otklonSum = 0, minOtklonSum=Integer.MAX_VALUE;
        int bestKey=-1;

        Cipher cipheAnalyz = new Cipher(alphabet);

        for (int i = 1; i < alphabet.length; i++) {

            representativeCharRating.retainAll(encryptedCharRating);

            for (int j = 0; j < encryptedCharRating.size(); j++) {

                char currentSymbolJ=cipheAnalyz.decrypt(encryptedCharRating.get(j), i);
                if (currentSymbolJ=='\uFFFD')
                    return -1;
                for (int k = 0; k < representativeCharRating.size(); k++) {
                    char currentSymbolK= representativeCharRating.get(k);
                    if (currentSymbolJ == currentSymbolK) {
                        otklon = j - k;
                        break;
                    }
                }
                otklonSum += Math.pow(otklon, 2);
                otklon = encryptedCharRating.size();
            }
            if (minOtklonSum>otklonSum)
            {
                minOtklonSum=otklonSum;
                bestKey=i;
                if (minOtklonSum==0)
                    break;
            }
            otklonSum=0;
        }
        return bestKey;

    }

    public List<Character> findMostFrequentlySymbols(String text) {
        Map<Character, Integer> symolsFrequentlyMap = new HashMap<>();

        for (char symbol : text.toCharArray()) {
            symolsFrequentlyMap.put(symbol, symolsFrequentlyMap.getOrDefault(symbol, 0) + 1);
        }

        List<Map.Entry<Character, Integer>> listWithEntry = new ArrayList<>(symolsFrequentlyMap.entrySet());
        listWithEntry.sort(Map.Entry.<Character, Integer>comparingByValue().reversed());

        List<Character> listRezult = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : listWithEntry)
        {
            listRezult.add(entry.getKey());
        }
        return listRezult;
    }
}
