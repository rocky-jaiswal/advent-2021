package de.rockyj

private val input1 = "16,1,2,0,4,2,7,1,2,14".split(",").map { it.toInt() }
private val input2 = fileToArr("day7_1.txt").first().split(",").map { it.toInt() }

fun main() {
    day7Part1(input2)
}

fun day7Part1(positions: List<Int>) {
    val min = positions.map { position ->
        positions.map { Math.abs(position - it) }.sum()
    }.minOrNull()

    println(min)
}
