public class LegolasGreenleafCard extends Card {
    public LegolasGreenleafCard(GameBoard board) {
        super(board,"LEGOL", "Legolas Greenleaf", 3, "The Prince of the Woodland Realm and a master archer.", "Damage a chosen enemy unit by 3.");
    }

    @Override
    public void deploy(GameBoard board, int player, int position) {

        board.damageChosenEnemyUnit(player, 3, position); // damageAmount is 3
            }
   
    }

