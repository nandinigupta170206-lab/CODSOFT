import java.util.Scanner;
import java.util.Random;

public class GuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        boolean playAgain = true;

        System.out.println("Welcome to the Number Guessing Game!");

        while (playAgain) {
            int number = random.nextInt(100) + 1; // Generate random number between 1 and 100
            int attempts = 0;
            int maxAttempts = 10; // Limit to 10 attempts
            boolean guessed = false;

            System.out.println("\nI'm thinking of a number between 1 and 100. You have " + maxAttempts + " attempts to guess it.");

            while (attempts < maxAttempts && !guessed) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess == number) {
                    guessed = true;
                    // Score calculation: higher score for fewer attempts (max 10 points per round)
                    int score = maxAttempts - attempts + 1;
                    totalScore += score;
                    System.out.println("Correct! You guessed it in " + attempts + " attempts. Score for this round: " + score);
                } else if (guess < number) {
                    System.out.println("Too low! Attempts left: " + (maxAttempts - attempts));
                } else {
                    System.out.println("Too high! Attempts left: " + (maxAttempts - attempts));
                }
            }

            if (!guessed) {
                System.out.println("Sorry, you've used all " + maxAttempts + " attempts. The number was " + number + ". Score for this round: 0");
            }

            System.out.print("Do you want to play again? (y/n): ");
            String response = scanner.next();
            playAgain = response.equalsIgnoreCase("y");
        }

        System.out.println("Thanks for playing! Your total score: " + totalScore);
        scanner.close();
    }
}