/**
 * Created by andreyzaytsev on 15/10/2016.
 */
public class Multiplication {

    private Addition additionModule;

    public Multiplication(Addition additionModule) {
        this.additionModule = additionModule;
    }

    public Multiplication() {
        this.additionModule = new Addition();
    }

    public static char singleBitOR(char firstBit, char secondBit) {
        if (firstBit == '0' & firstBit == secondBit) {
            return '0';
        } else {
            return '1';
        }
    }

    public static void main(String[] args) {
        Multiplication myAppl = new Multiplication();
        String string1 = "-1110";
        String string2 = "1011";
        String result = myAppl.runMultiplicationOperation(string1, string2);
        System.out.println(result);
    }

    public String runMultiplicationOperation(String firstBinaryNum, String secondBinaryNum) {
        char firstBinaryNumSignBit = '0';
        char secondBinaryNumSignBit = '0';
        StringBuilder firstNum = new StringBuilder(firstBinaryNum);
        StringBuilder secondNum = new StringBuilder(secondBinaryNum);
        String sum = "0";
        if (firstNum.charAt(0) == '-') {
            firstNum.deleteCharAt(0);
            firstBinaryNumSignBit = '1';
        }
        if (secondNum.charAt(0) == '-') {
            secondNum.deleteCharAt(0);
            secondBinaryNumSignBit = '1';
        }
        firstBinaryNum = firstNum.toString();
        secondBinaryNum = secondNum.toString();
        firstBinaryNum = Converter.ensureBinaryNumForm(firstBinaryNum);
        secondBinaryNum = Converter.ensureBinaryNumForm(secondBinaryNum);
        System.out.println(firstBinaryNum);
        System.out.println(secondBinaryNum);
        for (int i = 0; i < secondBinaryNum.length(); i++) {
            if (secondBinaryNum.charAt(i) == '1') {
                sum = additionModule.runAdditionOperation(sum, firstBinaryNum);
            }
            if (i < secondBinaryNum.length() - 1) {
                sum = sum + '0';
            }
            System.out.println(sum);
        }
        sum = Addition.singleBitXOR(firstBinaryNumSignBit, secondBinaryNumSignBit) + sum;
        return sum;
    }


}
