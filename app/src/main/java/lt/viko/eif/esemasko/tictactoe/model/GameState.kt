package lt.viko.eif.esemasko.tictactoe.model


data class GameState(
    val boardSize: Int = 3,
    val turnCount: Int = 1,
    val startingPlayer: Char = 'X',
    val currentPlayer: Char = 'X',
    val currentPlayerCellValue: CellValues = CellValues.CROSS,
    val crossOutLineType: CrossOutLineType = CrossOutLineType.NONE,
    val crossOutLineStart: Int = 0,
    val isDraw: Boolean = false,
    val hasWon: Boolean = false
)

enum class CellValues {
    CROSS,
    NOUGHT,
    NONE
}

enum class CrossOutLineType {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL1,
    DIAGONAL,
    NONE
}