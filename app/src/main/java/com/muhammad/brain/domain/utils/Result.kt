package com.muhammad.brain.domain.utils

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Error(val message: String) : Result<Nothing>
}

inline fun <D> Result<D>.onSuccess(action: (D) -> Unit): Result<D> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <D> Result<D>.onError(action: (String) -> Unit): Result<D> {
    if (this is Result.Error) action(message)
    return this
}

inline fun <D, R> Result<D>.map(map: (D) -> R): Result<R> {
    return when (this) {
        is Result.Error -> Result.Error(message)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <D> Result<D>.asEmptyDataResult() : EmptyResult<D>{
    return  map {  }
}

typealias EmptyResult<D> = Result<Unit>