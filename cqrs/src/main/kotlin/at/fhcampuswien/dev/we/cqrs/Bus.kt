package at.fhcampuswien.dev.we.cqrs

interface Bus<BusType> {
    fun dispatch(busTypeEvent: BusType): Any?
}
