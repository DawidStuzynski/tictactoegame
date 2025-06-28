package game.tictactoegame.storage

import game.tictactoegame.storage.entity.GameEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
internal interface GameRepository : JpaRepository<GameEntity, UUID>
