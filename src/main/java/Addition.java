import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreyzaytsev on 20/09/16.
 */
public class Addition {

    private Map<String, Pair<Character, Boolean>> binarySummingMap;

    public Addition(){
        binarySummingMap = new HashMap<>();
        binarySummingMap.put("000", new Pair<>('0', false));
        binarySummingMap.put("001", new Pair<>('1', false));
        binarySummingMap.put("010", new Pair<>('1', false));
        binarySummingMap.put("100", new Pair<>('1', false));
        binarySummingMap.put("011", new Pair<>('0', true));
        binarySummingMap.put("110", new Pair<>('0', true));
        binarySummingMap.put("101", new Pair<>('0', true));
        binarySummingMap.put("111", new Pair<>('1', true));
    }

    public static char singleBitXOR(char firstBit, char secondBit) {
        if ((firstBit != '0' & secondBit != '0') & (firstBit != '1' & secondBit != '1')) {
            throw new NumberFormatException();
        }
        if (firstBit == secondBit) {
            return '0';
        } else {
            return '1';
        }
    }

    public String ensureBinaryNumberLength(int numLength, String secondBinaryNum){
        return addZeroes((Math.max(0, numLength - secondBinaryNum.length()))) + secondBinaryNum;
    }

    public String  addZeroes(int numOfZeroes){
        char[] zeroes = new char[numOfZeroes];
        Arrays.fill(zeroes, '0');
        return new String(zeroes);
    }

    public String runAdditionOperation(String firstBinaryNumber, String secondBinaryNumber) {
        int maxNumLength = Math.max(firstBinaryNumber.length(), secondBinaryNumber.length());
        firstBinaryNumber = ensureBinaryNumberLength(maxNumLength, firstBinaryNumber);
        secondBinaryNumber = ensureBinaryNumberLength(maxNumLength, secondBinaryNumber);
        String sum = findSum(firstBinaryNumber, secondBinaryNumber);
        return sum;
    }

     public  String findSum(String firstBinaryNumber, String secondBinaryNumber){
         char[] firstNumber = firstBinaryNumber.toCharArray();
         char[] secondNumber = secondBinaryNumber.toCharArray();
         StringBuilder sum = new StringBuilder("");
         StringBuilder tmpString = new StringBuilder();
         Boolean hasAdditionalOne = false;
         for(int i = firstNumber.length - 1; i >= 0; i--){
             tmpString.append(firstNumber[i]);
             tmpString.append(secondNumber[i]);
             if(hasAdditionalOne){
                 tmpString.append('1');
                 hasAdditionalOne = false;
             } else tmpString.append('0');
             Pair<Character, Boolean> resultPair = binarySummingMap.get(tmpString.toString());
             sum.append(resultPair.getKey());
             hasAdditionalOne = resultPair.getValue();
             tmpString.setLength(0);
         }
         if(hasAdditionalOne){
             sum.append('1');
         }
         sum.reverse();
         return sum.toString();
     }


}
