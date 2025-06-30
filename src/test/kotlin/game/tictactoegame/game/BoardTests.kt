package game.tictactoegame.game

import game.tictactoegame.enums.Player
import game.tictactoegame.service.domain.Board
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BoardTests {

    @Test
    fun `should detect if cell is occupied`() {
        val board = Board(
            listOf(
                mutableListOf(Player.X, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )

        assertTrue(board.isCellOccupied(0, 0))
        assertFalse(board.isCellOccupied(1, 1))
    }

    @Test
    fun `should place a move on the board`() {
        val board = Board(
            listOf(
                mutableListOf(null, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )

        val updatedBoard = board.placeMove(0, 0, Player.X)

        assertEquals(Player.X, updatedBoard.getCells()[0][0])
    }

    @Test
    fun `should validate position within board boundaries`() {
        val board = Board(
            listOf(
                mutableListOf(null, null, null),
                mutableListOf(null, null, null),
                mutableListOf(null, null, null)
            )
        )

        assertTrue(board.isPositionValid(0, 0))
        assertFalse(board.isPositionValid(3, 3))
        assertFalse(board.isPositionValid(-1, 0))
    }
}
