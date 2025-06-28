package game.tictactoegame.exception

open class BadRequestException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
