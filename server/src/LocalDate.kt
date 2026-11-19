package apistandards

import com.apollographql.apollo.api.json.ApolloJsonElement
import com.apollographql.apollo.ast.GQLStringValue
import com.apollographql.apollo.ast.GQLValue
import com.apollographql.apollo.execution.Coercing
import galak.annotation.GraphQLScalar
import java.time.format.DateTimeParseException

/**
 * A calendar date without time or time zone, as ISO-8601, e.g. "2026-12-01"
 */
@GraphQLScalar(LocalDateCoercing::class)
typealias LocalDate = java.time.LocalDate

object LocalDateCoercing : Coercing<LocalDate> {
    override fun serialize(internalValue: LocalDate): ApolloJsonElement = internalValue.toString()

    override fun deserialize(value: ApolloJsonElement): LocalDate {
        check(value is String) { "'$value' cannot be deserialized to a LocalDate, expected a String." }
        return parse(value)
    }

    override fun parseLiteral(value: GQLValue): LocalDate {
        check(value is GQLStringValue) { "'$value' cannot be parsed to a LocalDate, expected a String." }
        return parse(value.value)
    }

    private fun parse(value: String): LocalDate = try {
        LocalDate.parse(value)
    } catch (e: DateTimeParseException) {
        error("'$value' is not a valid ISO-8601 date (expected YYYY-MM-DD).")
    }
}
