import java.util.Arrays;
import java.util.Random;

/**
 * Created by andreyzaytsev on 25/10/2016.
 */
public class BinaryVectorGenerator {

    public static String generateFourVarVector() {
        char[] charArray = new char[16];
        Arrays.fill(charArray, '0');
        Random rand = new Random();
        for (int i = 0; i < charArray.length; i++) {
            int option = rand.nextInt(2);
            if (option == 0) {
                charArray[i] = '0';
            } else {
                charArray[i] = '1';
            }
        }
        return new String(charArray);
    }

}
