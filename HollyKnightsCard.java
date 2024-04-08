public class HollyKnightsCard extends Card {
    public HollyKnightsCard(GameBoard board) {
        super(board,"KNIGT", "Holly Knights", 4, "Devout warriors of righteousness and divine protection.", "Boost all units in this line by 2.");
    }

    @Override
    public void deploy(GameBoard board, int player, int position) {
        board.boostAllUnitsInLine(player, position, 2);
    }
   
}
