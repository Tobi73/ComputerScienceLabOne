import java.util.Arrays;

/**
 * Created by andreyzaytsev on 21/09/16.
 */
public class Subtraction {

    Addition additionModule;

    public Subtraction(Addition additionObject){
        this.additionModule = additionObject;
    }

    public Subtraction() {
        this.additionModule = new Addition();
    }

    public static String makeReversion(String numInString) {
        char[] numInCharArray = numInString.trim().toUpperCase().toCharArray();
        for (int i = 2; i < numInCharArray.length; i++) {
            if (numInCharArray[i] == '0') {
                numInCharArray[i] = '1';
                continue;
            }
            if (numInCharArray[i] == '1') {
                numInCharArray[i] = '0';
            }
        }
        return new String(numInCharArray);
    }

    public String runSubtractionOperation(String firstBinaryNum, String secondBinaryNum, Boolean firstIsBigger) {
        StringBuilder firstNum;
        StringBuilder secondNum;
        if (firstBinaryNum.contains("-") && !secondBinaryNum.contains("-")) {
            firstNum = new StringBuilder(secondBinaryNum);
            secondNum = new StringBuilder(firstBinaryNum);
        } else {
            firstNum = new StringBuilder(firstBinaryNum);
            secondNum = new StringBuilder(secondBinaryNum);
        }
        String sum = "";
        if (firstNum.charAt(0) == '-' && secondNum.charAt(0) == '-') {
            firstNum.deleteCharAt(0);
            secondNum.deleteCharAt(0);
            sum = additionModule.runAdditionOperation(firstNum.toString(), secondNum.toString());
            return "1" + sum;
        }
        secondNum.deleteCharAt(0);
        int length;
        if (!firstIsBigger) {
            length = Math.max(secondNum.length(), firstNum.length());
            length += 2;
            firstNum = new StringBuilder(additionModule.ensureBinaryNumberLength(length, firstNum.toString()));
            secondNum = new StringBuilder(additionModule.ensureBinaryNumberLength(length, secondNum.toString()));
            secondNum.replace(1, 2, "1");
            secondNum = new StringBuilder(makeReversion(secondNum.toString()));
            secondNum = new StringBuilder(additionModule.runAdditionOperation(secondNum.toString(), "1"));
            StringBuilder sumToBuild = new StringBuilder(additionModule.runAdditionOperation(firstNum.toString(), secondNum.toString()));
            sumToBuild.delete(0, 2);
            sum = sumToBuild.toString();
        } else {
            System.out.println("I'm here!");
            length = Math.max(secondNum.length(), firstNum.length());
            length += 2;
            firstNum = new StringBuilder(additionModule.ensureBinaryNumberLength(length, firstNum.toString()));
            secondNum = new StringBuilder(additionModule.ensureBinaryNumberLength(length, secondNum.toString()));
            firstNum.replace(0, 1, "1");
            secondNum.replace(1, 2, "1");
            secondNum = new StringBuilder(makeReversion(secondNum.toString()));
            secondNum = new StringBuilder(additionModule.runAdditionOperation(secondNum.toString(), "1"));
            StringBuilder sumToBuild = new StringBuilder(additionModule.runAdditionOperation(firstNum.toString(), secondNum.toString()));
            sumToBuild.delete(0, 1);
            sum = sumToBuild.toString();
        }
        return sum;
    }

    public String turnBinaryNumNegative(String numInString) {
        if (numInString.contains("-")) {
            numInString = numInString.replace('-', '1');
            return numInString;
        } else {
            return "1" + numInString;
        }
    }

    public String turnBinaryNumNegative(String numInString, int length) {
        char[] zeroesAtAdd = new char[length + 1];
        Arrays.fill(zeroesAtAdd, '0');
        if (numInString.contains("-")) {
            StringBuilder stringToBuild = new StringBuilder(numInString);
            stringToBuild.deleteCharAt(0);
            stringToBuild.insert(0, new String(zeroesAtAdd));
            stringToBuild.insert(0, '1');
            return stringToBuild.toString();
        } else {
            StringBuilder stringToBuild = new StringBuilder(numInString);
            stringToBuild.insert(0, new String(zeroesAtAdd));
            stringToBuild.insert(0, '1');
            return stringToBuild.toString();
        }
    }

}
