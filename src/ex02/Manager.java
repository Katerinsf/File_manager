package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import static java.nio.file.StandardCopyOption.*;

public class Manager {
    private String path;

    public Manager(String pathStr) {
        File filePath = new File(pathStr);
        if (!filePath.exists() || !filePath.isAbsolute()) {
            System.err.println("This path is not absolute");
            System.exit(-1);
        }
        path = pathStr;
    }

    public void launch() {
        Scanner scanner = new Scanner(System.in);
        String[] command;
        System.out.println(path);
        do {
            command = scanner.nextLine().trim().split(" ");
            performCommand(command);
        } while (!command[0].equals("exit"));
    }

    private void performCommand(String[] command) {
        switch (command[0]) {
            case "ls":
                ls(command);
                break;
            case "mv":
                mv(command);
                break;
            case "cd":
                cd(command);
                break;
            case "exit":
                break;
            default:
                System.out.println("This command is not exists");
        }
    }

    private void ls(String[] command) {
        File filePath = new File(path);
        if (command.length == 1) {
            File[] list = filePath.listFiles();
            for (File file : list) {
                double size = 0;
                if (file.isFile()) {
                    size = file.length();
                } else if (file.isDirectory()) {
                    size = getFolderSize(file);
                }
                System.out.printf("%-40s %.3f KB\n", file.getName(), size / 1024);
            }
        } else {
            System.out.println("This command has not arguments");
        }
    }

    private double getFolderSize(File filePath) {
        double size = 0;
        File[] list = filePath.listFiles();
        for (File file : list) {
            if (file.isFile()) {
                size += file.length();
            } else if (file.isDirectory()) {
                size += getFolderSize(file);
            }
        }
        return size;
    }

    private void cd(String[] command) {
        if (command.length == 2) {
            String newPath = Paths.get(path + "/" + command[1]).normalize().toString();
            File filePath = new File(newPath);
            if (!filePath.exists()) {
                System.out.println("This path is not exist");
            } else {
                path = newPath;
                System.out.println(path);
            }
        } else {
            System.out.println("This command has 1 argument");
        }
    }

    private void mv(String[] command) {
        if (command.length == 3) {
            Path folder1 = Paths.get(command[1]).normalize();
            Path folder2 = Paths.get(command[2]).normalize();
            try {
                if (!folder2.toFile().exists()) {
                    if (!folder1.toFile().renameTo(folder2.toFile())) {
                        throw new IOException("Rename error");
                    }
                } else {
                    Path res = Files.move(folder1, folder2.resolve(folder1));
                }
            } catch (IOException exception) {
                System.out.println("Illegal arguments");
            }
        } else {
            System.out.println("This command has 2 argument");
        }
    }
}
