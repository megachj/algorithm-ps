package sunset.problem

import kotlin.math.max
import kotlin.math.min

/**
 * https://www.acmicpc.net/problem/30685
 */
// n <= 30만
fun main(args: Array<String>) {
    val n = readln().toInt()

    // O(n) -> 30만
    val positions: MutableList<Position> = mutableListOf()
    for (i in 0 ..n-1) {
        positions.add(Position(readln().split(" ")))
    }
    // O(nlgn) -> 545만
    positions.sortBy { it.x }

    // O(n) -> 30만
    var result: Int = Int.MAX_VALUE
    for (i in 0..n-2) {
        result = min(result, getResult(positions[i], positions[i+1]))
    }

    if (result == Int.MAX_VALUE) {
        println("forever")
    } else {
        println("$result")
    }
}

private fun getResult(pos1: Position, pos2: Position): Int {
    val distance:Int = pos2.x - pos1.x - 1
    val cnt2 = min(pos1.dis, pos2.dis)
    val cnt1 = max(pos1.dis, pos2.dis) - cnt2

    // 2칸씩 거리를 깐다.
    val halfDis = distance / 2
    if (cnt2 > halfDis) {
        return halfDis
    }

    val remainingDis = distance - (cnt2 * 2)
    return if (remainingDis >= cnt1) {
        Int.MAX_VALUE
    } else {
        cnt2 + remainingDis
    }
}

data class Position(
    val x: Int,
    val h: Int,
) {
    constructor(inputs: List<String>) : this(inputs[0].toInt(), inputs[1].toInt())

    val dis: Int
        get() = (h-1)/2
}
