// Nathan Kanas - Flashcard Study App - Final Project
import java.util.*;
import java.io.*;

public class FlashcardStudyApp {
    private static Flashcard[] cards = new Flashcard[50]; // This limits the number of flashcards to 50
    private static int cardCount = 0;  // To keep track of the number of flashcards added
    private static Scanner scanner = new Scanner(System.in);
    private static String filename; // Keep track of the filename
    public static void main(String[] args) {

        System.out.println();
        System.out.println("|----------------------------------|");
        System.out.println("| Enter the filename to study from |");
        System.out.println("|   (Example: flashcards.txt)      |");
        System.out.println("|  Use format: term,definition     |");
        System.out.println("|----------------------------------|");

        filename = scanner.nextLine();
        loadCards();
        boolean running = true;
        

        while (running) {
            System.out.println("The current file is : " + filename);
            System.out.println("Loaded " + cardCount + " flashcards");
            System.out.println(" ____________________________________");
            System.out.println("| Flashcard Study App - Nathan Kanas |");
            System.out.println("|____________________________________|");
            System.out.println("|            Main Menu               |");
            System.out.println("|      1.) Add a new flashcard       |");
            System.out.println("|      2.) Study flashcards          |");
            System.out.println("|      3.) Quiz yourself             |");
            System.out.println("|      4.) Exit                      |");
            System.out.println("|____________________________________|");
            System.out.println("|       Select an option (1-4)       |");
            System.out.println("|____________________________________|");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                addCard();
            } else if (choice.equals("2")) {
                studyFlashcards();
            } else if (choice.equals("3")) {
                quizYourself();
            } else if (choice.equals("4")) {
                running = false;
                System.out.println("Exiting... Keep Studying!");
            } else {
                System.out.println("Invalid option. Please select a number between 1 and 4.");
            }
        }
    }

    // Finds and loads flashcards from file user specified
    public static void loadCards() {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            cardCount = 0;
            while (fileScanner.hasNextLine() && cardCount < cards.length) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String term = parts[0].trim();
                    String definition = parts[1].trim();
                    cards[cardCount] = new Flashcard(term, definition);
                    cardCount = cardCount + 1;
                }
            }
            fileScanner.close();
            

        } catch (Exception e) {
            System.out.println("Error loading flashcards: " + e);
        }
    }

    // Adds a new flashcard within the program and also saves it to the file for future use
    public static void addCard() {
        if (cardCount >= cards.length) {
            System.out.println("Flashcard limit reached. 50 is max per file.");
            return;
        }
        System.out.println("---- Add a New Flashcard ----");
        System.out.println("Enter the term for the new flashcard: ");
        String term = scanner.nextLine().trim();
        System.out.println("Enter the definition for the new flashcard: ");
        String definition = scanner.nextLine().trim();

        cards[cardCount] = new Flashcard(term, definition);
        cardCount = cardCount + 1;

        try {
            PrintStream output = new PrintStream(new FileOutputStream(filename, true));
            output.println(term + "," + definition);
            System.out.println("Flashcard added successfully!");
            output.close();
        }catch (Exception e) {
            System.out.println("Error saving flashcard: " + e);
        }
    }
    // Presents all flashcards so the user can view/study 
    public static void studyFlashcards() {
            if  (cardCount == 0) {
                System.out.println("No flashcards available to study, Please add some first.");
                return;
            }

            System.out.println("---- Studying Flashcards ----");
            for (int i = 0; i < cardCount; i++) {
                System.out.println("Term: " + cards[i].getTerm());
                System.out.println("Definition: " + cards[i].getDefinition());
                System.out.println("-----------------------------");
            }

    }
    // Takes all loaded flashcards and quizzes user, gives a grade and percentage at the end
    public static void quizYourself() {
         if (cardCount == 0) {
                System.out.println("No flashcards available to quiz, Please add some first.");
          return;
    }   

    System.out.println("How would you like to be quizzed?");
    System.out.println("1.) Term");
    System.out.println("2.) Definition");
    System.out.println("Select 1 or 2:");
    System.out.println("-------------------");

    String choice = scanner.nextLine();

    int totalQuestions = cardCount;
    int correctAnswers = 0;

    
    for (int i = 0; i < cardCount; i++) {
        Flashcard card = cards[i];
            if (choice.equals("1")) {
                System.out.println("-------------------");
                System.out.println("What is: " + card.getTerm() + "?:");
                String answer = scanner.nextLine().trim();
                if (card.getDefinition().toLowerCase().contains(answer.toLowerCase())) { //Makes it so user can input semi correct answers
                    System.out.println("Correct!");
                    System.out.println("-------------------");
                    correctAnswers++;
                }
                 else {
                    System.out.println("Incorrect.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Which term matches: " + card.getDefinition() + "?:");
                String answer = scanner.nextLine().trim(); 
                if (card.getDefinition().toLowerCase().contains(answer.toLowerCase())) { //Makes it so user can input semi correct answers
                    System.out.println("Correct!");
                    System.out.println("-------------------");
                    correctAnswers++;
                } else {
                    System.out.println("Incorrect.");
                }
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
                return;
            }
        }
             double percentage = ((double) correctAnswers / totalQuestions) * 100; // Calculate percentage
             String grade;
             if (percentage >= 90) {
                 grade = "A";
             } else if (percentage >= 80) {
                 grade = "B";
             } else if (percentage >= 70) {
                 grade = "C";
             } else if (percentage >= 60) {
                 grade = "D";
             } else {
                 grade = "F";
             }
                System.out.println("Quiz Complete!");
                System.out.println("You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.");
                System.out.println("Your score: " + percentage + "%");
                System.out.println("Your grade: " + grade);
                
                
}
}