package game.tictactoegame.service.domain

import game.tictactoegame.enums.Player

internal data class Board(private val cells: List<MutableList<Player?>>) {
    fun isCellOccupied(row: Int, col: Int): Boolean = cells[row][col] != null
    fun placeMove(row: Int, col: Int, player: Player): Board {
        val updatedCells = cells.map { it.toMutableList() }
        updatedCells[row][col] = player
        return Board(updatedCells)
    }
    fun getCells(): List<List<Player?>> = cells
    fun isPositionValid(row: Int, col: Int): Boolean = row in 0..2 && col in 0..2
}
