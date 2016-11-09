import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreyzaytsev on 25/10/2016.
 */
public class QuineMcCluskeyMethod {

    private Addition additionModule;

    public QuineMcCluskeyMethod() {
        additionModule = new Addition();
    }

    public QuineMcCluskeyMethod(Addition additionModule) {
        this.additionModule = additionModule;
    }

    public List<StringBuilder> getAbsoluteDNF(String binaryVector) {
        List<StringBuilder> listToReturn = new ArrayList<>();
        StringBuilder vector = new StringBuilder(binaryVector);
        for (int i = 0; i < vector.length(); i++) {
            if (vector.charAt(i) == '1') {
                listToReturn.add(getTruthTableValue(i));
            }
        }
        return listToReturn;
    }

    public List<StringBuilder> getAbsoluteCNF(String binaryVector) {
        List<StringBuilder> listToReturn = new ArrayList<>();
        StringBuilder vector = new StringBuilder(binaryVector);
        for (int i = 0; i < vector.length(); i++) {
            if (vector.charAt(i) == '0') {
                listToReturn.add(getTruthTableValue(i));
            }
        }
        return listToReturn;
    }

    public StringBuilder getTruthTableValue(int iterations) {
        String initialBinaryNum = "0000";
        for (int i = 0; i < iterations; i++) {
            initialBinaryNum = additionModule.runAdditionOperation(initialBinaryNum, "1");
        }
        initialBinaryNum = additionModule.ensureBinaryNumberLength(4, initialBinaryNum);
        return new StringBuilder(initialBinaryNum);
    }

    public List<StringBuilder> findImplicants(List<StringBuilder> absoluteDNF) {
        absoluteDNF = new ArrayList<>(absoluteDNF);
        List<StringBuilder> listToCheck = new ArrayList<>();
        Boolean needAnotherIteration = true;
        Boolean isFirstIteration = true;
        Boolean isAdded = false;
        while (needAnotherIteration) {
            needAnotherIteration = false;
            if (!isFirstIteration) {
                absoluteDNF = new ArrayList<>(listToCheck);
                listToCheck.clear();
            }
            for (int i = 0; i < absoluteDNF.size(); i++) {
                for (int j = i; j < absoluteDNF.size(); j++) {
                    if (j != i) {
                        if (isValidForAdjusting(absoluteDNF.get(i), absoluteDNF.get(j))) {
                            StringBuilder binaryToAdd = new StringBuilder(adjustBinary(absoluteDNF.get(i), absoluteDNF.get(j)));
                            isAdded = true;
                            if (myContains(listToCheck, binaryToAdd) == false) {
                                listToCheck.add(binaryToAdd);
                                needAnotherIteration = true;
                            }
                        }
                    }
                }
                if (!isAdded) {
                    Boolean isAdjusted = false;
                    for (int i1 = 0; i1 < listToCheck.size(); i1++) {
                        if (isValidForAdjusting(absoluteDNF.get(i), listToCheck.get(i1))) {
                            isAdjusted = true;
                        }
                    }
                    if (!isAdjusted) {
                        listToCheck.add(absoluteDNF.get(i));
                    }
                }
                isAdded = false;
            }
            isFirstIteration = false;
        }
        return absoluteDNF;
    }

