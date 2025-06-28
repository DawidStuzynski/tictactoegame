package game.tictactoegame.service

import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.Player
import game.tictactoegame.exception.CellOccupiedException
import game.tictactoegame.exception.InvalidBoardIndexException
import game.tictactoegame.exception.InvalidTurnException


internal class Game(
    val board: Board,
    val currentPlayer: Player,
    val status: GameStatus
) {
    fun makeMove(row: Int, col: Int, player: Player): Game {
        if (currentPlayer != player) throw InvalidTurnException("Not $player's turn")
        if (board.isPositionValid(row = row,col = col).not()) throw InvalidBoardIndexException("Invalid move: row=$row, col=$col. Please select a valid position within the 3x3 board.")
        if (board.isCellOccupied(row = row, col = col)) throw CellOccupiedException("Cell occupied")
        val updatedBoard = Board(board.getCells().map { it.toMutableList() })
        updatedBoard.placeMove(row = row, col = col, player = player)
        val updatedStatus = checkStatus(
            player = player,
            board = updatedBoard
        )
        val nextPlayer = if (updatedStatus == GameStatus.IN_PROGRESS) togglePlayer(currentPlayer) else currentPlayer

        return Game(
            board = updatedBoard,
            currentPlayer = nextPlayer,
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
            return if (player == Player.X) GameStatus.X_WINS else GameStatus.Y_WINS
        }

        if (cells.flatten().all { it != null }) {
            return GameStatus.DRAW
        }

        return GameStatus.IN_PROGRESS
    }

    private fun togglePlayer(currentPlayer: Player): Player {
        return if (currentPlayer == Player.X) Player.Y else Player.X
    }
}
