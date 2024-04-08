
public abstract class Card {
    protected String shortName;
    protected String name;
    protected int power;
    protected String description;
    protected String deployDescription; // if applicable
    protected GameBoard gameBoard;
    
    public int getPower() {
        return power;
    }
    
    public Card(GameBoard board, String shortName, String name, int power, String description, String deployDescription) {
        this.gameBoard = board;
        this.shortName = shortName;
        this.name = name;
        this.power = power;
        this.description = description;
        this.deployDescription = deployDescription;
        }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public abstract void deploy(GameBoard board, int player, int position);
   
    public void boost(int amount) {
        this.power += amount;
    }
    public void damage(int amount) {
        this.power -= amount;
    }

     public String getShortName() {
        return shortName;
    }
   
    

}
 

