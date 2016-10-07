import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
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

    public Long ConvertToDecimal(String numberInString, Integer numericalSystem){
        Long numInDecimal = new Long(0);
        char[] numInCharArray = numberInString.toUpperCase().trim().toCharArray();
        Integer position = numInCharArray.length;
        for(int i = numInCharArray.length - 1; i >= 0; i--, position--){
            Integer numToConvert = numeralSystem.indexOf(numInCharArray[i])*myPow(numericalSystem, numInCharArray.length - position);
            numInDecimal+=numToConvert;
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

    public String ConvertToNumericalSys(Long numInDecimal, Integer numericalSystem){
        Long doublePartOfNum;
        StringBuilder numInNewNumericalSystem = new StringBuilder();
        while(numInDecimal >= numericalSystem){
            doublePartOfNum = numInDecimal%numericalSystem;
            numInDecimal/=numericalSystem;
            numInNewNumericalSystem.append(numeralSystem.get(doublePartOfNum.intValue()));
        }
        numInNewNumericalSystem.append(numeralSystem.get(numInDecimal.intValue()));
        numInNewNumericalSystem.reverse();
        return numInNewNumericalSystem.toString();
    }

}
