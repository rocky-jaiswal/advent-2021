package de.rockyj

import kotlin.math.abs

class Submarine(var x: Long, var y: Long) {
    override fun toString(): String {
        return "${this.x}, ${this.y}"
    }
}

class SubmarineWithAim(var x: Long, var y: Long, var aim: Long) {
    override fun toString(): String {
        return "${this.x}, ${this.y} -> ${this.aim}"
    }
}

enum class Directions {
    FORWARD, DOWN, UP
}

fun main() {
    val instructions = fileToArr("day2_1.txt")

    day2Part1(instructions)
    day2Part2(instructions)
}

fun applyInstructionsForPart1(sub: Submarine, parsedInstructions: List<Pair<Directions, Long>>) {
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

    applyInstructionsForPart1(sub, parsedInstructions)
    println(abs(sub.x) * abs(sub.y))
}

fun applyInstructionsForPart2(sub: SubmarineWithAim, parsedInstructions: List<Pair<Directions, Long>>) {
    parsedInstructions.forEach { instruction ->
        when(instruction.first) {
            Directions.DOWN -> sub.aim = sub.aim + instruction.second
            Directions.UP -> sub.aim = sub.aim - instruction.second
            Directions.FORWARD -> {
                sub.x = sub.x + instruction.second
                sub.y = sub.y - (sub.aim * instruction.second)
            }
        }
    }
}

fun day2Part2(instructions: List<String>) {
    val subWithAim = SubmarineWithAim(0L, 0L, 0L)

    val parsedInstructions = parseInstructions(instructions)
    applyInstructionsForPart2(subWithAim, parsedInstructions)
    println(abs(subWithAim.x) * abs(subWithAim.y))
}


