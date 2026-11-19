package apistandards

import java.text.Normalizer

/**
 * The API Standards Conference agenda, transcribed from the
 * "API Standards Conference - FOST Paris 2026 - Agenda Day {1,2,3}.csv" exports.
 *
 * "End of Day" rows are left out. These source typos were fixed:
 * - Day 1 "12:00 PM - 12:25" (missing PM on the end time)
 * - Day 2 "12:20 AM - 12:45 PM" -> 12:20 PM
 * - Day 3 "11:30 PM - 11:55 PM" -> 11:30 AM
 * - Day 2 "Jean-Pierre DIjcks" -> "Jean-Pierre Dijcks"; "&" in the speaker cell is treated as two speakers
 * - Day 3 "Unconference / API standards 2027 working group" is kept as a session without speakers
 */
internal object Agenda {
    private val dates = mapOf(1 to "2026-12-01", 2 to "2026-12-02", 3 to "2026-12-03")

    // Times are Paris local time (CET, UTC+01:00 in December)
    private fun timestamp(day: Int, time: String): LocalDateTime =
        LocalDateTime.parse("${dates.getValue(day)}T$time:00")

    private fun id(day: Int, start: String) = "day$day-${start.replace(":", "")}"

    private fun session(day: Int, start: String, end: String, title: String, vararg speakers: String) = Session(
        id = id(day, start),
        title = title,
        description = AgendaDetails.sessionDescriptions[id(day, start)],
        day = day,
        startsAt = timestamp(day, start),
        endsAt = timestamp(day, end),
        speakerNames = speakers.toList(),
    )

    private fun service(day: Int, start: String, end: String, title: String) = ServiceSlot(
        id = id(day, start),
        title = title,
        day = day,
        startsAt = timestamp(day, start),
        endsAt = timestamp(day, end),
    )

    /**
     * The whole agenda in chronological order.
     *
     * Breaks and lunches are inferred from the 15+ minute gaps between sessions:
     * lunch is the gap ending at 14:00, the others are breaks.
     */
    val slots: List<Slot> = listOf(
        // Day 1: December 1
        session(1, "11:00", "11:25", "API Standards Conference Welcome and OpenAPI in the Age of AI", "Erik Wilde"),
        session(1, "11:30", "11:55", "GraphQL Variables Meet JSON Schema: The New Pitfall When AI Agents Call Your API", "Camille Lawrence"),
        session(1, "12:00", "12:25", "Securing Open Source CI/CD Pipelines: Lessons from Handling 2 Supply Chain Incidents", "Florence Njeri", "Lukasz Gornicki"),
        session(1, "12:30", "12:55", "Make Your API Metadata AI-Ready and Automatically Discoverable by Agents", "Galina Tuova"),
        service(1, "12:55", "14:00", "Lunch"),
        session(1, "14:00", "14:25", "Beyond Validation: Reasoning About What a Schema Means", "Jason Desrosiers"),
        session(1, "14:30", "14:55", "One to many without breaking anyone: evolving a GraphQL schema with many consumers", "Gabriela Colombo"),
        session(1, "15:00", "15:25", "Beyond the Broker: What Event Gateways Are Really Trying to Solve", "Daniel Kocot"),
        session(1, "15:30", "15:55", "Arazzo: Turning Business Workflows into Agent Capabilities via MCP and Beyond", "Frank Kilcommins"),
        service(1, "15:55", "16:30", "Break"),
        session(1, "16:30", "16:55", "Not Yet Valid: Four Questions Your Editor Asks a JSON Schema", "Bart Dubbeldam"),
        session(1, "17:00", "17:25", "oasdiff vs Claude: A Case Study in LLMs vs Dedicated Tools", "Reuven Harrison"),

        // Day 2: December 2
        session(2, "11:20", "11:45", "Explaining GraphQL Federation to my non-technical wife", "David Stutt"),
        session(2, "11:50", "12:15", "JSON Schema Standardization and Versions", "Lisa Dusseault"),
        session(2, "12:20", "12:45", "Events as Contracts, Events as Data: Our AsyncAPI Journey", "Ignacio Castillejos"),
        service(2, "12:45", "14:00", "Lunch"),
        session(2, "14:00", "14:25", "Your Portal Has Two Kinds of Visitors Now: Arazzo Workflows for Humans and Agents", "Pubudu Gunatilaka"),
        session(2, "14:30", "14:55", "From Validation to Semantic Guardrails: JSON Schema as the Contract Layer for Agentic Application Development", "Jean-Pierre Dijcks", "Beda Hammerschmidt"),
        session(2, "15:00", "15:25", "From Schema Registry to Agent Registry: Governing AI Discovery with the A2A Protocol", "Carles Arnal"),
        session(2, "15:30", "15:55", "Event Contracts Are Domain Decisions", "Annegret Junker"),
        service(2, "15:55", "16:30", "Break"),
        session(2, "16:30", "16:55", "GraphQL as the Execution Layer for AI Agents", "Pascal Senn"),
        session(2, "17:00", "17:25", "Your AI Agent Is Only as Good as Its Schema: Designing Reliable MCP Tools with JSON Schema", "Ayodele Aransiola"),
        session(2, "17:30", "17:55", "OpenAPI: The Answer Key for MCP Tool Calling", "Miguel Quintero"),

        // Day 3: December 3
        session(3, "09:15", "09:40", "Beyond the Query: GraphQL Features That Make Frontends Simpler", "Aurelien David"),
        session(3, "09:45", "10:10", "Beyond REST: Evolving Arazzo for Real-World Agentic Workflows", "Naresh Jain"),
        session(3, "10:15", "10:40", "Houston, We Have a Partial Response: GraphQL Error Handling", "Željko Kozina"),
        service(3, "10:40", "11:00", "Break"),
        session(3, "11:00", "11:25", "Let’s Help Agents to Navigate the API and the Data World", "Simon Harrer"),
        session(3, "11:30", "11:55", "Foundational JSON Schema", "Ben Hutton"),
        session(3, "12:00", "12:25", "GraphQL with AWS AppSync - Building a Product API at HEMA", "Ivan Jančić", "Bart Rijksen"),
        session(3, "12:30", "12:55", "The Risks That Come with Underfunded API Standards", "Thulisile Sibanda"),
        service(3, "12:55", "14:00", "Lunch"),
        session(3, "14:00", "14:25", "TBA", "Matthew Adams"),
        session(3, "14:30", "15:25", "AsyncAPI Ambassador Panel - Can AsyncAPI help AI Agents Speak Event-Driven?", "Hugo Guerrero", "Naresh Jain", "Ludovic Dussart"),
        service(3, "15:25", "15:55", "Break"),
        session(3, "15:55", "16:50", "Unconference: API standards 2027 working group"),
    )

    /**
     * "Željko Kozina" -> "zeljko-kozina"
     */
    private fun slug(name: String): String =
        Normalizer.normalize(name, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}"), "")
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), "-")
            .trim('-')

    val days: List<Day> = dates.map { (number, date) -> Day(number, LocalDate.parse(date)) }

    val sessions: List<Session> = slots.filterIsInstance<Session>()

    val speakers: List<Speaker> = sessions.flatMap { it.speakerNames }
        .distinct()
        .sortedBy { it.lowercase() }
        .map { name ->
            val details = AgendaDetails.speakers[name]
            Speaker(
                id = slug(name),
                name = name,
                title = details?.title,
                company = details?.company,
                bio = details?.bio,
                social = details?.linkedin,
            )
        }
}
