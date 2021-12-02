package de.rockyj

import kotlin.test.Test
import kotlin.test.assertNotNull

class Day2Test {
    @Test
    fun testDay2Part1() {
        val instructions = fileToArr("day2_1.txt")
        day2Part1(instructions)
    }

    @Test
    fun testDay2Part2() {
        val instructions = fileToArr("day2_1.txt")
        day2Part2(instructions)
    }
}
