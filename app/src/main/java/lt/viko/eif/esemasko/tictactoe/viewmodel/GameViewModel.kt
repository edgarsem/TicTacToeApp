package lt.viko.eif.esemasko.tictactoe.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import lt.viko.eif.esemasko.tictactoe.model.UserAction
import lt.viko.eif.esemasko.tictactoe.model.CellValues
import lt.viko.eif.esemasko.tictactoe.model.CrossOutLineType
import lt.viko.eif.esemasko.tictactoe.model.GameState


class GameViewModel : ViewModel() {


    var gameState by mutableStateOf(GameState())


    val boardItems: MutableMap<Int, CellValues> = mutableMapOf<Int, CellValues>().apply {
        for (i in 0 until gameState.boardSize * gameState.boardSize) {
            this[i] = CellValues.NONE
        }
    }

    fun setGameOptions(boardSize: Int, startingPlayer: CellValues) {
        gameState =
            if (startingPlayer == CellValues.CROSS)
                gameState.copy(
                    boardSize = boardSize,
                    startingPlayer = 'X',
                    currentPlayer = 'X',
                    currentPlayerCellValue = startingPlayer
                )
            else
                gameState.copy(
                    boardSize = boardSize,
                    startingPlayer = 'O',
                    currentPlayer = 'O',
                    currentPlayerCellValue = startingPlayer
                )
        resetBoardItems(boardSize)

    }

    fun resetBoardItems(boardSize: Int) {
        boardItems.clear()
        for (i in 0 until boardSize * boardSize) {
            boardItems[i] = CellValues.NONE
        }
    }

    fun onAction(action: UserAction) {
        when (action) {
            is UserAction.BoardClick -> {
                addMarkToBoard(action.cellNumber)
            }

            UserAction.ResetButtonClick -> {
                gameReset()
            }
        }
    }


    fun onGameEndDialogDismissed() {
        gameReset()
        resetBoardItems(gameState.boardSize)

    }


    private fun gameReset() {
        gameState = gameState.copy(
            turnCount = 0,
            currentPlayer = gameState.startingPlayer,
            currentPlayerCellValue = if (gameState.startingPlayer == 'X') CellValues.CROSS else CellValues.NOUGHT,
            crossOutLineType = CrossOutLineType.NONE,
            isDraw = false,
            hasWon = false,
        )
        resetBoardItems(gameState.boardSize)
    }

    private fun addMarkToBoard(cellNumber: Int) {
        if (boardItems[cellNumber] != CellValues.NONE) {
            return
        }
        if (gameState.currentPlayerCellValue == CellValues.CROSS) {
            boardItems[cellNumber] = CellValues.CROSS
            if (hasWon()) {
                gameState = gameState.copy(
                    currentPlayerCellValue = CellValues.NONE,
                    hasWon = true
                )
            } else if (isBoardFull()) {
                gameState = gameState.copy(
                    isDraw = true,
                )
            } else {
                gameState = gameState.copy(
                    currentPlayer = 'O',
                    currentPlayerCellValue = CellValues.NOUGHT,
                    turnCount = gameState.turnCount + 1,
                )
            }
        } else if (gameState.currentPlayerCellValue == CellValues.NOUGHT) {
            boardItems[cellNumber] = CellValues.NOUGHT
            if (hasWon()) {
                gameState = gameState.copy(
                    currentPlayerCellValue = CellValues.NONE,
                    hasWon = true
                )
            } else if (isBoardFull()) {
                gameState = gameState.copy(
                    isDraw = true,
                )
            } else {
                gameState = gameState.copy(
                    currentPlayer = 'X',
                    currentPlayerCellValue = CellValues.CROSS,
                    turnCount = gameState.turnCount + 1,
                )
            }
        }
    }


    private fun isBoardFull(): Boolean {
        return !boardItems.containsValue(CellValues.NONE)
    }

    private fun assignCrossOut(crossOutStart: Int, crossOutLine: CrossOutLineType) {
        gameState = gameState.copy(
            crossOutLineStart = crossOutStart,
            crossOutLineType = crossOutLine
        )
    }

    private fun hasWon(): Boolean {
        for (i in 0 until gameState.boardSize) {
            if (checkRow((gameState.boardSize * i)) || checkColumn(i)) {
                return true
            }
        }
        return checkDiagonal() || checkAntiDiagonal()
    }


