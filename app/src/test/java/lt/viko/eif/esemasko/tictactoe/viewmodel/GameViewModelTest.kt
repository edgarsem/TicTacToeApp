package lt.viko.eif.esemasko.tictactoe.viewmodel

import androidx.lifecycle.Observer
import lt.viko.eif.esemasko.tictactoe.model.CellValues
import lt.viko.eif.esemasko.tictactoe.model.CrossOutLineType
import lt.viko.eif.esemasko.tictactoe.model.GameState
import lt.viko.eif.esemasko.tictactoe.model.UserAction
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GameViewModelTest {

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setUp() {
        gameViewModel = GameViewModel()
    }

    @Test
    fun setGameOptions_optionsAreAppliedCorrectlySize3() {
        val boardSize = 3
        val startingPlayer = CellValues.NOUGHT
        gameViewModel.setGameOptions(boardSize, startingPlayer)

        assertEquals(3, gameViewModel.gameState.boardSize)
        assertEquals('O', gameViewModel.gameState.startingPlayer)
        assertEquals('O', gameViewModel.gameState.currentPlayer)
        assertEquals(CellValues.NOUGHT, gameViewModel.gameState.currentPlayerCellValue)

        assertEquals(9, gameViewModel.boardItems.size)
    }

    @Test
    fun setGameOptions_optionsAreAppliedCorrectlySize4() {
        val boardSize = 4
        val startingPlayer = CellValues.NOUGHT
        gameViewModel.setGameOptions(boardSize, startingPlayer)

        assertEquals(4, gameViewModel.gameState.boardSize)
        assertEquals('O', gameViewModel.gameState.startingPlayer)
        assertEquals('O', gameViewModel.gameState.currentPlayer)
        assertEquals(CellValues.NOUGHT, gameViewModel.gameState.currentPlayerCellValue)

        assertEquals(16, gameViewModel.boardItems.size)
    }

    @Test
    fun setGameOptions_optionsAreAppliedCorrectlySize7() {
        val boardSize = 7
        val startingPlayer = CellValues.NOUGHT
        gameViewModel.setGameOptions(boardSize, startingPlayer)

        assertEquals(7, gameViewModel.gameState.boardSize)
        assertEquals('O', gameViewModel.gameState.startingPlayer)
        assertEquals('O', gameViewModel.gameState.currentPlayer)
        assertEquals(CellValues.NOUGHT, gameViewModel.gameState.currentPlayerCellValue)

        assertEquals(49, gameViewModel.boardItems.size)
    }

    @Test
    fun resetBoardItems_boardItemAmountIsCorrect3() {
        val boardSize = 3

        gameViewModel.resetBoardItems(boardSize)

        assertEquals(9, gameViewModel.boardItems.size)
    }

    @Test
    fun resetBoardItems_boardItemAmountIsCorrect5() {
        val boardSize = 5

        gameViewModel.resetBoardItems(boardSize)

        assertEquals(25, gameViewModel.boardItems.size)
    }


    @Test
    fun resetBoardItems_boardItemsResetToNone() {
        val boardSize = 4

        gameViewModel.resetBoardItems(boardSize)

        for(i in 0 until 16) {
            assertEquals(CellValues.NONE, gameViewModel.boardItems[i])
        }
    }


    @Test
    fun addMarkToBoard_MarkIsAddedCorrectly() {
        val boardSize = 3
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        gameViewModel.onAction(UserAction.BoardClick(0))

        assertEquals(CellValues.CROSS, gameViewModel.boardItems[0])
        assertEquals(2, gameViewModel.gameState.turnCount)
    }

    @Test
    fun addMarkToBoard_CoupleMarksAreAddedCorrectly() {
        val boardSize = 3
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(2))
        gameViewModel.onAction(UserAction.BoardClick(8))

        assertEquals(CellValues.CROSS, gameViewModel.boardItems[0])
        assertEquals(CellValues.NOUGHT, gameViewModel.boardItems[2])
        assertEquals(CellValues.CROSS, gameViewModel.boardItems[8])
        assertEquals(4, gameViewModel.gameState.turnCount)
    }

    @Test
    fun isBoardFull_DrawIsAchieved() {
        val boardSize = 3
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        //steps to achieve draw
        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(1))
        gameViewModel.onAction(UserAction.BoardClick(2))
        gameViewModel.onAction(UserAction.BoardClick(5))
        gameViewModel.onAction(UserAction.BoardClick(8))
        gameViewModel.onAction(UserAction.BoardClick(4))
        gameViewModel.onAction(UserAction.BoardClick(3))
        gameViewModel.onAction(UserAction.BoardClick(6))
        gameViewModel.onAction(UserAction.BoardClick(7))

        assertEquals(true, gameViewModel.gameState.isDraw)
    }

    @Test
    fun hasWon_WinIsAchievedOnSize3() {
        val boardSize = 3
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        //steps for player 'X' to win diagonally
        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(1))
        gameViewModel.onAction(UserAction.BoardClick(4))
        gameViewModel.onAction(UserAction.BoardClick(5))
        gameViewModel.onAction(UserAction.BoardClick(8))

        assertEquals(true, gameViewModel.gameState.hasWon)
        assertEquals('X', gameViewModel.gameState.currentPlayer)
    }

    @Test
    fun hasWon_WinIsAchievedOnSize4() {
        val boardSize = 4
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        //steps for player 'X' to win anti-diagonally
        gameViewModel.onAction(UserAction.BoardClick(3))
        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(6))
        gameViewModel.onAction(UserAction.BoardClick(1))
        gameViewModel.onAction(UserAction.BoardClick(9))

        assertEquals(true, gameViewModel.gameState.hasWon)
        assertEquals('X', gameViewModel.gameState.currentPlayer)
    }

    @Test
    fun hasWon_WinIsAchievedOnSize5() {
        val boardSize = 5
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        //steps for player 'O' to win horizontally
        gameViewModel.onAction(UserAction.BoardClick(8))
        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(7))
        gameViewModel.onAction(UserAction.BoardClick(1))
        gameViewModel.onAction(UserAction.BoardClick(10))
        gameViewModel.onAction(UserAction.BoardClick(2))

        assertEquals(true, gameViewModel.gameState.hasWon)
        assertEquals('O', gameViewModel.gameState.currentPlayer)
    }

    @Test
    fun assignCrossOut_correctCrossOutLineIsFoundVertical() {
        val boardSize = 5
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        //steps for player 'X' to win vertically
        gameViewModel.onAction(UserAction.BoardClick(5))
        gameViewModel.onAction(UserAction.BoardClick(7))
        gameViewModel.onAction(UserAction.BoardClick(10))
        gameViewModel.onAction(UserAction.BoardClick(8))
        gameViewModel.onAction(UserAction.BoardClick(15))

        assertEquals(true, gameViewModel.gameState.hasWon)
        assertEquals(5, gameViewModel.gameState.crossOutLineStart)
        assertEquals(CrossOutLineType.VERTICAL, gameViewModel.gameState.crossOutLineType)
    }



    @Test
    fun gameReset_gameIsResetCorrectly() {
        val boardSize = 4
        val startingPlayer = CellValues.NOUGHT

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(2))
        gameViewModel.onAction(UserAction.BoardClick(4))
        gameViewModel.onAction(UserAction.BoardClick(10))

        gameViewModel.onAction(UserAction.ResetButtonClick)

        assertEquals(0, gameViewModel.gameState.turnCount)
        assertEquals('O', gameViewModel.gameState.currentPlayer)
        assertEquals(CellValues.NOUGHT, gameViewModel.gameState.currentPlayerCellValue)
        assertEquals(CrossOutLineType.NONE, gameViewModel.gameState.crossOutLineType)
        assertEquals(false, gameViewModel.gameState.isDraw)
        assertEquals(false, gameViewModel.gameState.hasWon)

        for(i in 0 until 16) {
            assertEquals(CellValues.NONE, gameViewModel.boardItems[i])
        }
    }

    @Test
    fun gameReset_gameIsResetCorrectlyAfterWin() {
        val boardSize = 3
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(1))
        gameViewModel.onAction(UserAction.BoardClick(4))
        gameViewModel.onAction(UserAction.BoardClick(5))
        gameViewModel.onAction(UserAction.BoardClick(8))

        assertEquals(true, gameViewModel.gameState.hasWon)
        assertEquals('X', gameViewModel.gameState.currentPlayer)
        assertEquals(0, gameViewModel.gameState.crossOutLineStart)
        assertEquals(CrossOutLineType.DIAGONAL, gameViewModel.gameState.crossOutLineType)

        gameViewModel.onAction(UserAction.ResetButtonClick)

        assertEquals(0, gameViewModel.gameState.turnCount)
        assertEquals('X', gameViewModel.gameState.currentPlayer)
        assertEquals(CellValues.CROSS, gameViewModel.gameState.currentPlayerCellValue)
        assertEquals(CrossOutLineType.NONE, gameViewModel.gameState.crossOutLineType)
        assertEquals(false, gameViewModel.gameState.isDraw)
        assertEquals(false, gameViewModel.gameState.hasWon)

        for(i in 0 until 9) {
            assertEquals(CellValues.NONE, gameViewModel.boardItems[i])
        }
    }

    @Test
    fun gameReset_gameIsResetCorrectlyAfterDraw() {
        val boardSize = 3
        val startingPlayer = CellValues.CROSS

        gameViewModel.setGameOptions(boardSize, startingPlayer)

        gameViewModel.onAction(UserAction.BoardClick(0))
        gameViewModel.onAction(UserAction.BoardClick(1))
        gameViewModel.onAction(UserAction.BoardClick(2))
        gameViewModel.onAction(UserAction.BoardClick(5))
        gameViewModel.onAction(UserAction.BoardClick(8))
        gameViewModel.onAction(UserAction.BoardClick(4))
        gameViewModel.onAction(UserAction.BoardClick(3))
        gameViewModel.onAction(UserAction.BoardClick(6))
        gameViewModel.onAction(UserAction.BoardClick(7))

        assertEquals(true, gameViewModel.gameState.isDraw)

        gameViewModel.onAction(UserAction.ResetButtonClick)

        assertEquals(0, gameViewModel.gameState.turnCount)
        assertEquals('X', gameViewModel.gameState.currentPlayer)
        assertEquals(CellValues.CROSS, gameViewModel.gameState.currentPlayerCellValue)
        assertEquals(CrossOutLineType.NONE, gameViewModel.gameState.crossOutLineType)
        assertEquals(false, gameViewModel.gameState.isDraw)
        assertEquals(false, gameViewModel.gameState.hasWon)

        for(i in 0 until 9) {
            assertEquals(CellValues.NONE, gameViewModel.boardItems[i])
        }
    }

}