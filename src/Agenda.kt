package apistandards

import java.text.Normalizer

/**
 * The API Standards Conference agenda, transcribed from the
 * "API Standards Conference - FOST Paris 2026 - Agenda Day {1,2,3}.csv" exports.
 *
 * Breaks, meals and "End of Day" rows are left out. These source typos were fixed:
 * - Day 1 "12:00 PM - 12:25" (missing PM on the end time)
 * - Day 2 "12:20 AM - 12:45 PM" -> 12:20 PM
 * - Day 3 "11:30 PM - 11:55 PM" -> 11:30 AM
 * - Day 2 "Jean-Pierre DIjcks" -> "Jean-Pierre Dijcks"; "&" in the speaker cell is treated as two speakers
 * - Day 3 "Unconference / API standards 2027 working group" is kept as a session without speakers
 */
internal object Agenda {
    private val dates = mapOf(1 to "2026-12-01", 2 to "2026-12-02", 3 to "2026-12-03")

    // Paris is on CET (UTC+01:00) in December
    private const val OFFSET = "+01:00"

    private class Row(val day: Int, val start: String, val end: String, val title: String, val speakers: List<String>)

    private fun row(day: Int, start: String, end: String, title: String, vararg speakers: String) =
        Row(day, start, end, title, speakers.toList())

    private val rows = listOf(
        // Day 1: December 1
        row(1, "11:00", "11:25", "API Standards Conference Welcome and OpenAPI in the Age of AI", "Erik Wilde"),
        row(1, "11:30", "11:55", "GraphQL Variables Meet JSON Schema: The New Pitfall When AI Agents Call Your API", "Camille Lawrence"),
        row(1, "12:00", "12:25", "Securing Open Source CI/CD Pipelines: Lessons from Handling 2 Supply Chain Incidents", "Florence Njeri", "Lukasz Gornicki"),
        row(1, "12:30", "12:55", "Make Your API Metadata AI-Ready and Automatically Discoverable by Agents", "Galina Tuova"),
        row(1, "14:00", "14:25", "Beyond Validation: Reasoning About What a Schema Means", "Jason Desrosiers"),
        row(1, "14:30", "14:55", "One to many without breaking anyone: evolving a GraphQL schema with many consumers", "Gabriela Colombo"),
        row(1, "15:00", "15:25", "Beyond the Broker: What Event Gateways Are Really Trying to Solve", "Daniel Kocot"),
        row(1, "15:30", "15:55", "Arazzo: Turning Business Workflows into Agent Capabilities via MCP and Beyond", "Frank Kilcommins"),
        row(1, "16:30", "16:55", "Not Yet Valid: Four Questions Your Editor Asks a JSON Schema", "Bart Dubbeldam"),
        row(1, "17:00", "17:25", "oasdiff vs Claude: A Case Study in LLMs vs Dedicated Tools", "Reuven Harrison"),

        // Day 2: December 2
        row(2, "11:20", "11:45", "Explaining GraphQL Federation to my non-technical wife", "David Stutt"),
        row(2, "11:50", "12:15", "JSON Schema Standardization and Versions", "Lisa Dusseault"),
        row(2, "12:20", "12:45", "Events as Contracts, Events as Data: Our AsyncAPI Journey", "Ignacio Castillejos"),
        row(2, "14:00", "14:25", "Your Portal Has Two Kinds of Visitors Now: Arazzo Workflows for Humans and Agents", "Pubudu Gunatilaka"),
        row(2, "14:30", "14:55", "From Validation to Semantic Guardrails: JSON Schema as the Contract Layer for Agentic Application Development", "Jean-Pierre Dijcks", "Beda Hammerschmidt"),
        row(2, "15:00", "15:25", "From Schema Registry to Agent Registry: Governing AI Discovery with the A2A Protocol", "Carles Arnal"),
        row(2, "15:30", "15:55", "Event Contracts Are Domain Decisions", "Annegret Junker"),
        row(2, "16:30", "16:55", "GraphQL as the Execution Layer for AI Agents", "Pascal Senn"),
        row(2, "17:00", "17:25", "Your AI Agent Is Only as Good as Its Schema: Designing Reliable MCP Tools with JSON Schema", "Ayodele Aransiola"),
        row(2, "17:30", "17:55", "OpenAPI: The Answer Key for MCP Tool Calling", "Miguel Quintero"),

        // Day 3: December 3
        row(3, "09:15", "09:40", "Beyond the Query: GraphQL Features That Make Frontends Simpler", "Aurelien David"),
        row(3, "09:45", "10:10", "Beyond REST: Evolving Arazzo for Real-World Agentic Workflows", "Naresh Jain"),
        row(3, "10:15", "10:40", "Houston, We Have a Partial Response: GraphQL Error Handling", "Željko Kozina"),
        row(3, "11:00", "11:25", "Let’s Help Agents to Navigate the API and the Data World", "Simon Harrer"),
        row(3, "11:30", "11:55", "Foundational JSON Schema", "Ben Hutton"),
        row(3, "12:00", "12:25", "GraphQL with AWS AppSync - Building a Product API at HEMA", "Ivan Jančić", "Bart Rijksen"),
        row(3, "12:30", "12:55", "The Risks That Come with Underfunded API Standards", "Thulisile Sibanda"),
        row(3, "14:00", "14:25", "TBA", "Matthew Adams"),
        row(3, "14:30", "15:25", "AsyncAPI Ambassador Panel - Can AsyncAPI help AI Agents Speak Event-Driven?", "Hugo Guerrero", "Naresh Jain", "Ludovic Dussart"),
        row(3, "15:55", "16:20", "Unconference: API standards 2027 working group"),
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

    private fun timestamp(day: Int, time: String) = "${dates.getValue(day)}T$time:00$OFFSET"

    val days: List<Day> = dates.map { (number, date) -> Day(number, date) }

    val sessions: List<Session> = rows.map { row ->
        Session(
            id = "day${row.day}-${row.start.replace(":", "")}",
            title = row.title,
            day = row.day,
            startsAt = timestamp(row.day, row.start),
            endsAt = timestamp(row.day, row.end),
            speakerIds = row.speakers.map(::slug),
        )
    }

    val speakers: List<Speaker> = rows.flatMap { it.speakers }
        .distinct()
        .sortedBy { it.lowercase() }
        .map { Speaker(id = slug(it), name = it) }
}
