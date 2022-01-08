package de.rockyj

private val input = fileToArr("day8_1.txt")

fun main() {
    day8Part1(input)
}

fun day8Part1(input: List<String>) {
    val outputs = input.map {
        it.split("|")[1].split("\\n").map { st -> st.trim() }
    }.flatMap {
        it.first().split(" ").filter { str -> listOf(2, 4, 3, 7).contains(str.length) }
    }
    println(outputs.size)
}