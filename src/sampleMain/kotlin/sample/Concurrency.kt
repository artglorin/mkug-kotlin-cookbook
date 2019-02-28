/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("EXPERIMENTAL_UNSIGNED_LITERALS", "EXPERIMENTAL_API_USAGE")

package sample

import platform.posix.sleep
import kotlin.native.concurrent.*
import kotlin.system.getTimeMillis
import kotlin.system.measureTimeMillis

data class WorkerArgument(val intParam: Int, val jobId: String, val sleep: UInt)
data class WorkerResult(val computationResult: Int, val jobId: String)

@ExperimentalUnsignedTypes
fun concurrency(args: Array<String>) {
    val measureTimeMillis = measureTimeMillis {
        val count = resoleWorkersCount(args)
        val workers = createWorkers(count)
        executeConcurrentWork(workers)
        logWorkerTerminationStatus(workers)
    }
    "Workers: OK. Working time in milliseconds: $measureTimeMillis".log()
}

private fun resoleWorkersCount(args: Array<String>) = args.getOrNull(0)?.toIntOrNull() ?: 3

private fun createWorkers(count: Int) = Array(count) { Worker.start() }

private fun executeConcurrentWork(workers: Array<Worker>) {
    for (attempt in 1..3) {
        executeAttempt(attempt, workers)
    }
}

private fun executeAttempt(attempt: Int, workers: Array<Worker>) {
    "Attempt: $attempt".log()
    printAttemptStatistics(
        attempt,
        executeAttemptJobs(workers.mapIndexed(createJob(attempt)))
    )
}

private fun createJob(attempt: Int): (Int, Worker) -> Future<WorkerResult> = { workerId, worker ->
    worker.execute(
        TransferMode.SAFE,
        createJobArguments(workerId, attempt)
    ) { input ->
        var sum = 0
        "${input.jobId} Start computing".log()
        val jobTimeWorking = measureTimeMillis {
            for (i in 0..input.intParam * 1000) {
                sum += i
            }
            sleep(input.intParam.toUInt() * input.sleep)
        }
        "${input.jobId} Computation finished: $jobTimeWorking".log()
        WorkerResult(sum, input.jobId)
    }
}

private fun createJobArguments(jobNumber: Int, attempt: Int ): () -> WorkerArgument = {
    val jobId = "Attempt $attempt, Job#: $jobNumber: "
    "$jobId Creating arguments".log()
    WorkerArgument(jobNumber, jobId, attempt.toUInt())
}

private fun executeAttemptJobs(futures: List<Future<WorkerResult>>): Long {
    var consumed = 0
    val workersCount = futures.size
    return measureTimeMillis {
        while (consumed < workersCount) {
            val completed = waitForMultipleFutures(futures, 0)
            "Completed jobs: ${completed.size}".log()
            handleCompletedJobs(completed)
            consumed += completed.size
            "Consumed: $consumed of $workersCount".log()
            waitOneSecondIfNeed(consumed, workersCount)
        }
    }
}

private fun printAttemptStatistics(attempt: Int, attemptWorkingTime: Long) {
    """
    |Attempt $attempt has been finished.
    |Working time: $attemptWorkingTime
    |-----------------------------------
    """.trimMargin().log()

}

private fun waitOneSecondIfNeed(consumed: Int, count: Int) {
    if (consumed < count) {
        "Waiting 1 sec".log()
        sleep(1)
    }
}

private fun handleCompletedJobs(completed: Set<Future<WorkerResult>>) {
    completed.forEach {
        if (it.state == FutureState.COMPUTED) {
            it.consume { result ->
                "Result received from $it: ${result.jobId} ${result.computationResult}".log()
            }
        }
    }
}

private fun logWorkerTerminationStatus(workers: Array<Worker>) {
    workers.forEach {
        "Worker #${it.requestTermination().id} terminated".log()
    }
}

private fun Any?.log() {
    println("[${getTimeMillis()}] $this")
}