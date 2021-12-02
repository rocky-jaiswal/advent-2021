package de.rockyj

import kotlin.math.abs

class Submarine(var x: Long, var y: Long) {
    override fun toString(): String {
        return "${this.x}, ${this.y}"
    }
}

enum class Directions {
    FORWARD, DOWN, UP
}

fun main() {
    val instructions = fileToArr("day2_1.txt")

    day2Part1(instructions)
}

fun applyInstructions(sub: Submarine, parsedInstructions: List<Pair<Directions, Long>>) {
    parsedInstructions.forEach { instruction ->
        when(instruction.first) {
            Directions.FORWARD -> sub.x = sub.x + instruction.second
            Directions.DOWN -> sub.y = sub.y - instruction.second
            Directions.UP -> sub.y = sub.y + instruction.second
        }
    }
}

fun parseInstructions(instructions: List<String>): List<Pair<Directions, Long>> {
    return instructions.map { instruction ->
        instruction.split(Regex("\\s"))
    }.map { pairOfIns ->
        val direction = pairOfIns.first()
        val num = pairOfIns[1]

        Pair(Directions.valueOf(direction.uppercase()), num.toLong())
    }
}

fun day2Part1(instructions: List<String>) {
    val sub = Submarine(0L, 0L)

    val parsedInstructions = parseInstructions(instructions)

    applyInstructions(sub, parsedInstructions)
    println(abs(sub.x) * abs(sub.y))
}


