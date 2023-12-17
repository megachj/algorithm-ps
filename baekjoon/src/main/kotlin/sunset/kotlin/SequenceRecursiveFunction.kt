package sunset.kotlin

fun main(args: Array<String>) {
    val decreaseSequence = sequence {
        recursivelyDecrease(10)
    }

    for (i in decreaseSequence) {
        println("$i")
    }
}

/**
 * 시퀀스 내에서 재귀적으로 호출할 수 있는 함수이다.
 *
 * @param n 양의 정수
 * @return n 부터 1 까지의 정수를 리턴
 */
private suspend fun SequenceScope<Int>.recursivelyDecrease(n: Int) {
    if (n <= 0) {
        return
    }

    yield(n)
    recursivelyDecrease(n-1)
}
