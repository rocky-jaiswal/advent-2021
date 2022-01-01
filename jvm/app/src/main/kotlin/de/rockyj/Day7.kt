package de.rockyj

import kotlin.math.abs

private val input1 = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }
private val input2 = fileToArr("day7_1.txt").first().split(",").map { it.toInt() }

fun main() {
    day7Part1(input2)
    day7Part2(input2)
}

fun day7Part1(positions: List<Int>) {
    val min = positions.minOfOrNull { position ->
        positions.sumOf { abs(position - it) }
    }

    println(min)
}

fun findDistance(position1: Int, position2: Int): Int {
    val n = abs(position1 - position2)
    return (n * (n + 1)) / 2
}

fun day7Part2(positions: List<Int>) {
    val max = positions.maxOrNull()!!
    val min = (1..max).minOfOrNull { position ->
        positions.sumOf { findDistance(position, it) }
    }

    println(min)
}
