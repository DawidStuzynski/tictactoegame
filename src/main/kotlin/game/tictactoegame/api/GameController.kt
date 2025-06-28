package game.tictactoegame.api

import game.tictactoegame.api.request.MoveRequest
import game.tictactoegame.dto.GameDto
import game.tictactoegame.service.GameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/games" )
internal class GameController(private val gameService: GameService) {

    @PostMapping
    fun createGame(): GameDto {
        return gameService.createGame()
    }

    @GetMapping("/{gameId}")
    fun getGame(@PathVariable gameId: UUID): GameDto {
        return gameService.getGame(gameId)
    }

    @PostMapping("/{gameId}/move" )
    fun makeMove(
        @PathVariable gameId: UUID,
        @RequestBody moveRequest: MoveRequest
    ): GameDto {
        return gameService.makeMove(
            gameId = gameId,
            row = moveRequest.row,
            col = moveRequest.col,
            player = moveRequest.player
        )
    }
}
