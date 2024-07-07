package sunset.problem

var N: Int = 0
var M: Int = 0
var ai: List<Int> = listOf()
var bi: List<Int> = listOf()

fun main() {
    var line = readlnOrNull()?.split(" ") ?: throw Exception()
    N = line.get(0).toInt()
    M = line.get(1).toInt()

    line = readlnOrNull()?.split(" ") ?: throw Exception()
    ai = line.map { it.toInt() }
    ai = ai.sorted()

    line = readlnOrNull()?.split(" ") ?: throw Exception()
    bi = line.map { it.toInt() }
    bi = bi.sorted()

    println("${getMaxP()}")
}

private fun getMaxP(): Long {
    var p: Long = 0

    var bIdxCheckPoint = 0
    for (idx in bi.indices) {
        if (bi[idx] <= 1) {
            p *= bi[idx]
        } else {
            bIdxCheckPoint = idx
            break
        }
    }

    for (a in ai) {
        p += a
    }
    for (idx in bIdxCheckPoint until bi.size) {
        p *= bi[idx]
    }
    return p
}
