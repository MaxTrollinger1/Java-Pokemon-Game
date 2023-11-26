package main;

import entity.Moves;
import entity.Pokemon;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.Random;

public class BattleManager {

    boolean isBattleTurn = true;
    boolean alreadyChosen = false;

    public boolean showAttackUI = false;
    public boolean playerWon = false;

    public boolean battleOver = false;

    int playerHealth;
    int enemyHealth;

    GamePanel gp;
    Enemy currentEnemy;

    public void InitializeBattle(GamePanel gp)
    {
        this.gp = gp;

        isBattleTurn = true;
        showAttackUI = true;
        battleOver = false;
        playerWon = false;

        Random random = new Random();
        int randomIndex = random.nextInt(gp.enemies.length);
        currentEnemy = gp.enemies[randomIndex];

        playerHealth = gp.player.currentStarter.getHP();
        enemyHealth = currentEnemy.getHP();

        gp.uiHandler.battleText = "A wild " + currentEnemy.getName() + " appeared!";
        HandleBattle();
    }

    public void HandleBattle() {
        // Handles Switching Turns Based On the boolean isBattleTurn
        if(battleOver) return;
        if (!isBattleTurn) { // Enemy Turn
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.schedule(this::handleAITurn, 2, TimeUnit.SECONDS);
        } else { // Player Turn
            showAttackUI = true;
        }
    }

    private void handleAITurn() {
        // Handling the AI combat
        if(battleOver) return;

        Moves move = currentEnemy.getRandomMove();

        // determine if attack is going to land
        int randomValue = new Random().nextInt(101);

        if(randomValue <= move.getAccuracy()) { // Attack Landed
            gp.uiHandler.battleText = currentEnemy.getName() + " used " + move.getMoveName() + " dealing " + Integer.toString(move.getBasePower()) + " damage!";
            gp.playSFX(move.getAttackSound(), 0.3f);
            AttackPlayer(move.getBasePower());
        }
        else // Attack Missed
        {
            gp.uiHandler.battleText = currentEnemy.getName() + " used " + move.getMoveName() + " and Missed!";
        }

        // Wait 2 Seconds until resetting and moving on. This buffer is for game pacing

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(this::AIUsedAttack, 2, TimeUnit.SECONDS);
    }

    void AIUsedAttack()
    {
        // Reset AI attack, switch turn to player
        isBattleTurn = true;
        HandleBattle();
    }

    public void PlayerAttack(Pokemon pokemon, Moves move)
    {
        // Handle Players attack. Wait for input then handle attack
        if(battleOver) return;
        if(isBattleTurn && !alreadyChosen)
        {
            alreadyChosen = true;
            showAttackUI = false;

            // determine if attack is going to land
            int randomValue = new Random().nextInt(101);

            if(randomValue <= move.getAccuracy()) // Attack Landed
            {
                gp.uiHandler.battleText = pokemon.getName() + " used " + move.getMoveName() + " dealing " + Integer.toString(move.getBasePower()) + " damage!";
                gp.playSFX(move.getAttackSound(), 0.3f);
                AttackEnemy(move.getBasePower());
            }
            else // Attack Missed
            {
                gp.uiHandler.battleText = pokemon.getName() + " used " + move.getMoveName() + " and Missed!";
            }

            // Add 2 seconds buffer for pacing
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.schedule(this::PlayerUsedAttack, 2, TimeUnit.SECONDS);
        }
    }

    void PlayerUsedAttack()
    {
        // Reset Players attack back to the enemies turn
        isBattleTurn = false;
        alreadyChosen = false;
        HandleBattle();
    }

    void AttackPlayer(int value)
    {
        // Handle player health
        playerHealth -= value;

        if(playerHealth <= 0)
        {
            // Player Faint
            playerWon = false;
            battleOver = true;
            gp.stopMusic();
            gp.playMusic(8, 0.15f);
            gp.uiHandler.battleText = gp.player.currentStarter.getName() + " has Fainted, You Lose.";
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.schedule(this::loadBackToGame, 8, TimeUnit.SECONDS);
        }
    }

    void AttackEnemy(int value)
    {
        // Handle Enemy Health
        enemyHealth -= value;

        if(enemyHealth <= 0)
        {
            // Enemy Faint
            playerWon = true;
            battleOver = true;
            gp.stopMusic();
            gp.playMusic(8, 0.15f);
            //gp.uiHandler.battleText = currentEnemy.getName() + " has Fainted, You Win!"; // No music when player loses
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.schedule(this::loadBackToGame, 8, TimeUnit.SECONDS);
        }
    }

    void loadBackToGame()
    {
        gp.HandleStateChange(gp.inGameState);
    }
}