    public Boolean myContains(List<StringBuilder> listToCheck, StringBuilder stringToCheck) {
        String string = stringToCheck.toString();
        for (int i = 0; i < listToCheck.size(); i++) {
            if (listToCheck.get(i).toString().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isValidForAdjusting(StringBuilder firstBinary, StringBuilder secondBinary) {
        int numOfDifferences = 0;
        for (int i = 0; i < firstBinary.length(); i++) {
            if (firstBinary.charAt(i) != secondBinary.charAt(i)) {
                numOfDifferences++;
            }
        }
        return numOfDifferences == 1;
    }

    public StringBuilder adjustBinary(StringBuilder firstBinary, StringBuilder secondBinary) {
        StringBuilder binaryToBuild = new StringBuilder();
        for (int i = 0; i < firstBinary.length(); i++) {
            if (firstBinary.charAt(i) == secondBinary.charAt(i)) {
                binaryToBuild.append(firstBinary.charAt(i));
            } else {
                binaryToBuild.append("X");
            }
        }
        return binaryToBuild;
    }

    public List<StringBuilder> findCore(List<StringBuilder> vectors, List<StringBuilder> implicantions) {
        int numOfCoverage = 0;
        vectors = new ArrayList<>(vectors);
        implicantions = new ArrayList<>(implicantions);
        ArrayList<StringBuilder> core = new ArrayList<>();
        StringBuilder coreImplicant = new StringBuilder();
        for (StringBuilder vectorToCheck : vectors) {
            for (StringBuilder implicantionToCheck : implicantions) {
                if (isCovered(vectorToCheck, implicantionToCheck)) {
                    numOfCoverage++;
                    coreImplicant = implicantionToCheck;
                }
            }
            if (numOfCoverage == 1) {
                if (myContains(core, coreImplicant) == false) {
                    core.add(coreImplicant);
                }
            }
            numOfCoverage = 0;
        }
        return core;
    }

    public Boolean isCovered(StringBuilder vector, StringBuilder implicantion) {
        for (int i = 0; i < vector.length(); i++) {
            if (vector.charAt(i) != implicantion.charAt(i) && implicantion.charAt(i) != 'X') {
                return false;
            }
        }
        return true;
    }

    public List<StringBuilder> findMinForm(List<StringBuilder> vectors, List<StringBuilder> implicants) {
        vectors = new ArrayList<>(vectors);
        implicants = new ArrayList<>(implicants);
        ArrayList<StringBuilder> core = new ArrayList<>(findCore(vectors, implicants));
        implicants.removeAll(core);
        removeCoreCoverage(core, vectors);
        List<StringBuilder> result = new ArrayList<>(findOptimalCoverage(vectors, implicants));
        result.addAll(core);
        return result;
    }

    public void removeCoreCoverage(List<StringBuilder> core, List<StringBuilder> vectors) {
        List<StringBuilder> vectorsToRemove = new ArrayList<>();
        for (StringBuilder implicant : core) {
            for (StringBuilder vector : vectors) {
                if (isCovered(vector, implicant)) {
                    vectorsToRemove.add(vector);
                }
            }
        }
        vectors.removeAll(vectorsToRemove);
    }

    public List<StringBuilder> findOptimalCoverage(List<StringBuilder> vectors, List<StringBuilder> implicants) {
        int maxCoverage = 0;
        int counter = 0;
        int indexOfImplicant = 0;
        List<StringBuilder> vectorsToRemove = new ArrayList<>();
        List<StringBuilder> maxVectorsToRemove = new ArrayList<>();
        List<StringBuilder> result = new ArrayList<>();
        while (!vectors.isEmpty()) {
            for (StringBuilder implicant : implicants) {
                for (StringBuilder vector : vectors) {
                    if (isCovered(vector, implicant)) {
                        vectorsToRemove.add(vector);
                        counter++;
                    }
                }
                if (counter > maxCoverage) {
                    maxCoverage = counter;
                    indexOfImplicant = implicants.indexOf(implicant);
                    maxVectorsToRemove = new ArrayList<>(vectorsToRemove);
                }
                counter = 0;
                vectorsToRemove.clear();
            }
            result.add(implicants.get(indexOfImplicant));
            implicants.remove(indexOfImplicant);
            vectors.removeAll(maxVectorsToRemove);
            maxCoverage = 0;
            maxVectorsToRemove.clear();
        }
        return result;
    }

    public String formDMF(List<StringBuilder> minForm) {
        StringBuilder resultExpression = new StringBuilder();
        for (StringBuilder implicant : minForm) {
            for (int i = 0; i < implicant.length(); i++) {
                if (implicant.charAt(i) != 'X') {
                    if (implicant.charAt(i) == '0') {
                        resultExpression.append('!');
                    }
                    switch (i) {
                        case 0:
                            resultExpression.append('a');
                            break;
                        case 1:
                            resultExpression.append('b');
                            break;
                        case 2:
                            resultExpression.append('c');
                            break;
                        case 3:
                            resultExpression.append('d');
                            break;
                    }
                }
            }
            resultExpression.append('+');
        }
        resultExpression.setLength(resultExpression.length() - 1);
        return resultExpression.toString();
    }

    public String formCMF(List<StringBuilder> minForm) {
        StringBuilder resultExpression = new StringBuilder();
        for (StringBuilder implicant : minForm) {
            resultExpression.append('(');
            for (int i = 0; i < implicant.length(); i++) {
                if (implicant.charAt(i) != 'X') {
                    if (implicant.charAt(i) == '1') {
                        resultExpression.append('!');
                    }
                    switch (i) {
                        case 0:
                            resultExpression.append('a');
                            break;
                        case 1:
                            resultExpression.append('b');
                            break;
                        case 2:
                            resultExpression.append('c');
                            break;
                        case 3:
                            resultExpression.append('d');
                            break;
                    }
                    if (i != implicant.length() - 1) {
                        resultExpression.append('+');
                    }
                }
            }
            resultExpression.append(')');
            if (resultExpression.charAt(resultExpression.length() - 2) == '+') {
                resultExpression.deleteCharAt(resultExpression.length() - 2);
            }
            resultExpression.append('*');
        }
        resultExpression.setLength(resultExpression.length() - 1);
        return resultExpression.toString();
    }

}
