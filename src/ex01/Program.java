package ex01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Vector;
import static java.lang.Math.*;

public class Program {
    public static void main(String[] args) {
        if (args.length == 2) {
            ArrayList<String> wordList1 = findWordList(args[0]);
            ArrayList<String> wordList2 = findWordList(args[1]);
            TreeSet<String> wordSet = getSet(wordList1, wordList2);
            createDictionary(wordSet);
            Vector<Integer> wordVector1 = createVector(wordList1, wordSet);
            Vector<Integer> wordVector2 = createVector(wordList2, wordSet);

            System.out.println(wordVector1);
            System.out.println(wordVector2);
            System.out.println("Similarity = " + getSimilarity(wordVector1, wordVector2));

        } else {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
    }

    public static ArrayList<String> findWordList(String fileName) {
        ArrayList<String> words = new ArrayList<String>();
        try (FileInputStream inputFile = new FileInputStream(fileName)) {
            Scanner scannerFile = new Scanner(inputFile);
            while (scannerFile.hasNext()) {
                words.add(scannerFile.next());
            }
            scannerFile.close();
        } catch (IOException e) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        return words;
    }

    public static TreeSet<String> getSet(ArrayList<String> wordList1, ArrayList<String> wordList2) {
        TreeSet<String> wordSet = new TreeSet<String>();
        for (String word : wordList1) {
            wordSet.add(word);
        }
        for (String word : wordList2) {
            wordSet.add(word);
        }
        return wordSet;
    }

    public static Vector<Integer> createVector(ArrayList<String> wordList, TreeSet<String> wordSet) {
        Vector<Integer> wordVector = new Vector<Integer>(wordSet.size());
        for (int i = 0; i < wordSet.size(); i++) {
            wordVector.add(0);
        }
        int i = 0;
        for (String word : wordSet) {
            for (String listElem : wordList) {
                if (listElem.equals(word)) {
                    wordVector.set(i, wordVector.get(i)+1);
                }
            }
            i++;
        }
        return wordVector;
    }

    public static void createDictionary(TreeSet<String> wordSet) {
        String fileName = "ex01/Dictionary.txt";
        try (FileOutputStream resultFile = new FileOutputStream(fileName)) {
            for (String word : wordSet) {
                resultFile.write(word.getBytes());
                if (!word.equals(wordSet.last())) {
                    resultFile.write(", ".getBytes());
                }
            }
        } catch (IOException ex) {
            System.err.println("Dictionary was not created");
            System.exit(-1);
        }
    }

    public static double getSimilarity(Vector<Integer> v1, Vector<Integer> v2) {
        double similarity = 0.0;
//        if (v1.isEmpty() || v2.isEmpty()) {
//            similarity = 0;
//        } else {
//
//        }
        int numerator = 0;
        double denominator = 0.0, sqr_v1 = 0.0, sqr_v2 = 0.0;
        for (int i = 0; i < v1.size(); i++) {
            numerator += (v1.get(i) * v2.get(i));
            sqr_v1 += (v1.get(i) * v1.get(i));
            sqr_v2 += (v2.get(i) * v2.get(i));
        }
        denominator = sqrt(sqr_v1 * sqr_v2);
        if (numerator != 0) {
            similarity = floor(numerator / denominator * 100) / 100;
        }
        return similarity;
    }
}
