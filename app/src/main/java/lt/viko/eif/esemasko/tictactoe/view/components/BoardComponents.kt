package lt.viko.eif.esemasko.tictactoe.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lt.viko.eif.esemasko.tictactoe.model.CrossOutLineType
import lt.viko.eif.esemasko.tictactoe.view.ui.theme.LightBlue


@Composable
fun BoardBase(gridSize: Int) {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .padding(10.dp)
    ) {
        val cellSizeX = size.width / gridSize
        val cellSizeY = size.height / gridSize

        for (i in 1 until gridSize) {
            val x = cellSizeX * i
            drawLine(
                strokeWidth = 5f,
                color = Color.Gray,
                cap = StrokeCap.Round,
                start = Offset(x, 0f),
                end = Offset(x, size.height)
            )
        }

        for (i in 1 until gridSize) {
            val y = cellSizeY * i
            drawLine(
                strokeWidth = 5f,
                color = Color.Gray,
                cap = StrokeCap.Round,
                start = Offset(0f, y),
                end = Offset(size.width, y)
            )
        }

    }
}

@Composable
fun Cross(gridSize: Int) {
    Canvas(
        modifier = Modifier
            .size((180 / gridSize).dp)
            .padding(5.dp)
    ) {
        drawLine(
            strokeWidth = 15f,
            color = LightBlue,
            cap = StrokeCap.Square,
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )
        drawLine(
            strokeWidth = 15f,
            color = LightBlue,
            cap = StrokeCap.Square,
            start = Offset(size.width, 0f),
            end = Offset(0f, size.height)
        )
    }
}

@Composable
fun Nought(gridSize: Int) {
    Canvas(
        modifier = Modifier
            .size((180 / gridSize).dp)
            .padding(5.dp)
    ) {
        drawCircle(
            color = Color.Green,
            style = Stroke(width = 15f)
        )
    }
}

//@Composable
//fun HorizontalCrossOut(gridSize: Int, crossOutLineStartX: Float, crossOutLineStartY: Float) {
//    Canvas(
//        modifier = Modifier
//            .size(300.dp)
//            .padding(5.dp)
//    ) {
//        drawLine(
//            strokeWidth = 15f,
//            color = Color.Red,
//            cap = StrokeCap.Round,
//            start = Offset(crossOutLineStartX, crossOutLineStartY),
//            end = Offset(crossOutLineStartX + (size.width / gridSize * 2), crossOutLineStartY)
//        )
//    }
//}
//
//@Composable
//fun VerticalCrossOut(gridSize: Int, crossOutLineStartX: Float, crossOutLineStartY: Float) {
//    Canvas(
//        modifier = Modifier
//            .size(300.dp)
//            .padding(5.dp)
//    ) {
//        drawLine(
//            strokeWidth = 15f,
//            color = Color.Red,
//            cap = StrokeCap.Round,
//            start = Offset(crossOutLineStartX, crossOutLineStartY),
//            end = Offset(crossOutLineStartX, crossOutLineStartY + (size.height / gridSize * 2))
//        )
//    }
//}
//
//@Composable
//fun DiagonalCrossOut1(gridSize: Int, crossOutLineStartX: Float, crossOutLineStartY: Float) {
//    Canvas(
//        modifier = Modifier
//            .size((900 / gridSize).dp)
//            .padding(5.dp)
//    ) {
//        drawLine(
//            strokeWidth = 15f,
//            color = Color.Red,
//            cap = StrokeCap.Round,
//            start = Offset(crossOutLineStartX, crossOutLineStartY),
//            end = Offset(crossOutLineStartX + (300 / gridSize * 2), crossOutLineStartY + (300 / gridSize * 2))
//        )
//    }
//}
//
//@Composable
//fun DiagonalCrossOut2(gridSize: Int, crossOutLineStartX: Float, crossOutLineStartY: Float) {
//    Canvas(
//        modifier = Modifier
//            .size(300.dp)
//            .padding(5.dp)
//    ) {
//        drawLine(
//            strokeWidth = 15f,
//            color = Color.Red,
//            cap = StrokeCap.Round,
//            start = Offset(crossOutLineStartX, crossOutLineStartY),
//            end = Offset(crossOutLineStartX - (size.width / gridSize * 2), crossOutLineStartY + (size.height / gridSize * 2))
//        )
//    }
//}

@Composable
fun CrossOutLine(gridSize: Int, crossOutLineStart: Int, crossOutLineType: CrossOutLineType) {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .padding(5.dp)
    ) {
        val cellSize = size.width / gridSize
        val startX = (crossOutLineStart % gridSize) * cellSize
        val startY = (crossOutLineStart / gridSize) * cellSize

        val endX = when (crossOutLineType) {
            CrossOutLineType.HORIZONTAL -> startX + (cellSize * 2)
            CrossOutLineType.VERTICAL -> startX
            CrossOutLineType.DIAGONAL -> startX + (cellSize * 2)
            CrossOutLineType.DIAGONAL1 -> startX - (cellSize * 2)
            else -> startX
        }

        val endY = when (crossOutLineType) {
            CrossOutLineType.HORIZONTAL -> startY
            CrossOutLineType.VERTICAL -> startY + (cellSize * 2)
            CrossOutLineType.DIAGONAL -> startY + (cellSize * 2)
            CrossOutLineType.DIAGONAL1 -> startY + (cellSize * 2)
            else -> startY
        }

        drawLine(
            strokeWidth = 15f,
            color = Color.Red,
            cap = StrokeCap.Round,
            start = Offset(
                startX + cellSize / 2,
                startY + cellSize / 2
            ), // Centering the line in the cell
            end = Offset(endX + cellSize / 2, endY + cellSize / 2)
        )
    }
}

@Preview
@Composable
fun Preview() {
    //Nought(5)
    BoardBase(5)
    //Cross(5)
    //DiagonalCrossOut2(gridSize = 5, crossOutLineStartX = 112F, crossOutLineStartY = 112F)
    //HorizontalCrossOut(gridSize = 3, crossOutLineStartX = 112F, crossOutLineStartY = 112F)
    //VerticalCrossOut(gridSize = 4, crossOutLineStartX = 112F, crossOutLineStartY = 112F)
    //DiagonalCrossOut1(gridSize = 4, crossOutLineStartX = 112F, crossOutLineStartY = 112F)
    //(gridSize = 4, crossOutLineStartX = 112F, crossOutLineStartY = 112F)
    CrossOutLine(gridSize = 5, crossOutLineStart = 7, CrossOutLineType.DIAGONAL)
}