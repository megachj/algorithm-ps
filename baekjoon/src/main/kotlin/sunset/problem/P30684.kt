package sunset.problem

/**
 * https://www.acmicpc.net/problem/30684
 */
fun main(args: Array<String>) {
    val n = readln().toInt()

    var result: String = readln()
    for (i in 2..n) {
        var nextName = readln()
        result = getChairman(result, nextName)
    }
    println(result)
}

fun getChairman(name1: String, name2: String): String {
    if (name1.length != 3 && name2.length != 3)
        return "z"
    else if (name1.length == 3 && name2.length != 3)
        return name1
    else if (name1.length != 3 && name2.length == 3)
        return name2

    val compareTo = name1.compareTo(name2)
    return if (compareTo <= 0) {
        name1
    } else {
        name2
    }
}
