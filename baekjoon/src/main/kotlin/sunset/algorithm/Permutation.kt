package sunset.algorithm

fun main(args: Array<String>) {
    println("순열 리스트 예제")
    val permutationListGenerator = PermutationListGenerator(setOf(0, 1, 2))
    for (e in permutationListGenerator.createPermutationList(3)) {
        println("${e}")
    }

    println("순열 시퀀스 예제")
    val permutationSequenceGenerator = PermutationSequenceGenerator(setOf(0, 1, 2))
    for (e in permutationSequenceGenerator.createPermutationSequence(2)) {
        println("${e}")
    }
}

class PermutationListGenerator<T : Any>(
    private val elements: Set<T>
) {
    /**
     * nPr 전체 순열 리스트를 리턴한다.
     *
     * @return 순열 리스트
     */
    fun createPermutationList(r: Int): List<List<T>> {
        if (r > elements.size) {
            throw IllegalArgumentException("r 은 n 보다 클 수 없습니다.")
        }

        val picked = elements.map { MutablePair(it, false) }
        val permutations = mutableListOf<List<T>>()

        fun recursivelyPermutate(permutation: MutableList<T>) {
            if (permutation.size == r) {
                permutations.add(permutation.toList())
                return
            }

            for (i in picked.indices) {
                if (picked[i].second.not()) {
                    picked[i].second = true
                    permutation.add(picked[i].first)

                    recursivelyPermutate(permutation)

                    permutation.removeLast()
                    picked[i].second = false
                }
            }
        }

        recursivelyPermutate(mutableListOf())
        return permutations
    }

    private data class MutablePair<K, V>(
        var first: K,
        var second: V,
    )
}

class PermutationSequenceGenerator<T: Any>(
    private val elements: Set<T>
) {
    /**
     * nPr 시퀀스를 리턴한다.
     *
     * @return 순열 시퀀스
     */
    fun createPermutationSequence(r: Int): Sequence<List<T>> {
        if (r > elements.size) {
            throw IllegalArgumentException("r 은 n 보다 클 수 없습니다.")
        }

        suspend fun <T> SequenceScope<List<T>>.recursivelyPermutate(
            pickedElements: List<MutablePair<T, Boolean>>,
            permutation: MutableList<T>,
        ) {
            if (permutation.size == r) {
                yield(permutation.toList())
                return
            }

            for (i in pickedElements.indices) {
                if (pickedElements[i].second.not()) {
                    pickedElements[i].second = true
                    permutation.add(pickedElements[i].first)

                    recursivelyPermutate(pickedElements, permutation)

                    permutation.removeLast()
                    pickedElements[i].second = false
                }
            }
        }

        return sequence {
            recursivelyPermutate(elements.map { MutablePair(it, false) }, mutableListOf())
        }
    }

    private data class MutablePair<K, V>(
        var first: K,
        var second: V,
    )
}

/**
 * nPr 경우의 수를 계산한다.
 *
 * @return 순열 시퀀스
 */
fun calcPermutationCasesNumber(n: Int, r: Int): Int {
    if (r > n) {
        throw IllegalArgumentException("r 은 n 보다 클 수 없습니다.")
    }

    fun recursivelyCalcCases(n: Int, r: Int): Int {
        return if (r == 0) 1
        else n * recursivelyCalcCases(n - 1, r - 1)
    }

    return recursivelyCalcCases(n, r)
}
