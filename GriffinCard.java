public class GriffinCard extends Card {
    public GriffinCard(GameBoard board) {
        super(board,"GRIFF", "Griffin", 3, "A majestic creature with the body of a lion and the wings of an eagle.", "Damage the strongest enemy unit by 5.");
    }

    @Override
    public void deploy(GameBoard board, int player, int position) {
        board.damageStrongestEnemyUnit(player, 5);
    }
    
}
