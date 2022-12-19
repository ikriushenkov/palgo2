fun bfs(g: Graph, start: Int = 0): IntArray {
    val queue = ArrayDeque<Int>().apply { add(start) }
    val result = IntArray(g.size) { Int.MAX_VALUE }
    val used = BooleanArray(g.size)
    result[start] = 0
    used[start] = true

    while (queue.isNotEmpty()) {
        val cur = queue.removeFirst()
        val distCur = result[cur]

        for (i in g[cur]) {
            if (!used[i]) {
                queue += i
                result[i] = distCur + 1
                used[i] = true
            }
        }
    }

    return result
}