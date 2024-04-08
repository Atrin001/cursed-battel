import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;




public class Main implements GameBoard {
    private ArrayList<Card>[] playerHands; 
    private Card[][] gameBoard; 
    private int currentRound;
    private int[] playerScores;
    private Random random;
    private int currentPlayer; 

    public static void clearConsole() {
        for (int i = 0; i < 100; ++i) System.out.println();
    }
    
        
    public Main() {
       
        playerHands = new ArrayList[2];
        playerHands[0] = new ArrayList<>();
        playerHands[1] = new ArrayList<>();
        gameBoard = new Card[4][5];
        currentRound = 0;
        playerScores = new int[2];
        random = new Random();
        initializeGame();
    }

    private void initializeGame() {
        populateDeck();
        System.out.println("Total cards in deck after population: " + allCards.size()); // Debugging line
    
        if (allCards.size() < playerHands.length * 10) {
            System.out.println("Not enough cards to start the game. Required: " + (playerHands.length * 10) + ", Available: " + allCards.size());
            return; 
        }
    
        distributeCardsToPlayers();
        displayInitialHandInfo();
    }
    
    private void distributeCardsToPlayers() {
        for (int player = 0; player < playerHands.length; player++) {
            for (int i = 0; i < 10; i++) {
                if (!allCards.isEmpty()) {
                    Card card = allCards.remove(0);
                    playerHands[player].add(card);
                    System.out.println("Player " + player + " received card: " + card.getName()); // Debugging
                } else {
                    System.out.println("Ran out of cards while distributing to player " + player);
                }
            }
        }
    }
    
    
    private void displayInitialHandInfo() {
        for (int player = 0; player < playerHands.length; player++) {
            System.out.println("Player " + player + " has " + playerHands[player].size() + " cards.");
        }
    }
    
    private transient List<Card> allCards;
    private void populateDeck() {
        allCards = new ArrayList<>();
    
        for (int i = 0; i < 3; i++) {
            allCards.addAll(Arrays.asList(
                new PoorSoldiersCard(this),
                new WizardCard(this),
                new LegolasGreenleafCard(this),
                new HollyKnightsCard(this),
                new GriffinCard(this),
                new GiantCard(this)
            ));
        }
    
        Collections.shuffle(allCards);
        System.out.println("Deck populated with " + allCards.size() + " cards."); // Debugging
    }
    
private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
    aInputStream.defaultReadObject();
    populateDeck(); // Re-populate the allCards after deserialization.
}

