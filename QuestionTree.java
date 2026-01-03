import java.io.*;
import java.util.*;

/**
 * This class represents a binary question tree used for a guessing game.
 * The tree expands dynamically as it learns new questions from the user.
 */
 //@author Heven Gebrehiwot 
 
public class QuestionTree {
    private UserInterface my;
    private QuestionNode root;
    private int totalGames;
    private int gamesWon;

    /**
     * Constructs a new QuestionTree with a default starting question.
     * 
     * @param ui The user interface for interacting with the player.
     */
    public QuestionTree(UserInterface ui) {
        my = ui;
        root = new QuestionNode("Is it an animal?"); // Default starting question
    }

    /**
     * Starts a new game session and updates the tree as necessary.
     */
    public void play() {
        my.println("Think of an item, and I will guess it.");
        root = playGame(root);
        totalGames++;
    }

    /**
     * Plays a game by navigating the question tree and updating it if necessary.
     * 
     * @param node The current node in the tree.
     * @return The updated node after the game.
     */
    private QuestionNode playGame(QuestionNode node) {
        if (node.isLeaf()) {
            my.print("Is it " + node.data + "?");
            if (my.nextBoolean()) {
                my.println("I guessed it!");
                gamesWon++;
                return node;
            } else {
                return learnNewQuestion(node);
            }
        } else {
            my.print(node.data);
            if (my.nextBoolean()) {
                node.yes = playGame(node.yes);
            } else {
                node.no = playGame(node.no);
            }
            return node;
        }
    }

    /**
     * Learns a new question from the user when the program makes an incorrect guess.
     * 
     * @param node The incorrect guess node.
     * @return The updated question node.
     */
    private QuestionNode learnNewQuestion(QuestionNode node) {
        my.print("I give up! What were you thinking of?");
        String newAnswer = my.nextLine();
        my.print("Give me a yes/no question to distinguish " + newAnswer + " from " + node.data);
        String newQuestion = my.nextLine();
        my.print("For " + newAnswer + ", what is the answer to that question? (yes/no)");
        boolean answerIsYes = my.nextBoolean();

        QuestionNode newNode = new QuestionNode(newQuestion);
        if (answerIsYes) {
            newNode.yes = new QuestionNode(newAnswer);
            newNode.no = node;
        } else {
            newNode.no = new QuestionNode(newAnswer);
            newNode.yes = node;
        }
        return newNode;
    }

    /**
     * Saves the current question tree to an output stream.
     * 
     * @param output The PrintStream to write the tree data.
     */
    public void save(PrintStream output) {
        saveTree(output, root);
    }

    /**
     * Recursively saves the question tree to an output stream.
     * 
     * @param output The PrintStream to write the tree data.
     * @param node The current node being saved.
     */
    private void saveTree(PrintStream output, QuestionNode node) {
        if (node == null) return;
        output.println(node.isLeaf() ? "A:" + node.data : "Q:" + node.data);
        saveTree(output, node.yes);
        saveTree(output, node.no);
    }

    /**
     * Loads a previously saved question tree from an input stream.
     * 
     * @param input The Scanner to read the tree data.
     */
    public void load(Scanner input) {
        if (input.hasNext()) {
            root = loadTree(input);
        }
    }

    /**
     * Recursively loads a question tree from an input stream.
     * 
     * @param input The Scanner to read the tree data.
     * @return The root of the loaded question tree.
     */
    private QuestionNode loadTree(Scanner input) {
        if (!input.hasNextLine()) return null;
        String line = input.nextLine();
        if (line.startsWith("Q:")) {
            QuestionNode node = new QuestionNode(line.substring(2));
            node.yes = loadTree(input);
            node.no = loadTree(input);
            return node;
        } else {
            return new QuestionNode(line.substring(2));
        }
    }

    /**
     * Returns the total number of games played.
     * 
     * @return The total number of games.
     */
    public int totalGames() {
        return totalGames;
    }

    /**
     * Returns the number of games won by the computer.
     * 
     * @return The number of games won.
     */
    public int gamesWon() {
        return gamesWon;
    }
}
