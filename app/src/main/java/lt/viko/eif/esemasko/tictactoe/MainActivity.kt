package lt.viko.eif.esemasko.tictactoe

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lt.viko.eif.esemasko.tictactoe.view.ui.theme.TicTacToeTheme
import lt.viko.eif.esemasko.tictactoe.view.GameScreen
import lt.viko.eif.esemasko.tictactoe.view.OptionsScreen
import lt.viko.eif.esemasko.tictactoe.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContent {
            TicTacToeTheme {
                val viewModel = viewModel<GameViewModel>()

                val navController = rememberNavController()

                NavHost(navController, startDestination = "options") {
                    composable("options") {
                        OptionsScreen(viewModel = viewModel, navController)
                    }
                    composable("game") {
                        GameScreen(viewModel = viewModel, navController)
                    }
                }

            }
        }
    }
}
