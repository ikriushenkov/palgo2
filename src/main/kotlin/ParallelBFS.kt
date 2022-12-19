import kotlinx.atomicfu.AtomicBooleanArray

suspend fun parallelBfs1(g: Graph, start: Int = 0): IntArray {
    val n = g.size
    val used = AtomicBooleanArray(n)
    var f = intArrayOf(start)
    var dist = 1
    val result = IntArray(g.size) { Int.MAX_VALUE }
    result[start] = 0
    used[start].value = true

    while (f.isNotEmpty()) {
        var deg = IntArray(f.size)

        parallelFor (0, f.size) { i ->
            deg[i] = g[f[i]].size
        }

        deg = deg.scan(0, Int::plus).toIntArray()

        val fSize = deg.last()
        val nextF = IntArray(fSize) { -1 }

        parallelFor(0, f.size) { i ->
            val v = f[i]
            var cur = if (i == 0) 0 else deg[i - 1]
            for (u in g[v]) {
                if (used[u].compareAndSet(expect = false, update = true)) {
                    nextF[cur++] = u
                    result[u] = dist
                }
            }
        }


        f = nextF.parallelFilter { it != -1 }
        dist++
    }

    return result
}