package ex00;
import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Map<String, String> signaturesList = findListSignature();
        Scanner scanner = new Scanner(System.in);
        String firstLine, fileName = scanner.nextLine();
        while (!fileName.equals("42")) {
            firstLine = getFirstLine(fileName);
            defineExtension(signaturesList, firstLine);
            fileName = scanner.nextLine();
        }

    }

    public static Map<String, String> findListSignature() {
        String fileName = "ex00/signatures.txt";
        Map<String, String> signaturesList = new HashMap<String, String>();
        String str;
        String[] signatureArray;
        try (FileInputStream inputFile = new FileInputStream(fileName)) {
            Scanner scannerFile = new Scanner(inputFile);
            while (scannerFile.hasNextLine()) {
                str = scannerFile.nextLine();
                signatureArray = str.split(",");
                signaturesList.put(signatureArray[0], signatureArray[1].replaceAll(" ", ""));
            }
            scannerFile.close();
        } catch (Exception ex) {
            System.err.println("signatures.txt not found");
            System.exit(-1);
        }
        return signaturesList;
    }

    public static String getFirstLine(String fileName) {
        String firstLine = "";
        StringBuilder strBuffer = new StringBuilder(16);
        try (FileInputStream inputFile = new FileInputStream(fileName)) {
            byte[] buffer = new byte[8];
            inputFile.read(buffer);
            for (byte b : buffer) {
                strBuffer.append(String.format("%02X", b));
            }
            firstLine = strBuffer.toString();
        } catch (IOException ignored) { }
//        System.out.println("UNDEFINED: File was not found");

        return firstLine;
    }

    public static void defineExtension(Map<String, String> signaturesList, String signature) {
        String fileName = "ex00/result.txt";
        for (Map.Entry<String, String> entry : signaturesList.entrySet()) {
            if (signature.contains(entry.getValue())) {
                try (FileOutputStream resultFile = new FileOutputStream(fileName, true)) {
                    byte[] buffer = entry.getKey().getBytes();
                    resultFile.write(buffer);
                    resultFile.write('\n');
                    System.out.println("PROCESSED");
                    return;
                } catch (IOException ex) {
                    System.out.println("Output file was not open");
                }
            }
        }
        System.out.println("UNDEFINED: signature was not defined");
    }



}


