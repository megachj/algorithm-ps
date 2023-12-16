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
    private val learningExpirationDateList: List<Int>, // 학습한 뒤 지식의 유효일자 리스트
    private val problemList: MutableList<List<Int>>, // 문제 리스트(문제의 필요한 지식 리스트)
) {
    private var problems: MutableList<Int?>
    private var permutationMap: MutableMap<Int, Boolean>

    private var knowledges: Array<Int> = Array(knowledgeCount) { 0 }

    private var result: Int = Int.MAX_VALUE

    init {
        problems = mutableListOf()
        for (i in 0 until problemCount) {
            problems.add(i, null)
        }

        permutationMap = mutableMapOf()
        for (i in 0 until problemCount) {
            permutationMap[i] = false
        }
    }

    fun printAnswer() {
        proceedPermutation(0)
        println(result)
    }

    private fun proceedPermutation(index: Int) {
        if (index >= problemCount) {
            calcStudyMinCountAndSet()
            return
        }

        if (problems[index] != null) {
            return
        }

        for(i in 0..problemCount-1) {
            if (permutationMap[i]!!.not()) {
                permutationMap[i] = true
                problems[index] = i

                proceedPermutation(index + 1)

                permutationMap[i] = false
                problems[index] = null
            }
        }
    }

    /**
     * pickedProblemNumber 에 해당하는 공부해야 하는 최소 횟수를 result 프로퍼티에 저장한다.
     */
    private fun calcStudyMinCountAndSet() {
        // 공부 횟수
        var studyCount = 0

        // 머릿속 초기화
        for (i in 0..knowledges.size-1) {
            knowledges[i] = 0
        }

        // m 개 문제 풀기
        for (i in 0..problemCount-1) {
            // 문제 조회
            val problem: Int = problems[i]!!
            // 문제 푸는데 필요한 지식 조회
            val neededKnowledge: List<Int> = problemList[problem]

            // 머릿속에 없는 지식은 공부하기
            for (know in neededKnowledge) {
                if (knowledges[know] == 0) {
                    knowledges[know] += learningExpirationDateList[know] // 공부하기
                    studyCount++
                }
            }

            // 문제 풀고나면 하루 지남. 머릿속 지식 남은시간 -1
            for (i in 0..knowledges.size-1) {
                if (knowledges[i] > 0) {
                    knowledges[i]--
                }
            }
        }

        result = Math.min(result, studyCount)
//        println("중간과정: ${problems}, ${studyCount}")
    }
}