private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
    aOutputStream.defaultWriteObject();
    // No need to write allCards because it's transient.
}


    private void printCurrentRoundAndTurn(int player) {
        System.out.println("Round: " + (currentRound + 1) + ", Player " + (player + 1) + "'s Turn");
    }
    
    private void printPlayerHand(int player) {
        System.out.println("Player " + player + " cards:");
        if (playerHands[player].isEmpty()) {
            System.out.println("No cards in hand.");
        } else {
            for (int i = 0; i < playerHands[player].size(); i++) {
                Card card = playerHands[player].get(i);
                System.out.println(i + ". " + card.getName() + ": Power " + card.getPower());
                System.out.println("   Ability: " + card.getDescription());
            }
        }
    }
    private void startGame() {
    Scanner scanner = new Scanner(System.in);
    Arrays.fill(playerScores, 0); 
    currentPlayer = 0; 
    while (currentRound < MAX_ROUNDS && !isGameOver()) {
        printGameBoard(); 
        printCurrentRoundAndTurn(currentPlayer);

         
        handlePlayerActions(scanner);

       if (!isGameOver()) {
                prepareForNextRound();
            }
        currentPlayer = (currentPlayer + 1) % 2;
    }

   
    scanner.close();
    declareWinner();
}

    private void handlePlayerActions(Scanner scanner) {
        if (currentPlayer == 0) {
            pcPlay();
        } else {
            System.out.println("type 'P' to pass or anything else to play...");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("P")) {
                passTurn(currentPlayer);
            } else {
                int chosenCardIndex = getPlayerCardChoice(scanner, currentPlayer);
                int chosenPosition = getPlayerPositionChoice(scanner, currentPlayer);
                playCard(currentPlayer, chosenCardIndex, chosenPosition);
            }
        }
        if (isRoundOver()) {
            endRound();
        }
    }

    
    private void endRound() {
        if ( cardsPlayedThisRound[0] == 10) {
            Arrays.fill(cardsPlayedThisRound, 0);
            prepareForNextRound();
        }
        
    }
    
    private boolean isGameOver() {
      return playerScores[0] >= ROUNDS_TO_WIN || playerScores[1] >= ROUNDS_TO_WIN || currentRound >= MAX_ROUNDS;
    }
    
    
    
    private void declareWinner() {
        if (playerScores[0] > playerScores[1]) {
            System.out.println("Player 1 wins the game!");
        } else if (playerScores[1] > playerScores[0]) {
            System.out.println("Player 2 wins the game!");
        } 
    }


    private void prepareForNextRound() {
        if (currentRound >= MAX_ROUNDS - 1 || isGameOver()) {
            return; 
        }
    
       
        if (currentRound >= 2) {
            isGameOver();
        }
        else{
        populateDeck();
        currentPlayer = 0; 
        currentRound++;
    }
    }
    
    

    
    private List<Card> getEnemyCards(int player) {
        int enemyPlayer = (player + 1) % 2; 
        List<Card> enemyCards = new ArrayList<>();
        for (Card card : gameBoard[enemyPlayer]) {
            if (card != null) {
                enemyCards.add(card);
            }
        }
        return enemyCards;
    }
    
    

    
    
    private int getPlayerCardChoice(Scanner scanner, int player) {
        int cardChoice = -1;
        while (true) { 
            try {
                System.out.println("Choose a card index (0 to " + (playerHands[player].size() - 1) + "):");
                cardChoice = scanner.nextInt();
                if (cardChoice >= 0 && cardChoice < playerHands[player].size()) {
                   
                    break;
                } else {
                   
                    System.out.println("Card index is out of bounds. Please try again.");
                }
            } catch (InputMismatchException ime) {
              
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return cardChoice;
    }
    
    private int getPlayerPositionChoice(Scanner scanner, int player) {
        int positionChoice = -1;
        int maxPosition = gameBoard[0].length - 1;
        while (true) {
            System.out.println("Choose a position index (0 to " + maxPosition + "):");
            try {
                positionChoice = scanner.nextInt();
    
                if (positionChoice >= 0 && positionChoice <= maxPosition) {
                   
                    return positionChoice; 
                } else {
                    System.out.println("Invalid position. Please try again.");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }
    
    private static final int MAX_ROUNDS = 3;
    private void resetBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = null;
            }
        }
    }
    
    public static void main(String[] args) {
        Main game = new Main();
        game.startGame();
    }
 
    private int[] cardsPlayedThisRound = new int[2]; 

    private boolean isRoundOver() {
        return cardsPlayedThisRound[0] == 10 && cardsPlayedThisRound[1] == 10;
    }

    private void playCard(int player, int cardIndex, int position) {
        int row = player == 0 ? 0 : 2;
        int col = position;
        while (row < player * 2 + 2 && gameBoard[row][col] != null) {
            row++;
        }

        if (row >= player * 2 + 2) {
            System.out.println("No available positions in this column. Please choose another position.");
            return;
        }

        // Deploy the card
        Card playedCard = playerHands[player].get(cardIndex);
        gameBoard[row][col] = playedCard;
        playedCard.deploy(this, player, col);

       
        playerHands[player].remove(cardIndex);

        cardsPlayedThisRound[player]++;
       
        if (isRoundOver()) {
            endRound();
        }
    }
   private void pcPlay() {
        if (playerHands[0].size() > 0) {
            int chosenCardIndex = random.nextInt(playerHands[0].size());
            int row = random.nextInt(2);
            int column = random.nextInt(4);
    
            while (gameBoard[row][column] != null) {
                row = random.nextInt(2); 
                column = random.nextInt(4); 
            }

    
            Card chosenCard = playerHands[0].get(chosenCardIndex);
            gameBoard[row][column] = chosenCard;
            chosenCard.deploy(this, row, column);
            cardsPlayedThisRound[0]++; 
            playerHands[0].remove(chosenCardIndex);
        } else {
            passTurn(0);
        }
    }
    
    
    
    
    private void passTurn(int player) {
        System.out.println("Player " + (player + 1) + " passes the turn.");
        if (player == 0) {
          
            playerScores[1]++;
            endRound();
        } else {
         
            playerScores[0]++;
            endRound();
        }
    }
     private static final int ROUNDS_TO_WIN = 2;
    
    

    @Override
    public void addCardToBoard(Card card, int row, int col) {
    if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[row].length) {
        System.out.println("Invalid position.");
        return;
    }

    while (gameBoard[row][col] != null) {
        return;
    }
    gameBoard[row][col] = card;
}

@Override
public Card getCardAtPosition(int player, int position) {
    if (position >= 0 && position < gameBoard[player].length) {
        return gameBoard[player][position];
    }
    return null;
}
    
@Override
public Card getStrongestCard(int player) {
    Card strongestCard = null;
    for (Card card : gameBoard[player]) {
        if (card != null && (strongestCard == null || card.getPower() > strongestCard.getPower())) {
            strongestCard = card;
        }
    }
    return strongestCard;
}

@Override
public void damageRandomEnemyUnits(int player, int damageAmount, int numberOfUnits) {
    List<Card> enemyCards = getEnemyCards(player);
    Collections.shuffle(enemyCards);
    enemyCards.stream()
        .limit(numberOfUnits) 
        .forEach(card -> card.damage(damageAmount));
}

@Override
public void damageChosenEnemyUnit(int player, int damageAmount, int position) {
    int enemyPlayer = (player == 0) ? 1 : 0;
    Card enemyCard = getCardAtPosition(enemyPlayer, position);
    if (enemyCard != null) {
        enemyCard.damage(damageAmount);
    }
}
@Override
public void boostAllUnitsInLine(int player, int position, int boostAmount) {
    for (Card card : gameBoard[player]) {
        if (card != null) {
            card.boost(boostAmount);
        }
    }
}
@Override
public void damageStrongestEnemyUnit(int player, int damageAmount) {
    int enemyPlayer = (player == 0) ? 1 : 0;
    Card strongestCard = getStrongestCard(enemyPlayer);
    if (strongestCard != null) {
        strongestCard.damage(damageAmount);
    }
}
@Override
public void suckHalfLifeFromEnemy(int player, int position) {
    int enemyPlayer = (player == 0) ? 1 : 0;
    Card enemyCard = getCardAtPosition(enemyPlayer, position);
    if (enemyCard != null) {
        int halfLife = enemyCard.getPower() / 2;
        enemyCard.damage(halfLife);
        
        Card currentCard = getCardAtPosition(player, position);
        if (currentCard != null) {
            currentCard.boost(halfLife);
        }
    }
}

public void damageRandomEnemyCard(int player, int damageAmount) {
    int opponent = (player == 0) ? 1 : 0;
    ArrayList<Integer> validPositions = new ArrayList<>();
    for (int i = 0; i < gameBoard[opponent].length; i++) {
        if (gameBoard[opponent][i] != null) {
            validPositions.add(i);
        }
    }
    
    if (!validPositions.isEmpty()) {
        int randomPosition = validPositions.get(random.nextInt(validPositions.size()));
        gameBoard[opponent][randomPosition].damage(damageAmount);
    }
}

    public void printGameBoard() {
    clearConsole();
    StringBuilder boardBuilder = new StringBuilder();
    for (Card[] row : gameBoard) {
        boardBuilder.append(createRowDisplay(row)).append("\n");
    }
    System.out.print(boardBuilder.toString());
    System.out.println("Choose a card number and place to summon (e.g., 0 to 9):");
    if (currentPlayer == 1) {
        printPlayerHand(currentPlayer);
    }
}

    private String createRowDisplay(Card[] row) {
        return Arrays.stream(row)
                     .map(card -> "| " + (card != null ? card.getShortName() + "(" + card.getPower() + ") " : "       "))
                     .collect(Collectors.joining("", "", "|"));
    }

}
