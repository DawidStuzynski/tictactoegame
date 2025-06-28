package game.tictactoegame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TicTacToeGameApplication

fun main(args: Array<String>) {
	runApplication<TicTacToeGameApplication>(*args)
}
