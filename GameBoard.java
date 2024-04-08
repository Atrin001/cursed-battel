public interface GameBoard {
    void addCardToBoard(Card card, int player, int position);
    Card getCardAtPosition(int player, int position);
    Card getStrongestCard(int player);
    void damageRandomEnemyCard(int player, int damageAmount);
    void damageRandomEnemyUnits(int player, int damageAmount, int numberOfUnits);
    void damageChosenEnemyUnit(int player, int damageAmount, int position);
    void boostAllUnitsInLine(int player, int position, int boostAmount);
    void damageStrongestEnemyUnit(int player, int damageAmount);
    void suckHalfLifeFromEnemy(int player, int position);
}
