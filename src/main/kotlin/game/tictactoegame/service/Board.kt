package game.tictactoegame.service

import game.tictactoegame.enums.Player

internal data class Board(private val cells: List<MutableList<Player?>>) {
    fun isCellOccupied(row: Int, col: Int): Boolean = cells[row][col] != null
    fun placeMove(row: Int, col: Int, player: Player) { cells[row][col] = player }
    fun getCells(): List<List<Player?>> = cells
    fun isPositionValid(row: Int, col: Int): Boolean = row in 0..2 && col in 0..2
}
