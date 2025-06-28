package game.tictactoegame.game

import game.tictactoegame.storage.GameStorage
import game.tictactoegame.storage.entity.GameEntity
import java.util.Optional
import java.util.UUID

internal class GameMockStorage : GameStorage {
    private val games = mutableMapOf<UUID, GameEntity>()

    override fun save(game: GameEntity): GameEntity {
        games[game.id] = game
        return game
    }

    override fun findById(gameId: UUID): Optional<GameEntity> {
        return Optional.ofNullable(games[gameId])
    }

    override fun deleteById(gameId: UUID) {
        games.remove(gameId)
    }

    override fun findAll(): List<GameEntity> {
        return games.values.toList()
    }
}
