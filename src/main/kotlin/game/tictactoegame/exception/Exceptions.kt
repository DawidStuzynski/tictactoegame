package game.tictactoegame.exception

class GameFinishedException(message: String) : BadRequestException(message)
class InvalidTurnException(message: String) : BadRequestException(message)
class CellOccupiedException(message: String) : BadRequestException(message)
class InvalidBoardIndexException(message: String) : BadRequestException(message)
class GameNotFoundException(message: String) : NotFoundException(message)
