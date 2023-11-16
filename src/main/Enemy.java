package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy {

    public static void main(String[] args) {
        // Create a list of moves
        List<String> moves = new ArrayList<>();
        moves.add("Move 1");
        moves.add("Move 2");
        moves.add("Move 3");
        // Add more moves as needed

        // Randomly select a move
        String selectedMove = getRandomMove(moves);

        // Print the selected move
        System.out.println("Selected Move: " + selectedMove);
    }

    private static String getRandomMove(List<String> moves) {
        Random random = new Random();
        int randomIndex = random.nextInt(moves.size());
        return moves.get(randomIndex);
    }
}

