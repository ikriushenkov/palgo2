import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicIntegerArray

@OptIn(ExperimentalCoroutinesApi::class)
fun parallelBfs(g: Graph, start: Int = 0): IntArray = runBlocking {
    withContext(Dispatchers.IO.limitedParallelism(4)) {
        suspendParallelBfs(g, start)
    }
}

suspend fun suspendParallelBfs(g: Graph, start: Int = 0): IntArray {
    val n = g.size
    val used = AtomicIntegerArray(n)
    var f = intArrayOf(start)
    var dist = 1
    val result = IntArray(g.size) { Int.MAX_VALUE }
    result[start] = 0
    used[start] = 1

    while (f.isNotEmpty()) {
        var deg = IntArray(f.size)

        parallelFor (0, f.size) { i ->
            deg[i] = g[f[i]].size
        }

        deg = deg.parallelScan()

        val fSize = deg.last()
        val nextF = IntArray(fSize) { -1 }

        parallelFor(0, deg.size - 1) { i ->
            val v = f[i]
            var cur = deg[i]
            for (u in g[v]) {
                if (used.compareAndSet(u, 0, 1)) {
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