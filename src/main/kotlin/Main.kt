fun main() {
    val g = generateCube(500)
    val answer = bfs(g)

    benchmark(retry = 5, generateInput = ::generateCube, assertCorrect = answer::contentEquals, ::bfs, ::parallelBfs)
}