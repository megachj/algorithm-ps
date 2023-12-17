package sunset.problem

/**
 * https://www.acmicpc.net/problem/30686
 */
fun main(args: Array<String>) {
    // n: 지식 수, m: 문제 수
    // 1 <= n <= 1000, 1 <= m <= 7
    val (n, m) = readln().split(" ").map { it.toInt() }

    // d[i]: n(i) 지식을 까먹게 되는 시간. 원소수는 n개. 1 <= d(i) <= m
    val d = readln().split(" ").map { it.toInt() }

    // k[i][j]: m(i) 를 해결하기 위해 필요한 지식들. 원소수는 최대 m * n 개.
    val k: MutableList<List<Int>> = mutableListOf()
    for (i in 1..m) {
        val inputs = readln().split(" ").map { it.toInt() - 1 }
        k.add(inputs.subList(1, inputs.size))
    }

    PSMachine(n, m, d, k).printAnswer()
}

/**
 * 분류: 브루트포스 + 그리디 알고리즘
 * 주어진 문제들의 순열을 구하고 그때의 최소 학습 횟수를 구한다.
 * 모든 순열에서 최소 학습 횟수중 가장 작은 값을 출력한다.
 * 모든 지식은 필요할 때 공부하는게 항상 최선인데 왜냐하면 필요한 순간 늦게 공부할 수록 잊어버리는 일자가 가장 늦춰지기 때문이다. 따라서 각 순열의 최소 학습 횟수는 매번 필요한 공부를 진행하면 된다.
 *
 * - 문제 수 7 개, 문제 순열은 7! = 5060
 * - 각 순열마다 문제를 최대 1000 * 7 개씩 풀 수 있다.
 * - => 5000 * 7000 = 3500만
 */
class PSMachine(
    private val knowledgeCount: Int, // 지식 수
    private val problemCount: Int, // 문제 수
    private val knowledgeExpirationDateList: List<Int>, // 학습한 뒤 지식의 유효일자 리스트
    private val problemToKnowledgeList: MutableList<List<Int>>, // 각 문제의 필요한 지식 리스트
) {
    fun printAnswer() {
        val permutationSequenceGenerator = PermutationSequenceGenerator((0 until problemCount).toList())

        var result = Int.MAX_VALUE
        for (problemPermutation in permutationSequenceGenerator.createPermutationSequence(problemCount)) {
            result = Math.min(result, calcMinStudyCount(problemPermutation))
        }

        println(result)
    }

    /**
     * pickedProblemNumber 에 해당하는 공부해야 하는 최소 횟수를 result 프로퍼티에 저장한다.
     */
    private fun calcMinStudyCount(
        problemPermutation: List<Int>
    ): Int {
        // 공부 횟수
        var studyCount = 0
        // 머릿속 지식 초기화
        val headKnowledges: Array<Int> = Array(knowledgeCount) { 0 }

        // m 개 문제 풀기
        for (problem in problemPermutation) {
            // 문제 푸는데 필요한 지식 조회
            val neededKnowledge: List<Int> = problemToKnowledgeList[problem]

            // 머릿속에 없는 지식은 공부하기
            for (know in neededKnowledge) {
                if (headKnowledges[know] == 0) {
                    headKnowledges[know] += knowledgeExpirationDateList[know]
                    studyCount++
                }
            }

            // 문제 풀고나면 하루 지나고, 머릿속 지식 유효일자가 1씩 감소
            for (i in headKnowledges.indices) {
                if (headKnowledges[i] > 0) {
                    headKnowledges[i]--
                }
            }
        }

        return studyCount
    }
}

class PermutationSequenceGenerator(
    private val elements: Collection<Int>
) {
    /**
     * nPr 시퀀스를 리턴한다.
     *
     * @return 순열 시퀀스
     */
    fun createPermutationSequence(r: Int): Sequence<List<Int>> {
        if (r > elements.size) {
            throw IllegalArgumentException("r 은 n 보다 클 수 없습니다.")
        }

        suspend fun SequenceScope<List<Int>>.recursivelyPermutate(
            pickedElements: BooleanArray,
            permutation: MutableList<Int>,
        ) {
            if (permutation.size == r) {
                yield(permutation.toList())
                return
            }

            for (i in pickedElements.indices) {
                if (pickedElements[i].not()) {
                    pickedElements[i] = true
                    permutation.add(i)

                    recursivelyPermutate(pickedElements, permutation)

                    permutation.removeLast()
                    pickedElements[i] = false
                }
            }
        }

        return sequence {
            recursivelyPermutate(elements.map { false }.toBooleanArray(), mutableListOf())
        }
    }
}
