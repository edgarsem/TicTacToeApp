package lt.viko.eif.esemasko.tictactoe.model

sealed class UserAction {
    object ResetButtonClick : UserAction()
    data class BoardClick(val cellNumber: Int) : UserAction()
}