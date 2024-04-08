public class WizardCard extends Card {
    public WizardCard(GameBoard board) {
        super(board,"WIZRD", "Dark Wizard", 3, "A sinister and enigmatic wizard, whispering curses in the shadows.", "Damage 5 random enemy units by 1.");
    }

    @Override
    public void deploy(GameBoard board, int player, int position) {
        board.damageRandomEnemyUnits(player, 1, 5);
    }
}
