package lab.orders

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

data class Order(val id: Int, val item: String, val qty: Int)

// Simulated slow lookup (e.g. a price service). delay() suspends; it does not block a thread.
suspend fun price(order: Order): Int {
    delay(200)
    return order.qty * 3
}

fun main() = runBlocking {
    val orders = listOf(Order(1, "book", 2), Order(2, "pen", 10), Order(3, "ink", 1))
    var totals = emptyList<Int>()
    val ms = measureTimeMillis {
        totals = orders.map { async { price(it) } }.awaitAll()
    }
    println("totals=$totals in ${ms}ms")
    check(ms < 500) { "expected concurrent lookups (~200ms), got ${ms}ms" }
    println("OK: 3 lookups of 200ms ran concurrently on one thread")
}
