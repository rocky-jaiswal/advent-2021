package de.rockyj

fun main() {
    val inputs = fileToArr("app/src/main/resources/day1_1.txt")

    part1(inputs)
    part2(inputs)
}

private fun part1(input: List<String>) {
    var current = input.first().toLong()
    var increases = 0

    input.map { it.toLong() }.forEach {
        if (it > current) increases += 1
        current = it
    }

    println(increases)
}

private fun part2(input: List<String>) {
    val groups = mutableListOf<List<Long>>()
    val max = input.size - 3

    input.forEachIndexed { index, element ->
        if (index <= max) {
            val list = listOf(element, input.elementAt(index + 1), input.elementAt(index + 2)).map { it.toLong() }
            groups.add(list)
        }
    }

    val sums = groups.map { it.sum() }.map { it.toString() }
    part1(sums)
}