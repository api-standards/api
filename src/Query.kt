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
    fun days(): List<Day> = Agenda.days

    /**
     * All sessions in chronological order, optionally filtered by day (1, 2 or 3)
     */
    fun sessions(@GraphQLDefault("null") day: Int?): List<Session> =
        Agenda.sessions.filter { day == null || it.day == day }

    /**
     * A session by id, or null if no such session exists
     */
    fun session(id: String): Session? = Agenda.sessions.firstOrNull { it.id == id }

    /**
     * All speakers, sorted by name
     */
    fun speakers(): List<Speaker> = Agenda.speakers

    /**
     * A speaker by id, or null if no such speaker exists
     */
    fun speaker(id: String): Speaker? = Agenda.speakers.firstOrNull { it.id == id }
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
    fun sessions(): List<Session> = Agenda.sessions.filter { it.day == number }
}

/**
 * A talk, panel or other agenda slot that has a title
 */
class Session internal constructor(
    val id: String,
    val title: String,
    /**
     * The day number: 1, 2 or 3
     */
    val day: Int,
    /**
     * Start time in Paris local time (CET, UTC+01:00), e.g. "2026-12-01T11:00:00"
     */
    val startsAt: LocalDateTime,
    /**
     * End time in Paris local time (CET, UTC+01:00), e.g. "2026-12-01T11:25:00"
     */
    val endsAt: LocalDateTime,
    internal val speakerIds: List<String>,
) {
    fun speakers(): List<Speaker> = speakerIds.map { id -> Agenda.speakers.first { it.id == id } }
}

class Speaker internal constructor(
    val id: String,
    val name: String,
) {
    /**
     * The sessions this speaker presents, in chronological order
     */
    fun sessions(): List<Session> = Agenda.sessions.filter { id in it.speakerIds }
}
