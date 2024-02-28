package com.moriawe.translationapp.core.domain.util

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


actual open class CommonFlow actual constructor(
private val flow: Flow<T>
): Flow<T> by flow {
    fun subsribe(
        coroutinesScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (T) -> Unit
    ): DisposableHandle {
        coroutinesScope.launch(dispatcher) {
            flow.collect(onCollect)
        }
        return DisposableHandle { job.cancel() }
    }
}