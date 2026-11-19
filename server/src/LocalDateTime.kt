package apistandards

import com.apollographql.apollo.api.json.ApolloJsonElement
import com.apollographql.apollo.ast.GQLStringValue
import com.apollographql.apollo.ast.GQLValue
import com.apollographql.apollo.execution.Coercing
import galak.annotation.GraphQLScalar
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * A date and time without time zone, as ISO-8601, e.g. "2026-12-01T11:00:00"
 */
@GraphQLScalar(LocalDateTimeCoercing::class)
typealias LocalDateTime = java.time.LocalDateTime

object LocalDateTimeCoercing : Coercing<LocalDateTime> {
    // toString() omits ":00" seconds, so format explicitly to always get "HH:mm:ss"
    override fun serialize(internalValue: LocalDateTime): ApolloJsonElement =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(internalValue)

    override fun deserialize(value: ApolloJsonElement): LocalDateTime {
        check(value is String) { "'$value' cannot be deserialized to a LocalDateTime, expected a String." }
        return parse(value)
    }

    override fun parseLiteral(value: GQLValue): LocalDateTime {
        check(value is GQLStringValue) { "'$value' cannot be parsed to a LocalDateTime, expected a String." }
        return parse(value.value)
    }

    private fun parse(value: String): LocalDateTime = try {
        LocalDateTime.parse(value)
    } catch (e: DateTimeParseException) {
        error("'$value' is not a valid ISO-8601 local date-time (expected YYYY-MM-DDTHH:MM[:SS]).")
    }
}
