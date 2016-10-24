import javafx.util.Pair;

import java.util.Arrays;

/**
 * Created by andreyzaytsev on 16/10/2016.
 */
public class Division {

    private Addition additionModule;
    private Subtraction subtractionModule;
    private Converter converterModule;

    public Division(Addition additionModule, Converter converterModule, Subtraction subtractionModule) {
        this.additionModule = additionModule;
        this.subtractionModule = subtractionModule;
        this.converterModule = converterModule;
    }

    public Division() {
        this.additionModule = new Addition();
        this.subtractionModule = new Subtraction();
        this.converterModule = new Converter();
    }

    public Pair<String, String> runDivisionOperation(String firstBinaryNum, String secondBinaryNum) {
        char firstBinaryNumSignBit = '0';
        char secondBinaryNumSignBit = '0';
        StringBuilder dividend = new StringBuilder(firstBinaryNum);
        StringBuilder divisor = new StringBuilder(secondBinaryNum);
        String sum = "0";
        if (dividend.charAt(0) == '-') {
            dividend.deleteCharAt(0);
            firstBinaryNumSignBit = '1';
        }
        if (divisor.charAt(0) == '-') {
            divisor.deleteCharAt(0);
            secondBinaryNumSignBit = '1';
        }
        int steps = dividend.length();
        Boolean isPositive;
        StringBuilder twosComplementDivisor = new StringBuilder(divisor.toString());
        twosComplementDivisor.insert(0, addZeroes((dividend.length() - divisor.length())));
        twosComplementDivisor = new StringBuilder(subtractionModule.runSubtractionOperation("-" + twosComplementDivisor.toString(), "0", false));
        StringBuilder result = new StringBuilder();
        twosComplementDivisor.delete(0, 1);
        twosComplementDivisor.append(addZeroes(dividend.length()));
        twosComplementDivisor.replace(0, 1, "1");
        divisor.insert(0, addZeroes((dividend.length() - divisor.length())));
        divisor.append(addZeroes(dividend.length()));
        dividend.insert(0, addZeroes(dividend.length()));
        dividend.delete(0, 1);
        twosComplementDivisor.setLength(twosComplementDivisor.length() - 1);
        divisor.setLength(divisor.length() - 1);
        dividend.replace(0, dividend.length(), additionModule.runAdditionOperation(dividend.toString(), twosComplementDivisor.toString()));
        dividend.delete(0, dividend.length() - divisor.length());
        if (dividend.charAt(0) == '1') {
            isPositive = false;
            result.append("0");
        } else {
            isPositive = true;
            result.append("1");
        }
        for (int i = 0; i < steps - 1; i++) {
            dividend.delete(0, 1);
            twosComplementDivisor.setLength(twosComplementDivisor.length() - 1);
            divisor.setLength(divisor.length() - 1);
            if (!isPositive) {
                dividend = new StringBuilder(additionModule.runAdditionOperation(dividend.toString(), divisor.toString()));
            } else {
                dividend = new StringBuilder(additionModule.runAdditionOperation(dividend.toString(), twosComplementDivisor.toString()));
            }
            dividend.delete(0, dividend.length() - divisor.length());
            if (dividend.charAt(0) == '1') {
                isPositive = false;
                result.append("0");
            } else {
                isPositive = true;
                result.append("1");
            }
        }
        if (dividend.charAt(0) == '1') {
            dividend = new StringBuilder(additionModule.runAdditionOperation(dividend.toString(), divisor.toString()));
            dividend.delete(0, 1);
        }
        result.insert(0, Addition.singleBitXOR(firstBinaryNumSignBit, secondBinaryNumSignBit));
        Pair<String, String> resultingPair = new Pair<>(result.toString(), dividend.toString());
        return resultingPair;
    }

    public String ensureBinaryNumberLength(int numLength, String secondBinaryNum) {
        return secondBinaryNum + addZeroes((Math.max(0, numLength - secondBinaryNum.length())));
    }

    public String addZeroes(int numOfZeroes) {
        char[] zeroes = new char[numOfZeroes];
        Arrays.fill(zeroes, '0');
        return new String(zeroes);
    }

}
