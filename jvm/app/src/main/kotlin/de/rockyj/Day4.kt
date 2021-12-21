package de.rockyj

data class BoardItem(val location: Pair<Int, Int>, val value: Int, var marked: Boolean)
data class BingoBoard(val id: Int, val boardItems: List<BoardItem>)
data class RawBoard(var boardItems: List<String>) {
    fun addItems(items: List<String>) {
        this.boardItems = this.boardItems + items
    }
}

fun main() {
    val bingo = fileToArr("day4_1.txt")

    day4Part1(bingo)
    day4Part2(bingo)
}

fun buildBoards(boardsRaw: List<String>): List<BingoBoard> {
    var boardList = emptyList<RawBoard>()
    var board = RawBoard(emptyList())

    boardsRaw.forEach {
        if (it.trim() == "") {
            boardList = boardList + board
            board = RawBoard(emptyList())
        } else {
            board.addItems(it.trim().split(Regex("\\s+")).map { st -> st.trim() })
        }
    }
    boardList = boardList + board

    fun numToRow(num: Int): Int {
        return when(num) {
            in 0..4 -> 0
            in 5..9 -> 1
            in 10..14 -> 2
            in 15..19 -> 3
            in 20..24 -> 4
            else -> 999
        }
    }

    val boards = boardList.mapIndexed { ix, it ->
        val items = it.boardItems.mapIndexed { idx, item ->
            BoardItem(Pair(numToRow(idx), idx % 5), item.toInt(), false)
        }
        BingoBoard(ix, items)
    }

    return boards
}

fun markBoard(boards: List<BingoBoard>, calledNumber: Int) {
    boards.forEach {
        it.boardItems.forEach { item ->
            if (item.value == calledNumber) {
                item.marked = true
            }
        }
    }
}

fun rowMarked(marked: List<Pair<Int, Int>>): Boolean {
    return (0..4).any {
        marked.filter { pair -> pair.first == it }.map { pair ->  pair.second }.sorted().containsAll(listOf(0, 1, 2, 3, 4))
    }
}

fun columnMarked(marked: List<Pair<Int, Int>>): Boolean {
    return (0..4).any {
        marked.filter { pair -> pair.second == it }.map { pair ->  pair.first }.sorted().containsAll(listOf(0, 1, 2, 3, 4))
    }
}


fun isWinner(boards: List<BingoBoard>): List<BingoBoard> {
    return boards.filter {
        val marked = it.boardItems.filter { item -> item.marked }.map { item -> item.location }
        rowMarked(marked) || columnMarked(marked)
    }
}

fun day4Part1(bingo: List<String>) {
    val calledNumbers = bingo[0].split(",")
    val boards = buildBoards(bingo.subList(2, bingo.size))

    for (num in calledNumbers) {
        val called = num.trim().toInt()
        markBoard(boards, called)
        val winner = isWinner(boards)
        if (winner.isNotEmpty()) {
            println(called)
            val unmarkedSum = winner.first().boardItems.filter { item -> !item.marked }.sumOf { it.value }
            println(called * unmarkedSum)
            break
        }
    }
}

fun day4Part2(bingo: List<String>) {
    val calledNumbers = bingo[0].split(",")
    var boards = buildBoards(bingo.subList(2, bingo.size))
    var lastWinner: BingoBoard? = null
    var lastWinCall: Int? = null

    for (num in calledNumbers) {
        val called = num.trim().toInt()
        markBoard(boards, called)

        val winners = isWinner(boards)

        if (winners.isNotEmpty()) {
            boards = boards.filter { !winners.map { win -> win.id }.contains(it.id) }
            lastWinner = winners.first()
            lastWinCall = called
        }
    }

    val unmarkedSum = lastWinner!!.boardItems.filter { item -> !item.marked }.sumOf { it.value }
    println(lastWinCall)
    println(lastWinCall!! * unmarkedSum)
}