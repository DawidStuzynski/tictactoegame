package game.tictactoegame.exception

open class NotFoundException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
