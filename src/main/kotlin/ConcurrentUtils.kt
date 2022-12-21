import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

const val BLOCK = 50

suspend inline fun parallelFor(l: Int, r: Int, crossinline action: suspend (Int) -> Unit) {
    if (r - l <= BLOCK) {
        for (i in l until r) {
            action(i)
        }
        return
    }

    coroutineScope {
        val block = (r - l) / 4 + 1
        for (j in l until r step block) {
            launch {
                for (i in j until minOf(r, j + block)) {
                    action(i)
                }
            }
        }
    }
}

suspend fun IntArray.parallelScan(): IntArray {
    val n = size
    if (size <= BLOCK) {
        val result = IntArray(size + 1)
        for (i in 1..size) {
            result[i] += result[i - 1] + this[i - 1]
        }
        return result
    }

    val blocks = n divideRoundUp BLOCK

    var sums = IntArray(blocks)

    parallelFor(0, sums.size) { i ->
        var s = 0
        for (j in BLOCK * i until minOf(n, BLOCK * (i + 1))) {
            s += this[j]
        }
        sums[i] = s
    }

    sums = sums.parallelScan()

    val s = IntArray(n + 1)

    parallelFor(0, sums.size) { i ->
        if (i * BLOCK > size) {
            return@parallelFor
        }
        val sum = sums[i]
        s[i * BLOCK] = sum
        for (j in BLOCK * i + 1 until minOf(s.size, BLOCK * (i + 1))) {
            s[j] += s[j - 1] + this[j - 1]
        }
    }

    return s
}

suspend inline fun IntArray.parallelMap(
    l: Int = 0,
    r: Int = size,
    crossinline transform: (Int) -> Int,
): IntArray {
    val destination = IntArray(r - l)

    parallelFor(l, r) { i ->
        destination[i] = transform(this[i])
    }

    return destination
}

suspend inline fun IntArray.parallelFilter(
    l: Int = 0,
    r: Int = size,
    crossinline predicate: (Int) -> Boolean,
): IntArray {
    val flags = parallelMap(l, r) { if (predicate(it)) 1 else 0 }
    val sums = flags.parallelScan()
    val destination = IntArray(sums.last())

    parallelFor(l, r) { i ->
        if (flags[i] == 1) {
            destination[sums[i]] = this[i]
        }
    }

    return destination
}

infix fun Int.divideRoundUp(divided: Int) = (this + divided - 1) / divided