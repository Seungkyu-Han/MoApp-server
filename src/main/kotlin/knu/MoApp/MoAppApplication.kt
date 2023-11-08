package knu.MoApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoAppApplication

fun main(args: Array<String>) {
	runApplication<MoAppApplication>(*args)
}
