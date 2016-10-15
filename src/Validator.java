import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreyzaytsev on 15/09/16.
 */
public class Validator {

    private List<Character> numeralSystem;

    public Validator(){
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
        numeralSystem.add(16, 'G');
    }

    public boolean isInputValid(String numberInString, Integer numeralSystem){
        int i = 0;
        if (numberInString.isEmpty()) {
            return false;
        }
        numberInString = numberInString.trim().toUpperCase();
        char[] stringInChars = numberInString.toCharArray();
        if (stringInChars[i] == '-') {
            i++;
        }
        for (; i < stringInChars.length; i++) {
            if(!this.numeralSystem.contains(stringInChars[i])){
                return false;
            }
            if(this.numeralSystem.indexOf(stringInChars[i]) >= numeralSystem){
                return false;
            }
        }
        return true;
    }





}
