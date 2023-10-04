package ex02;

public class Program {
    public static void main(String[] args) {
        if(args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.err.println("The argument must begin with \"--current-folder=*absolute path to the folder*\"");
            System.exit(-1);
        } else {
            String pathStr = args[0].replace("--current-folder=", "");
            Manager manager = new Manager(pathStr);
            manager.launch();
        }
    }
}
