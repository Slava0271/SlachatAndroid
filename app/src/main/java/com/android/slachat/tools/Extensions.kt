package com.android.slachat.tools

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.android.slachat.either.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> mapToCoreEither(networkEither: clean.android.network.either.Either<T>): Either<T> {
    return if (networkEither.isSuccess) {
        Either.success(networkEither.value as T)
    } else {
        Either(networkEither.value)
    }
}

fun <T> Flow<T>.collectInLifecycle(
    lifecycleOwner: LifecycleOwner,
    action: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        collect { value ->
            action(value)
        }
    }
}
