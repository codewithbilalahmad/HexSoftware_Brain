package com.muhammad.brain.data.network.client

import io.ktor.client.HttpClient
import com.muhammad.brain.domain.utils.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified Response : Any> HttpClient.get(
    route : String, queryParameters : Map<String, Any?> = mapOf()
) : Result<Response>{
    return safeCall {
        get{
            url(route)
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(
    crossinline execute: suspend () -> HttpResponse,
): Result<T> {
    val response = try {
        execute()
    } catch (e: CancellationException) {
        throw e
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error("No internet connection!")
    } catch (e: UnknownHostException) {
        e.printStackTrace()
        return Result.Error("No internet connection!")
    } catch (e: ConnectException) {
        e.printStackTrace()
        return Result.Error("Unable to connect to server!")
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        return Result.Error("Connection timeout!")
    } catch (e: IOException) {
        e.printStackTrace()
        return Result.Error("Network error!")
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error("Serialization error!")
    } catch (e: Exception) {
        e.printStackTrace()
        return Result.Error("Something went wrong!")
    }
    return responseToResult(response)
}


suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T> {
    return when (response.status.value) {
        in 200..299 -> {
            val body = try {
                response.body<T>()
            } catch (e: SerializationException) {
                e.printStackTrace()
                return Result.Error("Parsing error")
            }
            Result.Success(body)
        }

        401 -> Result.Error("You are logged out!")
        403 -> Result.Error("Access denied!")
        404 -> Result.Error("Not found!")
        408 -> Result.Error("Request timeout")
        413 -> Result.Error("Payload too large")
        429 -> Result.Error("Too many requests!")
        in 500..599 -> Result.Error("Server error!")
        else -> Result.Error("Something went wrong (${response.status.value})")
    }
}