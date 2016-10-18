import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreyzaytsev on 15/09/16.
 */
public class Converter {

    private List<Character> numeralSystem;

    public Converter(){
        numeralSystem = new ArrayList<>();
        numeralSystem.add(0, '0');
        numeralSystem.add(1, '1');
        numeralSystem.add(2, '2');
        numeralSystem.add(3, '3');
        numeralSystem.add(4, '4');
        numeralSystem.add(5, '5');
        numeralSystem.add(6, '6');
        numeralSystem.add(7, '7');
        numeralSystem.add(8, '8');
        numeralSystem.add(9, '9');
        numeralSystem.add(10, 'A');
        numeralSystem.add(11, 'B');
        numeralSystem.add(12, 'C');
        numeralSystem.add(13, 'D');
        numeralSystem.add(14, 'E');
        numeralSystem.add(15, 'F');
    }

    public static String ensureBinaryNumForm(String binaryNum) {
        StringBuilder stringToBuild = new StringBuilder(binaryNum);
        int numOfZeroes = 0;
        for (int i = 0; i < stringToBuild.length() - 1; i++) {
            if (stringToBuild.charAt(i) == '1') {
                break;
            }
            numOfZeroes++;
        }
        stringToBuild.delete(0, numOfZeroes);
        return stringToBuild.toString();
    }

    public Long convertToDecimal(String numberInString, Integer numericalSystem) {
        Long numInDecimal = new Long(0);
        int borderIndex = 0;
        char[] numInCharArray = numberInString.toUpperCase().trim().toCharArray();
        if (numInCharArray[0] == '-') {
            borderIndex++;
        }
        Integer position = numInCharArray.length;
        for (int i = numInCharArray.length - 1; i >= borderIndex; i--, position--) {
            Integer numToConvert = numeralSystem.indexOf(numInCharArray[i])*myPow(numericalSystem, numInCharArray.length - position);
            numInDecimal+=numToConvert;
        }
        if (borderIndex == 1) {
            numInDecimal *= -1;
        }
        return numInDecimal;
    }

    private Integer myPow(Integer num, Integer power){
        if(power == 0){
            return 1;
        }
        Integer returnValue = 1;
        for(int i = 1; i <= power; i++){
            returnValue*=num;
        }
        return returnValue;
    }

    public String convertToNumericalSys(Long numInDecimal, Integer numericalSystem) {
        Boolean isNegative = false;
        if (numInDecimal < 0) {
            numInDecimal *= -1;
            isNegative = true;
        }
        Long doublePartOfNum;
        StringBuilder numInNewNumericalSystem = new StringBuilder();
        while(numInDecimal >= numericalSystem){
            doublePartOfNum = numInDecimal%numericalSystem;
            numInDecimal/=numericalSystem;
            numInNewNumericalSystem.append(numeralSystem.get(doublePartOfNum.intValue()));
        }
        numInNewNumericalSystem.append(numeralSystem.get(numInDecimal.intValue()));
        numInNewNumericalSystem.reverse();
        if (isNegative) {
            return "-" + numInNewNumericalSystem.toString();
        }
        return numInNewNumericalSystem.toString();
    }

    /*
    public static void main(String[] args){
        String string1 = "00010000101";
        string1 = Converter.ensureBinaryNumForm(string1);
        System.out.println(string1);
    }
    */

    /*
    public static void main(String[] args){
        String first = "10100101010";
        String second = "-001";
        Converter conv = new Converter();
        Addition add = new Addition();
        first = conv.turnBinaryNumNegative(first);
        int maxLength = Math.max(first.length(), second.length());
        int minLength = Math.min(first.length(), second.length());
        int length = maxLength - minLength;

        StringBuilder sum = new StringBuilder(add.findSum(first, second));
        sum.deleteCharAt(0);
        second = conv.turnBinaryNumNegative(second, length);
        System.out.println(Integer.toString(maxLength));
        System.out.println(Integer.toString(minLength));
        System.out.println(Integer.toString(length));
        System.out.println(first);
        System.out.println(second);
        System.out.println(sum);
    }
    */

}
