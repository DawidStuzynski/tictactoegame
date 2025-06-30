package game.tictactoegame.storage.entity

import game.tictactoegame.dto.GameDto
import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.Player
import game.tictactoegame.service.domain.Game
import game.tictactoegame.util.BoardUtils
import game.tictactoegame.util.BoardUtils.serializeBoard
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "games")
internal data class GameEntity(
    @Id
    val id: UUID,
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    var board: String,
    @Enumerated(STRING)
    var currentPlayer: Player,
    @Enumerated(STRING)
    var status: GameStatus,
    @Version
    val version: Long = 0,
    val createdAt: Instant,
    var updatedAt: Instant
){
    fun toDto(): GameDto {
        return GameDto(
            id = this.id,
            board = BoardUtils.parseBoard(this.board),
            currentPlayer = this.currentPlayer,
            status = this.status,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
    fun update(game: Game) {
        this.board = serializeBoard(game.board.getCells())
        this.currentPlayer = game.currentPlayer
        this.status = game.status
        this.updatedAt = Instant.now()
    }
}
