package game.tictactoegame.api.request

import game.tictactoegame.enums.Player

internal data class MoveRequest(
    val row: Int,
    val col: Int,
    val player: Player
)
