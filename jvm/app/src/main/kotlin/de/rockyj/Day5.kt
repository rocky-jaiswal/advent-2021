package de.rockyj

import kotlin.math.abs

private data class VentLine(val start: Pair<Int, Int>, val end: Pair<Int, Int>)
private data class Dot(val pos: Pair<Int, Int>, var count: Int = 1) {
    val id: String
        get() {
            return "x${this.pos.first}y${this.pos.second}"
        }
}
private data class Diagram(var dots: MutableMap<String, Dot> = mutableMapOf()) {
    fun addDot(dot: Dot) {
        val existingDot = dots[dot.id]
        if (existingDot != null) {
            existingDot.count = existingDot.count + 1
            dots[dot.id] = existingDot
        } else {
            dots[dot.id] = dot
        }
    }
}

fun main() {
    val input = fileToArr("day5_1.txt")

    day5Part1(input)
    day5Part2(input)
}

fun day5Part1(input: List<String>) {
    val diagram = Diagram()

    input.map { line ->
        val startAndEnd = line.split(" -> ")
        val start = startAndEnd[0].split(",").map { it.trim() }.map { it.toInt() }
        val end = startAndEnd[1].split(",").map { it.trim() }.map { it.toInt() }

        VentLine(Pair(start[0], start[1]), Pair(end[0], end[1]))
    }.filter {
        it.start.first == it.end.first || it.start.second == it.end.second
    }.forEach { coord ->
        if (coord.start.first == coord.end.first) {
            if (coord.start.second <= coord.end.second) {
                (coord.start.second .. coord.end.second).forEach { diagram.addDot(Dot(Pair(coord.start.first, it))) }
            }
            if (coord.start.second > coord.end.second) {
                (coord.end.second .. coord.start.second).forEach { diagram.addDot(Dot(Pair(coord.start.first, it))) }
            }
        }
        if (coord.start.second == coord.end.second) {
            if (coord.start.first <= coord.end.first) {
                (coord.start.first .. coord.end.first).forEach { diagram.addDot(Dot(Pair(it, coord.start.second))) }
            }
            if (coord.start.first > coord.end.first) {
                (coord.end.first .. coord.start.first).forEach { diagram.addDot(Dot(Pair(it, coord.start.second))) }
            }
        }
    }

    val overlaps = diagram.dots.values.count { dot ->
        dot.count >= 2
    }

    println(overlaps)
}

fun day5Part2(input: List<String>) {
    val diagram = Diagram()

    input.map { line ->
        val startAndEnd = line.split(" -> ")
        val start = startAndEnd[0].split(",").map { it.trim() }.map { it.toInt() }
        val end = startAndEnd[1].split(",").map { it.trim() }.map { it.toInt() }

        VentLine(Pair(start[0], start[1]), Pair(end[0], end[1]))
    }.filter {
        it.start.first == it.end.first ||
                it.start.second == it.end.second ||
                abs((it.end.second - it.start.second) / (it.end.first - it.start.first)) == 1
    }.forEach { line ->
        if (line.start.first == line.end.first && line.start.second != line.end.second) {
            if (line.start.second <= line.end.second) {
                (line.start.second .. line.end.second).forEach { diagram.addDot(Dot(Pair(line.start.first, it))) }
            }
            if (line.start.second > line.end.second) {
                (line.end.second .. line.start.second).forEach { diagram.addDot(Dot(Pair(line.start.first, it))) }
            }
        }
        if (line.start.second == line.end.second && line.start.first != line.end.first) {
            if (line.start.first <= line.end.first) {
                (line.start.first .. line.end.first).forEach { diagram.addDot(Dot(Pair(it, line.start.second))) }
            }
            if (line.start.first > line.end.first) {
                (line.end.first .. line.start.first).forEach { diagram.addDot(Dot(Pair(it, line.start.second))) }
            }
        }
        // diagonal case
        if (line.start.second != line.end.second
            && line.start.first != line.end.first
            && abs((line.end.second - line.start.second) / (line.end.first - line.start.first)) == 1) {
            if (line.start.first < line.end.first) {
                if (line.start.second < line.end.second) {
                    (line.start.first .. line.end.first).forEachIndexed { idx, coord ->
                        diagram.addDot(Dot(Pair(coord, line.start.second + idx)))
                    }
                } else {
                    (line.start.first .. line.end.first).forEachIndexed { idx, coord ->
                        diagram.addDot(Dot(Pair(coord, line.start.second - idx)))
                    }
                }

            } else {
                if (line.end.second < line.start.second) {
                    (line.end.first .. line.start.first).forEachIndexed { idx, coord ->
                        diagram.addDot(Dot(Pair(coord, line.end.second + idx)))
                    }
                } else {
                    (line.end.first .. line.start.first).forEachIndexed { idx, coord ->
                        diagram.addDot(Dot(Pair(coord, line.end.second - idx)))
                    }
                }
            }
        }
    }

    val overlaps = diagram.dots.values.count { dot ->
        dot.count >= 2
    }

    println(overlaps)
}