package game.tictactoegame.storage

import game.tictactoegame.storage.entity.GameEntity
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
internal class GamePostgresStorage(private val repository: GameRepository) : GameStorage {
    override fun save(game: GameEntity): GameEntity {
        return repository.save(game)
    }

    override fun findById(gameId: UUID): Optional<GameEntity> {
        return repository.findById(gameId)
    }

    override fun deleteById(gameId: UUID) {
        repository.deleteById(gameId)
    }

    override fun findAll(): List<GameEntity> {
        return repository.findAll()
    }
}
