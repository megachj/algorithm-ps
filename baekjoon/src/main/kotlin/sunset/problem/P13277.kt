package sunset.problem

import kotlin.math.max

/**
 * https://www.acmicpc.net/problem/13277
 */
fun main(args: Array<String>) {
    val (aNumber, bNumber) = readln().split(" ")
    val result = multiply(aNumber, bNumber)
    println(result)
}

fun multiply(aNumber: String, bNumber: String): String {
    val n: Int = max(aNumber.length, bNumber.length)
    val a = numberStringToByteArray(aNumber, n);
    val b = numberStringToByteArray(bNumber, n);

    val result: ByteArray = if (isGreaterOrEqual(a, b)) {
        solveDivideConquer(a, DividableRange.of(0, n), b, DividableRange.of(0, n))
    } else {
        solveDivideConquer(b, DividableRange.of(0, n), a, DividableRange.of(0, n))
    }

    val stringBuilder = StringBuilder()
    for (i in result.indices.reversed()) {
        stringBuilder.append(result[i].toInt())
    }
    return stringBuilder.toString()
}

private fun numberStringToByteArray(numberString: String, n: Int): ByteArray {
    // 문자열의 길이만큼의 byte 배열 생성
    val byteArray = ByteArray(n)

    // 각 문자를 byte로 변환하여 배열에 저장
    val numberLength = numberString.length
    for (i in 0 until numberLength) {
        byteArray[i] = (numberString[numberLength - i - 1].code - '0'.code).toByte()
    }

    return byteArray
}

private fun isGreaterOrEqual(a: ByteArray, b: ByteArray): Boolean {
    for (i in a.indices.reversed()) {
        if (a[i] > b[i]) {
            return true
        }
        if (a[i] < b[i]) {
            return false
        }
    }
    return true
}

private fun solveDivideConquer(
    a: ByteArray, aRange: DividableRange, b: ByteArray, bRange: DividableRange
): ByteArray {
    val aRangeDividable: Boolean = aRange.isDividable

    // 쪼개지는 경우
    if (aRangeDividable) {
        val a1Range: DividableRange = aRange.dividedRight
        val a0Range: DividableRange = bRange.dividedLeft

        val b1Range: DividableRange = bRange.dividedRight
        val b0Range: DividableRange = bRange.dividedLeft

        val halfN: Int = (aRange.middle - aRange.start)

        // 곱셈1: a1 * b1
        val a1b1 = solveDivideConquer(a, a1Range, b, b1Range)
        // 곱셈2: a0 * b0
        val a0b0 = solveDivideConquer(a, a0Range, b, b0Range)

        // a1 + a0
        val aPlus: ByteArray = plusBytes(
            a, a1Range.start, a1Range.endExclusive, a, a0Range.start, a0Range.endExclusive
        )
        // b1 + b0
        val bPlus: ByteArray = plusBytes(
            b, b1Range.start, b1Range.endExclusive, b, b0Range.start, b0Range.endExclusive
        )

        val n = max(aPlus.size.toDouble(), bPlus.size.toDouble()).toInt()
        val aPlusArray: ByteArray = upSizing(aPlus, n)
        val bPlusArray: ByteArray = upSizing(bPlus, n)

        // 곱셈3: (a1 + a0)*(b1 + b0) = a1*b1 + a1*b0 + a0*b1 + a0*b0
        val aPlusBPlus = solveDivideConquer(
            aPlusArray, DividableRange.of(0, aPlusArray.size), bPlusArray, DividableRange.of(0, bPlusArray.size)
        )

        // a1b1 * 10^n
        val z1: ByteArray = multiplyDecimalExp(a1b1, halfN * 2)
        // a0b0
        val z3 = a0b0
        // ((a1+a0)(b1+b0)-(a1b1+a0b0)) * 10^(n/2)
        var z2: ByteArray = plusBytes(a1b1, 0, a1b1.size, a0b0, 0, a0b0.size)
        z2 = minusBytes(aPlusBPlus, 0, aPlusBPlus.size, z2, 0, z2.size)
        z2 = multiplyDecimalExp(z2, halfN)

        var result: ByteArray = plusBytes(z1, 0, z1.size, z2, 0, z2.size)
        result = plusBytes(result, 0, result.size, z3, 0, z3.size)
        return result
    } else {
        val value = a[aRange.start] * b[bRange.start]
        if (value > 10) {
            val result = ByteArray(2)
            result[0] = (value % 10).toByte()
            result[1] = Math.floorDiv(value, 10).toByte()
            return result
        } else {
            val result = ByteArray(1)
            result[0] = value.toByte()
            return result
        }
    }
}

private fun upSizing(a: ByteArray, n: Int): ByteArray {
    require(a.size <= n) { "업사이징하려는 사이즈가 더 작음" }

    if (a.size == n) {
        return a
    }

    val upsizingArray = ByteArray(n)
    for (i in 0 until n) {
        if (i < a.size) {
            upsizingArray[i] = a[i]
        } else {
            upsizingArray[i] = 0
        }
    }
    return upsizingArray
}

