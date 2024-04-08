public class PoorSoldiersCard extends Card {
    public PoorSoldiersCard(GameBoard board) {
        super(board,"SLDRS", "Poor Soldiers", 3, "Weary and impoverished soldiers, bearing the scars of conflict.", "Damage a random enemy unit by 3.");
    }
    @Override
    public void deploy(GameBoard board, int player, int position) {
        board.damageRandomEnemyCard(player, 3);
    }
   
}