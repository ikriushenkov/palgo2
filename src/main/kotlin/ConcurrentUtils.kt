import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

const val BLOCK = 100

suspend inline fun parallelFor(l: Int, r: Int, crossinline action: suspend (Int) -> Unit) {
    if (r - l <= BLOCK) {
        for (i in l until r) {
            action(i)
        }
        return
    }

    coroutineScope {
        for (j in l until r step BLOCK) {
            launch {
                for (i in j until minOf(r, j + BLOCK)) {
                    action(i)
                }
            }
        }
    }
}

suspend fun IntArray.parallelScan(): IntArray {
    val n = size
    if (size <= BLOCK) {
        return scan(0, Int::plus).toIntArray()
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

    parallelFor(0, size) { i ->
        val prev = if (i % BLOCK == 0) {
            if (i == 0) {
                0
            } else {
                sums[i / BLOCK]
            }
        } else {
            s[i]
        }
        s[i + 1] = prev + this[i]
    }

    return s
}

suspend inline fun IntArray.parallelMap(
    l: Int = 0,
    r: Int = size,
    crossinline transform: (Int) -> Int,
) {
    parallelFor(l, r) { i ->
        this[i] = transform(this[i])
    }
}

suspend inline fun IntArray.parallelFilter(
    l: Int = 0,
    r: Int = size,
    crossinline predicate: (Int) -> Boolean,
): IntArray {
    val flags = copyOf().apply { parallelMap(l, r) { if (predicate(it)) 1 else 0 } }
    val sums = flags.scan(0, Int::plus).toIntArray()
    val destination = IntArray(sums.last())

    parallelFor(l, r) { i ->
        if (flags[i] == 1) {
            destination[sums[i]] = this[i]
        }
    }

    return destination
}

infix fun Int.divideRoundUp(divided: Int) = (this + divided - 1) / divided