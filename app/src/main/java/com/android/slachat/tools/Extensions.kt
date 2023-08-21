package com.android.slachat.tools

import com.android.slachat.either.Either

fun <T> mapToCoreEither(networkEither: clean.android.network.either.Either<T>): Either<T> {
    return if (networkEither.isSuccess) {
        Either.success(networkEither.value as T)
    } else {
        Either(networkEither.value)
    }
}