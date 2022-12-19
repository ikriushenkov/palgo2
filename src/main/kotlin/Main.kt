import kotlinx.atomicfu.atomic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
//    val a = generate(200)
//    val b = a.copyOf()
    val g = generateCube(200)

    val time1 = measureTimeMillis {
        bfs(g)
    }

    println(time1)

    val time2 = measureTimeMillis {
        withContext(Dispatchers.IO) {
            parallelBfs1(g)
        }
    }

    println(time2)
}

fun generate(size: Int = 1e8.toInt()) = IntArray(size) { Random.nextInt() }