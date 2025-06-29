package game.tictactoegame.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import game.tictactoegame.enums.Player

internal object BoardUtils {
    private val objectMapper = ObjectMapper()

    fun serializeBoard(board: List<List<Player?>>): String {
        return try {
            objectMapper.writeValueAsString(board)
        } catch (_: Exception) {
            "[]"
        }
    }

    fun parseBoard(boardJson: String): List<List<Player?>> {
        return try {
            objectMapper.readValue(boardJson, object : TypeReference<List<List<Player?>>>() {})
        } catch (_: Exception) {
            emptyList()
        }
    }
}
