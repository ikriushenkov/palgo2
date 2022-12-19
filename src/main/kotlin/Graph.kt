interface Graph {
    val size: Int
    operator fun get(index: Int): IntArray
}

class GeneratedGraph(override val size: Int, private val init: (Int) -> IntArray) : Graph {
    override fun get(index: Int): IntArray = init(index)
}

fun generateCube(size: Int = 500): Graph =
    GeneratedGraph(size * size * size) { i ->
        val x = i / (size * size)
        val y = i / size % size
        val z = i % size

        val edges = mutableListOf<Int>()

        if (x != 0) {
            edges += (x - 1) * size * size + y * size + z
        }
        if (x != size - 1) {
            edges += (x + 1) * size * size + y * size + z
        }

        if (y != 0) {
            edges += x * size * size + (y - 1) * size + z
        }
        if (y != size - 1) {
            edges += x * size * size + (y + 1) * size + z
        }

        if (z != 0) {
            edges += x * size * size + y * size + z - 1
        }
        if (z != size - 1) {
            edges += x * size * size + y * size + z + 1
        }

        return@GeneratedGraph edges.toIntArray()
    }