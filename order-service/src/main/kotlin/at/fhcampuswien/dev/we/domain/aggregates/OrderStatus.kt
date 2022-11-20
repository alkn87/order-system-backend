package at.fhcampuswien.dev.we.domain.aggregates

enum class OrderStatus {
    CREATED,
    PROCESSING,
    READY,
    DELIVERED
}
