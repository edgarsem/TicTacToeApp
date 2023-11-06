package lt.viko.eif.esemasko.tictactoe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import lt.viko.eif.esemasko.tictactoe.model.CellValues
import lt.viko.eif.esemasko.tictactoe.model.GameState
import lt.viko.eif.esemasko.tictactoe.viewmodel.GameViewModel
import lt.viko.eif.esemasko.tictactoe.model.UserAction
import lt.viko.eif.esemasko.tictactoe.view.ui.theme.Purple40
import lt.viko.eif.esemasko.tictactoe.view.components.BoardBase
import lt.viko.eif.esemasko.tictactoe.view.components.Cross
import lt.viko.eif.esemasko.tictactoe.view.components.CrossOutLine
import lt.viko.eif.esemasko.tictactoe.view.components.Nought

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    navController: NavHostController
) {

    val gameState = viewModel.gameState

    if (gameState.hasWon || gameState.isDraw) {
        EndGameDialog(
            gameState = gameState,
            onDismiss = { viewModel.onGameEndDialogDismissed() },
            onConfirm = {
                viewModel.onGameEndDialogDismissed()
                navController.navigate("options")
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(horizontal = 30.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Turn: " + gameState.turnCount,
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = "Current Player: '${gameState.currentPlayer}'",
            fontSize = 25.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.Monospace
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            BoardBase(gridSize = gameState.boardSize)
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(gameState.boardSize)
            ) {
                viewModel.boardItems.forEach { (cellNumber, cellValues) ->
                    (
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clickable(
                                            interactionSource = MutableInteractionSource(),
                                            indication = null
                                        ) {
                                            viewModel.onAction(
                                                UserAction.BoardClick(cellNumber)
                                            )
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    if (cellValues == CellValues.CROSS) {
                                        Cross(gridSize = gameState.boardSize)
                                    } else if (cellValues == CellValues.NOUGHT) {
                                        Nought(gridSize = gameState.boardSize)
                                    }
                                }
                            })
                }
            }
            if (gameState.hasWon) {
                CrossOutLine(
                    gridSize = gameState.boardSize,
                    crossOutLineStart = gameState.crossOutLineStart,
                    crossOutLineType = gameState.crossOutLineType
                )
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    viewModel.resetBoardItems(gameState.boardSize)
                    viewModel.setGameOptions(
                        gameState.boardSize,
                        if (gameState.startingPlayer == 'X') CellValues.CROSS else CellValues.NOUGHT
                    )
                    navController.navigate("options")
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Purple40
                )
            ) {
                Text(
                    text = "OPTIONS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.Monospace
                )
            }
            Button(
                onClick = {
                    viewModel.onAction(
                        UserAction.ResetButtonClick
                    )
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.buttonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Purple40
                )
            ) {
                Text(
                    text = "RESET",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.Monospace
                )
            }

        }

    }
}

@Composable
fun EndGameDialog(gameState: GameState, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (gameState.hasWon) "Congratulations!" else "Game Over",
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.Monospace,
            )
        },
        text = {
            Text(
                text = when {
                    gameState.hasWon -> "Player '${gameState.currentPlayer}' has won!"
                    gameState.isDraw -> "The game is a draw."
                    else -> ""
                },
                fontSize = 15.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.Monospace
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = onConfirm,
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Purple40
                    )

                ) {
                    Text(
                        text = "OPTIONS",
                        fontSize = 15.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Monospace
                    )
                }
                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Purple40
                    )

                ) {
                    Text(
                        text = "RESET",
                        fontSize = 15.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

        },
        containerColor = Color.LightGray,
        textContentColor = Color.Black,
        titleContentColor = Color.Black

    )
}


@Preview
@Composable
fun Prev() {
    GameScreen(viewModel = GameViewModel(), navController = rememberNavController())
//    EndGameDialog(
//        gameState = GameViewModel().gameState,
//        onDismiss = { GameViewModel().onGameEndDialogDismissed() },
//        onConfirm = { GameViewModel().onGameEndDialogDismissed() }
//    )

}