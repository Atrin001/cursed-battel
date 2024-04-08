public class GiantCard extends Card {
    public GiantCard(GameBoard board) {
        super(board,"GIANT", "Giant", 7, "Just a colossal creature.", null);
    }

    @Override
    public void deploy(GameBoard board, int player, int position) {
        // No special deploy ability
    }
   
}
