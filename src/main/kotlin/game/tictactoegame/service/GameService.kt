package game.tictactoegame.service

import game.tictactoegame.dto.GameDto
import game.tictactoegame.enums.GameStatus
import game.tictactoegame.enums.Player
import game.tictactoegame.exception.GameNotFoundException
import game.tictactoegame.storage.GameStorage
import game.tictactoegame.storage.entity.GameEntity
import game.tictactoegame.util.BoardUtils.parseBoard
import game.tictactoegame.util.BoardUtils.serializeBoard
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
internal class GameService(private val gameStorage: GameStorage) {
    fun createGame(): GameDto {
        val board = List(3) { List(3) { null } }
        val game = GameEntity(
            id = UUID.randomUUID(),
            board = serializeBoard(board),
            currentPlayer = Player.X,
            status = GameStatus.IN_PROGRESS,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        return gameStorage.save(game).toDto()
    }

    fun getGame(gameId: UUID): GameDto {
        return gameStorage.findById(gameId)
            .orElseThrow { GameNotFoundException("Game not found") }.toDto()
    }

    @Transactional
    fun makeMove(gameId: UUID, row: Int, col: Int, player: Player): GameDto {
        val gameEntity = gameStorage.findById(gameId).orElseThrow { GameNotFoundException("Game not found") }
        val game = Game(
            board = Board(cells = parseBoard(gameEntity.board).map { it.toMutableList() }),
            currentPlayer = gameEntity.currentPlayer,
            status = gameEntity.status,
        ).makeMove(
            row = row,
            col = col,
            player = player
        )
        return gameStorage.save(gameEntity.apply { update(game) }).toDto()
    }
}
