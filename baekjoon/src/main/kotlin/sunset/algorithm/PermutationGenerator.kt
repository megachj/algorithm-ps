package sunset.algorithm

fun main(args: Array<String>) {
    val permutationGenerator = PermutationGenerator<Int>(listOf(0, 1, 2))
    for (e in permutationGenerator.createPermutationList()) {
        println("${e}")
    }
}

class PermutationGenerator<T : Any>(
    private val elements: List<T>
) {

    /**
     * 원소 리스트의 순열 리스트를 생성한다.
     *
     * @param elements 원소 리스트
     * @return 순열 리스트
     */
    fun createPermutationList(): List<List<T>> {
        println("순열 리스트를 생성합니다. 원소 수: ${elements.size}, 경우의 수: ${factorial()}")

        var pickedElements = elements.map { MutablePair(it, false) }
        val permutations = mutableListOf<List<T>>()

        fun permutate(permutation: MutableList<T>) {
            if (permutation.size == elements.size) {
                permutations.add(permutation.toList())
                return
            }

            for (i in 0 until pickedElements.size) {
                if (pickedElements[i].second.not()) {
                    pickedElements[i].second = true
                    permutation.add(pickedElements[i].first)

                    permutate(permutation)

                    permutation.removeLast()
                    pickedElements[i].second = false
                }
            }
        }

        permutate(mutableListOf())
        return permutations
    }

    /**
     * 순열 경우의 수를 구한다.
     */
    fun factorial(): Int {
        fun factorial(n: Int): Int {
            return if (n == 1) 1
            else n * factorial(n - 1)
        }

        return factorial(elements.size)
    }

    private data class MutablePair<K, V>(
        var first: K,
        var second: V,
    )
}


