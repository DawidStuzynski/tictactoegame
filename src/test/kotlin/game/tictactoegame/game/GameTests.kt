package game.tictactoegame.game

import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.GameStatus.IN_PROGRESS
import game.tictactoegame.enums.GameStatus.X_WINS
import game.tictactoegame.enums.GameStatus.Y_WINS
import game.tictactoegame.enums.Player
import game.tictactoegame.enums.Player.X
import game.tictactoegame.enums.Player.Y
import game.tictactoegame.exception.CellOccupiedException
import game.tictactoegame.exception.InvalidBoardIndexException
import game.tictactoegame.exception.InvalidTurnException
import game.tictactoegame.service.domain.Board
import game.tictactoegame.service.domain.Game
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class GameTests {

    @Test
    fun `should detect horizontal win for Player X`() {
        val board = Board(
            listOf(
                mutableListOf(X, X, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, X, IN_PROGRESS)
        val updatedGame = game.makeMove(0, 2, X)

        assertEquals(X_WINS, updatedGame.status)
    }

    @Test
    fun `should detect vertical win for Player Y`() {
        val board = Board(
            listOf(
                mutableListOf(Y, null, null),
                mutableListOf(Y, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Y, IN_PROGRESS)
        val updatedGame = game.makeMove(2, 0, Y)

        assertEquals(Y_WINS, updatedGame.status)
    }

    @Test
    fun `should detect diagonal win for Player X`() {
        val board = Board(
            listOf(
                mutableListOf(X, null, null),
                mutableListOf(null, X, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, X, IN_PROGRESS)
        val updatedGame = game.makeMove(2, 2, X)

        assertEquals(X_WINS, updatedGame.status)
    }

    @Test
    fun `should detect anti-diagonal win for Player Y`() {
        val board = Board(
            listOf(
                mutableListOf(null, null, Y),
                mutableListOf(null, Y, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Y, IN_PROGRESS)
        val updatedGame = game.makeMove(2, 0, Y)

        assertEquals(Y_WINS, updatedGame.status)
    }

    @Test
    fun `should throw exception for invalid turn`() {
        val board = Board(
            listOf(
                mutableListOf(null, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, X, IN_PROGRESS)

        assertThrows(InvalidTurnException::class.java) {
            game.makeMove(0, 0, Y)
        }
    }

    @Test
    fun `should throw exception for invalid board index`() {
        val board = Board(
            listOf(
                mutableListOf(null, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, X, IN_PROGRESS)

        assertThrows(InvalidBoardIndexException::class.java) {
            game.makeMove(3, 3, X)
        }
    }

    @Test
    fun `should throw exception for cell already occupied`() {
        val board = Board(
            listOf(
                mutableListOf(X, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, X, IN_PROGRESS)

        assertThrows(CellOccupiedException::class.java) {
            game.makeMove(0, 0, X)
        }
    }
}
