import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@OptIn(ExperimentalTime::class)
fun <T> benchmark(
    retry: Int = 5,
    generateInput: () -> T,
    assertCorrect: (IntArray) -> Boolean,
    vararg actions: (T) -> IntArray,
) {
    println("Start benchmark...")
    println("------------------------------")

    val times = actions.mapIndexed { index, action ->
        List(retry) { tryIndex ->
            val input = generateInput()
            val (result, time) = measureTimedValue {
                action(input)
            }
            println("function: ${index + 1}\ttry: ${tryIndex + 1}\ttime: ${time.toDouble(DurationUnit.MILLISECONDS) / 1000}s")

            check(assertCorrect(result)) { "Array is not sorted" }
            time.toDouble(DurationUnit.MILLISECONDS)
        }.average()
    }

    println("------------------------------")

    times.forEachIndexed { index, time ->
        println("function: $index\tavg time: ${time / 1000}s")
    }

    println("------------------------------")

    val places = times.withIndex().sortedBy { it.value }

    places.forEachIndexed { place, (index, time) ->
        println("${place + 1}. function: ${index + 1}\t diff: ${time / places.first().value}")
    }

    println("------------------------------")
    println("Finish benchmark...")
}