package lt.viko.eif.esemasko.tictactoe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import lt.viko.eif.esemasko.tictactoe.view.components.BoardBase
import lt.viko.eif.esemasko.tictactoe.view.components.Cross
import lt.viko.eif.esemasko.tictactoe.view.components.Nought
import lt.viko.eif.esemasko.tictactoe.viewmodel.GameViewModel
import lt.viko.eif.esemasko.tictactoe.view.ui.theme.Purple40

@Composable
fun OptionsScreen(viewModel: GameViewModel, navController: NavHostController) {

    val gameState = viewModel.gameState

    var selectedGridSize by remember { mutableStateOf(gameState.boardSize) }
    var selectedFirstMark by remember { mutableStateOf(gameState.currentPlayerCellValue) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(horizontal = 10.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 40.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = "Choose board size:",
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.Monospace
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf(3, 4, 5).forEach { size ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BoardBox(
                        isSelected = size == selectedGridSize,
                        size = size,
                        onClick = { selectedGridSize = size }
                    )
                    Text(
                        text = "${size}x${size}",
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

        }
        Text(
            text = "Choose who starts first:",
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.Monospace
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf(CellValues.CROSS, CellValues.NOUGHT).forEach { current ->
                MarkBox(
                    isSelected = current == selectedFirstMark,
                    cellValue = current,
                    onClick = { selectedFirstMark = current }
                )
            }

        }
        Button(
            onClick = {
                viewModel.setGameOptions(selectedGridSize, selectedFirstMark)
                navController.navigate("game")
            },
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Purple40
            )
        ) {
            Text(
                text = "START",
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.Monospace
            )
        }


    }
}


@Composable
fun BoardBox(isSelected: Boolean, size: Int, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .size(110.dp)
            .aspectRatio(1f)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Purple40 else Color.LightGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        BoardBase(gridSize = size)
    }
}

@Composable
fun MarkBox(isSelected: Boolean, cellValue: CellValues, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(110.dp)
            .aspectRatio(1f)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) Purple40 else Color.LightGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (cellValue == CellValues.CROSS)
            Cross(gridSize = 3)
        else
            Nought(gridSize = 3)
    }
}


@Preview
@Composable
fun Previe() {
    OptionsScreen(viewModel = GameViewModel(), navController = rememberNavController())

}
