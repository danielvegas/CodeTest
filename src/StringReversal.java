import java.util.Scanner;

public class StringReversal {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Earthlings / Martians dictionary ");
        boolean quit = false;
        int choice = 0;
        printInstructions();
        while (!quit) {
            System.out.println("Please enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    printInstructions();
                    break;
                case 1:
                    System.out.println("Type in a word you want to translate: ");
                    String earthlingsWord = scanner.nextLine();
                    System.out.println("Earthlings word: " + earthlingsWord + " --------> " + " in Martians language: " + translateWords(earthlingsWord));
                    break;
                case 2:
                    quit = true;
                    System.out.println("See you soon!");
                    break;
                default:
                    break;
            }
        }
    }

    static void printInstructions() {
        System.out.println("Press: ");
        System.out.println("\t0 - To print options");
        System.out.println("\t1 - To translate your word");
        System.out.println("\t2 - To quit?");
    }

    static String translateWords(String toTranslate) {
        String martiansWord = new StringBuilder(toTranslate).reverse().toString();
        return martiansWord;
    }
}
