package game.tictactoegame.game

import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.Player
import game.tictactoegame.exception.CellOccupiedException
import game.tictactoegame.exception.InvalidBoardIndexException
import game.tictactoegame.exception.InvalidTurnException
import game.tictactoegame.service.Board
import game.tictactoegame.service.Game
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class GameTests {

    @Test
    fun `should detect horizontal win for Player X`() {
        val board = Board(
            listOf(
                mutableListOf(Player.X, Player.X, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Player.X, GameStatus.IN_PROGRESS)
        val updatedGame = game.makeMove(0, 2, Player.X)

        assertEquals(GameStatus.X_WINS, updatedGame.status)
    }

    @Test
    fun `should detect vertical win for Player Y`() {
        val board = Board(
            listOf(
                mutableListOf(Player.Y, null, null),
                mutableListOf(Player.Y, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Player.Y, GameStatus.IN_PROGRESS)
        val updatedGame = game.makeMove(2, 0, Player.Y)

        assertEquals(GameStatus.Y_WINS, updatedGame.status)
    }

    @Test
    fun `should detect diagonal win for Player X`() {
        val board = Board(
            listOf(
                mutableListOf(Player.X, null, null),
                mutableListOf(null, Player.X, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Player.X, GameStatus.IN_PROGRESS)
        val updatedGame = game.makeMove(2, 2, Player.X)

        assertEquals(GameStatus.X_WINS, updatedGame.status)
    }

    @Test
    fun `should detect anti-diagonal win for Player Y`() {
        val board = Board(
            listOf(
                mutableListOf(null, null, Player.Y),
                mutableListOf(null, Player.Y, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Player.Y, GameStatus.IN_PROGRESS)
        val updatedGame = game.makeMove(2, 0, Player.Y)

        assertEquals(GameStatus.Y_WINS, updatedGame.status)
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
        val game = Game(board, Player.X, GameStatus.IN_PROGRESS)

        assertThrows(InvalidTurnException::class.java) {
            game.makeMove(0, 0, Player.Y)
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
        val game = Game(board, Player.X, GameStatus.IN_PROGRESS)

        assertThrows(InvalidBoardIndexException::class.java) {
            game.makeMove(3, 3, Player.X)
        }
    }

    @Test
    fun `should throw exception for cell already occupied`() {
        val board = Board(
            listOf(
                mutableListOf(Player.X, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )
        val game = Game(board, Player.X, GameStatus.IN_PROGRESS)

        assertThrows(CellOccupiedException::class.java) {
            game.makeMove(0, 0, Player.X)
        }
    }
}
