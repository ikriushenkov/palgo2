package bfs

import Graph
import bfs
import generateCube
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole
import parallelBfs
import java.util.concurrent.TimeUnit

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 5)
open class BfsTest {

    private lateinit var graph: Graph

    @Setup(Level.Trial)
    fun setup() {
        graph = generateCube()
    }

    @Benchmark
    fun sequentialbfs(blackhole: Blackhole) {
        blackhole.consume(bfs(graph))
    }

    @Benchmark
    fun parallelBfs(blackhole: Blackhole) {
        blackhole.consume(parallelBfs(graph))
    }
}