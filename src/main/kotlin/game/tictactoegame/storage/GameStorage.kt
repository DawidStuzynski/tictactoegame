package game.tictactoegame.storage

import game.tictactoegame.storage.entity.GameEntity
import java.util.Optional
import java.util.UUID

internal interface GameStorage {
    fun save(game: GameEntity): GameEntity
    fun findById(gameId: UUID): Optional<GameEntity>
    fun deleteById(gameId: UUID)
    fun findAll(): List<GameEntity>
}
