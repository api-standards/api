package apistandards

import galak.annotation.GraphQLDefault
import galak.annotation.GraphQLQuery

/**
 * API Standards Conference, FOST Paris 2026 (December 1-3, 2026)
 */
@GraphQLQuery
class Query {
    /**
     * The conference days, in chronological order
     */
    fun days(): List<Day> = allDays

    /**
     * All sessions in chronological order, optionally filtered by day (1, 2 or 3)
     */
    fun sessions(@GraphQLDefault("null") day: Int?): List<Session> =
        allSessions.filter { day == null || it.day == day }

    /**
     * All slots (sessions, breaks and lunches) in chronological order, optionally filtered by day (1, 2 or 3)
     */
    fun slots(@GraphQLDefault("null") day: Int?): List<Slot> =
        allSlots.filter { day == null || it.day == day }

    /**
     * A session by id, or null if no such session exists
     */
    fun session(id: String): Session? = allSessions.firstOrNull { it.id == id }

    /**
     * All speakers, sorted by name
     */
    fun speakers(): List<Speaker> = allSpeakers

    /**
     * A speaker by id, or null if no such speaker exists
     */
    fun speaker(id: String): Speaker? = allSpeakers.firstOrNull { it.id == id }
}

/**
 * One day of the conference
 */
class Day internal constructor(
    /**
     * The day number: 1, 2 or 3
     */
    val number: Int,
    /**
     * The date, e.g. "2026-12-01"
     */
    val date: LocalDate,
) {
    /**
     * The sessions on this day, in chronological order
     */
    fun sessions(): List<Session> = allSessions.filter { it.day == number }

    /**
     * All slots on this day (sessions, breaks and lunches), in chronological order
     */
    fun slots(): List<Slot> = allSlots.filter { it.day == number }
}
