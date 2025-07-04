package game.tictactoegame.game

import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.GameStatus.IN_PROGRESS
import game.tictactoegame.enums.Player
import game.tictactoegame.enums.Player.X
import game.tictactoegame.enums.Player.Y
import game.tictactoegame.exception.CellOccupiedException
import game.tictactoegame.exception.GameFinishedException
import game.tictactoegame.exception.GameNotFoundException
import game.tictactoegame.exception.InvalidBoardIndexException
import game.tictactoegame.exception.InvalidTurnException
import game.tictactoegame.game.storage.GameMockStorage
import game.tictactoegame.service.GameService
import game.tictactoegame.storage.GameStorage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

internal class GameServiceTests {

    private val storage: GameStorage = GameMockStorage()
    private val gameService = GameService(storage)

    @Test
    fun `createGame should initialize a new game`() {
        val game = gameService.createGame()

        assertNotNull(game.id)
        assertEquals(X, game.currentPlayer)
        assertEquals(IN_PROGRESS, game.status)
        assertEquals(
            listOf(
                listOf(null, null, null),
                listOf(null, null, null),
                listOf(null, null, null)
            ),
            game.board
        )
    }

    @Test
    fun `getGame should retrieve an existing game`() {
        val game = gameService.createGame()
        val retrievedGame = gameService.getGame(game.id)

        assertEquals(game.id, retrievedGame.id)
        assertEquals(game.currentPlayer, retrievedGame.currentPlayer)
    }

    @Test
    fun `makeMove should update the board and switch players`() {
        val game = gameService.createGame()
        val updatedGame = gameService.makeMove(game.id, 0, 0, X)

        assertEquals(Y, updatedGame.currentPlayer)
        assertEquals(IN_PROGRESS, updatedGame.status)
    }

    @Test
    fun `makeMove should throw exception for invalid moves`() {
        val game = gameService.createGame()

        assertThrows(InvalidTurnException::class.java) {
            gameService.makeMove(game.id, 0, 0, Y)
        }

        assertThrows(InvalidBoardIndexException::class.java) {
            gameService.makeMove(game.id, 3, 3, X)
        }
    }

    @Test
    fun `makeMove should detect a win condition`() {
        val game = gameService.createGame()
        gameService.makeMove(game.id, 0, 0, X)
        gameService.makeMove(game.id, 1, 0, Y)
        gameService.makeMove(game.id, 0, 1, X)
        gameService.makeMove(game.id, 1, 1, Y)
        gameService.makeMove(game.id, 0, 2, X)

        val updatedGame = gameService.getGame(game.id)
        assertEquals(GameStatus.X_WINS, updatedGame.status)
    }

    @Test
    fun `makeMove should detect a draw condition`() {
        val game = gameService.createGame()
        gameService.makeMove(game.id, 0, 0, X)
        gameService.makeMove(game.id, 0, 1, Y)
        gameService.makeMove(game.id, 0, 2, X)
        gameService.makeMove(game.id, 1, 1, Y)
        gameService.makeMove(game.id, 1, 0, X)
        gameService.makeMove(game.id, 1, 2, Y)
        gameService.makeMove(game.id, 2, 1, X)
        gameService.makeMove(game.id, 2, 0, Y)
        gameService.makeMove(game.id, 2, 2, X)

        val updatedGame = gameService.getGame(game.id)
        assertEquals(GameStatus.DRAW, updatedGame.status)
    }

    @Test
    fun `makeMove should throw exception for moves on an occupied cell`() {
        val game = gameService.createGame()
        gameService.makeMove(game.id, 0, 0, X)

        assertThrows(CellOccupiedException::class.java) {
            gameService.makeMove(game.id, 0, 0, Y)
        }
    }

    @Test
    fun `makeMove should throw exception for moves after game ends`() {
        val game = gameService.createGame()
        gameService.makeMove(game.id, 0, 0, X)
        gameService.makeMove(game.id, 1, 0, Y)
        gameService.makeMove(game.id, 0, 1, X)
        gameService.makeMove(game.id, 1, 1, Y)
        gameService.makeMove(game.id, 0, 2, X)

        assertThrows(GameFinishedException::class.java) {
            gameService.makeMove(game.id, 2, 2, Y)
        }
    }

    @Test
    fun `createGame should initialize a game with correct timestamps`() {
        val game = gameService.createGame()

        assertNotNull(game.createdAt)
        assertNotNull(game.updatedAt)
        assertTrue(game.createdAt <= game.updatedAt)
    }

    @Test
    fun `makeMove should update the updatedAt timestamp`() {
        val game = gameService.createGame()
        val initialUpdatedAt = game.updatedAt

        val updatedGame = gameService.makeMove(game.id, 0, 0, X)

        assertTrue(updatedGame.updatedAt > initialUpdatedAt)
    }

    @Test
    fun `makeMove should throw exception for invalid player`() {
        val game = gameService.createGame()

        assertThrows(InvalidTurnException::class.java) {
            gameService.makeMove(game.id, 0, 0, Y)
        }
    }

    @Test
    fun `makeMove should throw exception for out-of-bound indices`() {
        val game = gameService.createGame()

        assertThrows(InvalidBoardIndexException::class.java) {
            gameService.makeMove(game.id, -1, 0, X)
        }

        assertThrows(InvalidBoardIndexException::class.java) {
            gameService.makeMove(game.id, 3, 3, X)
        }
    }

    @Test
    fun `makeMove should handle consecutive moves correctly`() {
        val game = gameService.createGame()
        gameService.makeMove(game.id, 0, 0, X)
        val secondMove = gameService.makeMove(game.id, 1, 1, Y)

        assertEquals(X, secondMove.currentPlayer)
        assertEquals(IN_PROGRESS, secondMove.status)
        assertNotNull(secondMove.board[0][0])
        assertNotNull(secondMove.board[1][1])
    }

    @Test
    fun `getGame should throw exception for non-existent game`() {
        val nonExistentGameId = UUID.randomUUID()

        assertThrows(GameNotFoundException::class.java) {
            gameService.getGame(nonExistentGameId)
        }
    }

    @Test
    fun `createGame should initialize board with null values`() {
        val game = gameService.createGame()

        assertTrue(game.board.flatten().all { it == null })
    }
}
