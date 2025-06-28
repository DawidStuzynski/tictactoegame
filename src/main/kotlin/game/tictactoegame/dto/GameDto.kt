package game.tictactoegame.dto

import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.Player
import java.time.Instant
import java.util.UUID

internal data class GameDto(
    val id: UUID,
    val board: List<List<Player?>>,
    val currentPlayer: Player,
    val status: GameStatus,
    val createdAt: Instant,
    val updatedAt: Instant
)
