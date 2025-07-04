package game.tictactoegame.service.domain

import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.GameStatus.DRAW
import game.tictactoegame.enums.GameStatus.IN_PROGRESS
import game.tictactoegame.enums.GameStatus.X_WINS
import game.tictactoegame.enums.GameStatus.Y_WINS
import game.tictactoegame.enums.Player
import game.tictactoegame.enums.Player.X
import game.tictactoegame.enums.Player.Y
import game.tictactoegame.exception.CellOccupiedException
import game.tictactoegame.exception.GameFinishedException
import game.tictactoegame.exception.InvalidBoardIndexException
import game.tictactoegame.exception.InvalidTurnException

internal class Game(
    val board: Board,
    val currentPlayer: Player,
    val status: GameStatus
) {
    fun makeMove(row: Int, col: Int, player: Player): Game {
        if (status != IN_PROGRESS) throw GameFinishedException("Game is already finished")
        if (currentPlayer != player) throw InvalidTurnException("Not $player's turn")
        if (board.isPositionValid(row = row, col = col).not()) throw InvalidBoardIndexException("Invalid move: row=$row, col=$col. Please select a valid position within the 3x3 board.")
        if (board.isCellOccupied(row = row, col = col)) throw CellOccupiedException("Cell occupied")
        val updatedBoard = board.placeMove(row = row, col = col, player = player)
        val updatedStatus = checkStatus(
            player = player,
            board = updatedBoard
        )
        return Game(
            board = updatedBoard,
            currentPlayer = togglePlayer(currentPlayer),
            status = updatedStatus
        )
    }

    private fun checkStatus(player: Player, board: Board): GameStatus {
        val cells = board.getCells()
        val winConditions = listOf(
            cells[0], cells[1], cells[2],
            listOf(cells[0][0], cells[1][0], cells[2][0]),
            listOf(cells[0][1], cells[1][1], cells[2][1]),
            listOf(cells[0][2], cells[1][2], cells[2][2]),
            listOf(cells[0][0], cells[1][1], cells[2][2]),
            listOf(cells[0][2], cells[1][1], cells[2][0])
        )

        if (winConditions.any { it.all { cell -> cell == player } }) {
            return if (player == X) X_WINS else Y_WINS
        }

        if (cells.flatten().all { it != null }) {
            return DRAW
        }

        return IN_PROGRESS
    }

    private fun togglePlayer(currentPlayer: Player): Player {
        return if (currentPlayer == X) Y else X
    }
}
