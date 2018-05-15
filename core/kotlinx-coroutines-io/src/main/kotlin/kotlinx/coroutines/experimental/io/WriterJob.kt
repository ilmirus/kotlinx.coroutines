package kotlinx.coroutines.experimental.io

import kotlinx.coroutines.experimental.*
import kotlin.coroutines.*

/**
 * A coroutine job that is writing to a byte channel
 */
interface WriterJob : Job {
    /**
     * A reference to the channel that this coroutine is writing to
     */
    val channel: ByteReadChannel
}

interface WriterScope : CoroutineScope {
    val channel: ByteWriteChannel
}

fun writer(coroutineContext: CoroutineContext,
           channel: ByteChannel,
           parent: Job? = null,
           block: suspend WriterScope.() -> Unit): WriterJob {
    val newContext = newCoroutineContext(coroutineContext, parent)
    val coroutine = WriterCoroutine(newContext, channel)
    coroutine.start(CoroutineStart.DEFAULT, coroutine, block)
    return coroutine
}

fun writer(coroutineContext: CoroutineContext,
           autoFlush: Boolean = false,
           parent: Job? = null,
           block: suspend WriterScope.() -> Unit): WriterJob {
    val channel = ByteChannel(autoFlush) as ByteBufferChannel
    val job = writer(coroutineContext, channel, parent, block)
    channel.attachJob(job)
    return job
}

private class WriterCoroutine(ctx: CoroutineContext, channel: ByteChannel)
    : ByteChannelCoroutine(ctx, channel), WriterScope, WriterJob