    private fun checkRow(row: Int): Boolean {
        var count = 0
        var crossOutStart = 0
        var hasCrossOutStarted = false
        for (i in row until gameState.boardSize + row) {
            if (boardItems[i] == gameState.currentPlayerCellValue) {
                count++
                if (!hasCrossOutStarted) {
                    crossOutStart = i
                    hasCrossOutStarted = true
                }
            } else {
                count = 0
                hasCrossOutStarted = false
            }
            if (count == 3) {
                assignCrossOut(crossOutStart, CrossOutLineType.HORIZONTAL)
                return true
            }
        }
        return false
    }

    private fun checkColumn(col: Int): Boolean {
        var count = 0
        var crossOutStart = 0
        var hasCrossOutStarted = false
        var i = col
        while (i < col + gameState.boardSize * (gameState.boardSize - 1) + 1) {
            if (boardItems[i] == gameState.currentPlayerCellValue) {
                count++
                if (!hasCrossOutStarted) {
                    crossOutStart = i
                    hasCrossOutStarted = true
                }
            } else {
                count = 0
                hasCrossOutStarted = false
            }
            if (count == 3) {
                assignCrossOut(crossOutStart, CrossOutLineType.VERTICAL)
                return true
            }
            i = (i + gameState.boardSize)
        }
        return false
    }


    private fun checkDiagonal(): Boolean {
        var count = 0
        var crossOutStart = 0
        var hasCrossOutStarted = false
        for (i in 0 until gameState.boardSize - 2) {
            var j = i
            while (j < gameState.boardSize * (gameState.boardSize - i)) {
                if (boardItems[j] == gameState.currentPlayerCellValue) {
                    count++
                    if (!hasCrossOutStarted) {
                        crossOutStart = j
                        hasCrossOutStarted = true
                    }
                } else {
                    count = 0
                    hasCrossOutStarted = false
                }
                if (count == 3) {
                    assignCrossOut(crossOutStart, CrossOutLineType.DIAGONAL)
                    return true
                }
                j = (j + gameState.boardSize + 1)
            }
        }
        count = 0
        for (i in 0 until gameState.boardSize - 3) {
            var j = (gameState.boardSize * (i + 1))
            while (j < gameState.boardSize * gameState.boardSize - i - 1) {
                if (boardItems[j] == gameState.currentPlayerCellValue) {
                    count++
                    if (!hasCrossOutStarted) {
                        crossOutStart = j
                        hasCrossOutStarted = true
                    }
                } else {
                    count = 0
                    hasCrossOutStarted = false
                }
                if (count == 3) {
                    assignCrossOut(crossOutStart, CrossOutLineType.DIAGONAL)
                    return true
                }
                j = (j + gameState.boardSize + 1)
            }
        }
        return false
    }

    private fun checkAntiDiagonal(): Boolean {
        var count = 0
        var crossOutStart = 0
        var hasCrossOutStarted = false
        for (i in gameState.boardSize - 1 downTo 2) {
            var j = i
            while (j <= i + (gameState.boardSize - 1) * i) {
                if (boardItems[j] == gameState.currentPlayerCellValue) {
                    count++
                    if (!hasCrossOutStarted) {
                        crossOutStart = j
                        hasCrossOutStarted = true
                    }
                } else {
                    count = 0
                    hasCrossOutStarted = false
                }
                if (count == 3) {
                    assignCrossOut(crossOutStart, CrossOutLineType.DIAGONAL1)
                    return true
                }
                j = (j + gameState.boardSize - 1)
            }
        }
        count = 0
        for (i in 0 until gameState.boardSize - 3) {
            var j = (gameState.boardSize * (i + 2) - 1)
            while (j < j + (gameState.boardSize - (j + 1) / gameState.boardSize) * (gameState.boardSize - 1)) {
                if (boardItems[j] == gameState.currentPlayerCellValue) {
                    count++
                    if (!hasCrossOutStarted) {
                        crossOutStart = j
                        hasCrossOutStarted = true
                    }
                } else {
                    count = 0
                    hasCrossOutStarted = false
                }
                if (count == 3) {
                    assignCrossOut(crossOutStart, CrossOutLineType.DIAGONAL1)
                    return true
                }
                j = (j + gameState.boardSize - 1)
            }
        }
        return false
    }


}