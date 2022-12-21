# BFS

### Summary
```
Benchmark              Mode  Cnt      Score     Error  Units
BfsTest.parallelBfs    avgt    5   9695.205 ± 286.616  ms/op
BfsTest.sequentialbfs  avgt    5  29190.182 ± 146.833  ms/op

```

### Full result

```
# JMH version: 1.35
# VM version: JDK 17-vanilla, OpenJDK 64-Bit Server VM, 17-vanilla+0-adhoc.heretic.jdk-17.0.0
# VM invoker: /Users/ikriushenkov/.ya/tools/v4/2460645110/bin/java
# VM options: -Dfile.encoding=UTF-8 -Djava.io.tmpdir=/Users/ikriushenkov/IdeaProjects/palgo2/build/tmp/jmh -Duser.country=RU -Duser.language=en -Duser.variant -XX:+UseZGC -Xms32G -Xmx32G
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: bfs.BfsTest.parallelBfs

# Run progress: 0.00% complete, ETA 00:03:20
# Fork: 1 of 1
# Warmup Iteration   1: 12183.193 ms/op
# Warmup Iteration   2: 10124.398 ms/op
# Warmup Iteration   3: 9788.570 ms/op
# Warmup Iteration   4: 9889.469 ms/op
# Warmup Iteration   5: 9549.258 ms/op
Iteration   1: 9678.687 ms/op
Iteration   2: 9573.400 ms/op
Iteration   3: 9737.981 ms/op
Iteration   4: 9761.112 ms/op
Iteration   5: 9724.842 ms/op


Result "bfs.BfsTest.parallelBfs":
  9695.205 ±(99.9%) 286.616 ms/op [Average]
  (min, avg, max) = (9573.400, 9695.205, 9761.112), stdev = 74.433
  CI (99.9%): [9408.588, 9981.821] (assumes normal distribution)


# JMH version: 1.35
# VM version: JDK 17-vanilla, OpenJDK 64-Bit Server VM, 17-vanilla+0-adhoc.heretic.jdk-17.0.0
# VM invoker: /Users/ikriushenkov/.ya/tools/v4/2460645110/bin/java
# VM options: -Dfile.encoding=UTF-8 -Djava.io.tmpdir=/Users/ikriushenkov/IdeaProjects/palgo2/build/tmp/jmh -Duser.country=RU -Duser.language=en -Duser.variant -XX:+UseZGC -Xms32G -Xmx32G
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: bfs.BfsTest.sequentialbfs

# Run progress: 50.00% complete, ETA 00:02:59
# Fork: 1 of 1
# Warmup Iteration   1: 37426.496 ms/op
# Warmup Iteration   2: 30170.941 ms/op
# Warmup Iteration   3: 29239.876 ms/op
# Warmup Iteration   4: 28770.439 ms/op
# Warmup Iteration   5: 29062.853 ms/op
Iteration   1: 29171.555 ms/op
Iteration   2: 29185.172 ms/op
Iteration   3: 29216.559 ms/op
Iteration   4: 29237.519 ms/op
Iteration   5: 29140.106 ms/op


Result "bfs.BfsTest.sequentialbfs":
  29190.182 ±(99.9%) 146.833 ms/op [Average]
  (min, avg, max) = (29140.106, 29190.182, 29237.519), stdev = 38.132
  CI (99.9%): [29043.349, 29337.015] (assumes normal distribution)


# Run complete. Total time: 00:08:00

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
extra caution when trusting the results, look into the generated code to check the benchmark still
works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
different JVMs are already problematic, the performance difference caused by different Blackhole
modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.

Benchmark              Mode  Cnt      Score     Error  Units
BfsTest.parallelBfs    avgt    5   9695.205 ± 286.616  ms/op
BfsTest.sequentialbfs  avgt    5  29190.182 ± 146.833  ms/op
```