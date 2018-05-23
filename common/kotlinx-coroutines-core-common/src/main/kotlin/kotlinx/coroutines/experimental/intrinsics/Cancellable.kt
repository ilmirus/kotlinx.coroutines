/*
 * Copyright 2016-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlinx.coroutines.experimental.intrinsics

import kotlinx.coroutines.experimental.*
import kotlin.coroutines.*
import kotlin.coroutines.intrinsics.*

/**
 * Use this function to start coroutine in a cancellable way, so that it can be cancelled
 * while waiting to be dispatched.
 */
public fun <T> (suspend () -> T).startCoroutineCancellable(completion: Continuation<T>) =
    createCoroutineUnchecked(completion).resumeCancellable(Unit)

/**
 * Use this function to start coroutine in a cancellable way, so that it can be cancelled
 * while waiting to be dispatched.
 */
public fun <R, T> (suspend (R) -> T).startCoroutineCancellable(receiver: R, completion: Continuation<T>) =
    createCoroutineUnchecked(receiver, completion).resumeCancellable(Unit)
