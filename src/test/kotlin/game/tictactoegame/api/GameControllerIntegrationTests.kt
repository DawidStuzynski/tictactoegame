package game.tictactoegame.api

import game.tictactoegame.api.request.MoveRequest
import game.tictactoegame.BaseIntegrationTest
import game.tictactoegame.enums.Player
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GameControllerIntegrationTests : BaseIntegrationTest() {

    @Test
    fun `createGame should return a new game`() {
        Given {
            contentType(ContentType.JSON)
        } When {
            post("/games")
        } Then {
            statusCode(200)
            body("currentPlayer", CoreMatchers.equalTo("X"))
            body("status", CoreMatchers.equalTo("IN_PROGRESS"))
        }
    }

    @Test
    fun `getGame should return the created game`() {
        val gameIdString = Given {
            contentType(ContentType.JSON)
        } When {
            post("/games")
        } Then {
            statusCode(200)
        } Extract {
            path<String>("id")
        }

        val gameId = UUID.fromString(gameIdString)

        Given {
            contentType(ContentType.JSON)
        } When {
            get("/games/$gameId")
        } Then {
            statusCode(200)
            body("id", CoreMatchers.equalTo(gameId.toString()))
        }
    }

    @Test
    fun `makeMove should update the game state`() {
        val gameIdString = Given {
            contentType(ContentType.JSON)
        } When {
            post("/games")
        } Then {
            statusCode(200)
        } Extract {
            path<String>("id")
        }

        val gameId = UUID.fromString(gameIdString)

        val moveRequest = MoveRequest(row = 0, col = 0, player = Player.X)

        Given {
            contentType(ContentType.JSON)
            body(moveRequest)
        } When {
            post("/games/$gameId/move")
        } Then {
            statusCode(200)
            body("currentPlayer", CoreMatchers.equalTo("Y"))
            body("board[0][0]", CoreMatchers.equalTo("X"))
        }
    }
}