/**
 * a + b 연산을 한다.
 */
private fun plusBytes(
    a: ByteArray, aStart: Int, aEndExclusive: Int,
    b: ByteArray, bStart: Int, bEndExclusive: Int
): ByteArray {
    val aLength = aEndExclusive - aStart
    val bLength = bEndExclusive - bStart

    var resultArray = ByteArray((max(aLength.toDouble(), bLength.toDouble()) + 1).toInt())

    // 각 자릿수에 맞춰 덧셈 연산을 한다.
    for (i in resultArray.indices) {
        val aValue = if (i < aLength) a[aStart + i] else 0
        val bValue = if (i < bLength) b[bStart + i] else 0

        resultArray[i] = (aValue + bValue).toByte()
    }

    // 10진법에 맞게 각 자릿수를 조정한다.
    resultArray = normalize(resultArray)

    return trimBytes(resultArray)
}

/**
 * a >= b 인 수에 대해 a - b 연산을 한다.<br></br>
 * 만약 b 가 더 큰 경우는 고려하지 않는다. 따라서 b 가 더 큰 경우는 자릿수에 음수가 포함되게 된다.<br></br>
 */
private fun minusBytes(
    a: ByteArray, aStart: Int, aEndExclusive: Int,
    b: ByteArray, bStart: Int, bEndExclusive: Int
): ByteArray {
    val aLength = aEndExclusive - aStart
    val bLength = bEndExclusive - bStart

    var resultArray = ByteArray((max(aLength.toDouble(), bLength.toDouble()) + 1).toInt())

    // 각 자릿수에 맞춰 뺄셈 연산을 한다.
    for (i in resultArray.indices) {
        val aValue = if (i < aLength) a[aStart + i] else 0
        val bValue = if (i < bLength) b[bStart + i] else 0

        resultArray[i] = (aValue - bValue).toByte()
    }

    // 각 자릿수의 값이 음수이면 윗 자릿수에서 빌려와서 양수로 만들어준다.
    for (i in resultArray.indices) {
        if (resultArray[i] < 0) {
            resultArray[i + 1] = (resultArray[i + 1] - 1).toByte()
            resultArray[i] = (resultArray[i] + 10).toByte()
        }
    }

    // 10진법에 맞게 각 자릿수를 조정한다.
    resultArray = normalize(resultArray)

    return trimBytes(resultArray)
}

/**
 * 10진법에 맞게 자릿수들에서 올림을 통해 값을 조정한다.
 */
private fun normalize(a: ByteArray): ByteArray {
    var curPos: Byte
    var nextPos: Byte
    for (i in a.indices) {
        curPos = (a[i] % 10).toByte()
        nextPos = Math.floorDiv(a[i].toInt(), 10).toByte()

        a[i] = curPos
        if (i + 1 < a.size) {
            a[i + 1] = (a[i + 1] + nextPos).toByte()
        } else check(nextPos <= 0) { "배열 길이가 필요한 것보다 짧습니다! 확인이 필요해요." }
    }
    return trimBytes(a)
}

/**
 * a * 10^(decimalExp)
 */
private fun multiplyDecimalExp(a: ByteArray, decimalExp: Int): ByteArray {
    val result = ByteArray(a.size + decimalExp)
    for (i in 0 until decimalExp) {
        result[i] = 0
    }
    System.arraycopy(a, 0, result, decimalExp + 0, a.size)

    return trimBytes(result)
}

private fun trimBytes(a: ByteArray): ByteArray {
    // 가장 큰 자릿수의 인덱스를 구한다.
    var lastIndex = a.size - 1
    for (i in a.indices.reversed()) {
        if (a[i].toInt() != 0) {
            lastIndex = i
            break
        }
        if (i == 0 && a[i].toInt() == 0) {
            lastIndex = 0
        }
    }

    // 큰 자릿수들이 0으로 채워져있으면 배열을 조정한다.
    if (lastIndex == a.size - 1) {
        return a
    } else {
        val subarray = ByteArray(lastIndex + 1)
        System.arraycopy(a, 0, subarray, 0, lastIndex + 1)
        return subarray
    }
}

class DividableRange private constructor(val start: Int, val endExclusive: Int) {
    val middle: Int = (start + endExclusive) / 2

    val isDividable: Boolean
        get() = start != middle

    val dividedLeft: DividableRange
        /**
         * start <= x < middle 로 나눈다.
         */
        get() {
            require(isDividable) { "더 이상 쪼갤 수 없습니다." }
            return DividableRange(start, middle)
        }

    val dividedRight: DividableRange
        /**
         * middle <= x < endExclusive 로 나눈다.
         */
        get() {
            require(isDividable) { "더 이상 쪼갤 수 없습니다." }
            return DividableRange(middle, endExclusive)
        }

    companion object {
        fun of(start: Int, endExclusive: Int): DividableRange {
            return DividableRange(start, endExclusive)
        }
    }
}