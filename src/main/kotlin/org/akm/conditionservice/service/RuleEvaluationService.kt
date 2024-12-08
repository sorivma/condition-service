package org.akm.conditionservice.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.akm.conditionservice.dto.CheckDto
import org.akm.conditionservice.dto.ViolationDto
import org.akm.conditionservice.model.Condition
import org.akm.conditionservice.repostiory.RuleRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class RuleEvaluationService(
    private val ruleRepository: RuleRepository,
    @Qualifier("clickhouse")
    private val jdbcTemplate: JdbcTemplate,
) {
    val logger = LoggerFactory.getLogger(this::class.java)

    fun evaluateRules(checkDto: CheckDto): List<ViolationDto> {
        val violations = mutableListOf<ViolationDto>()
        val rules = ruleRepository.getAllByType(checkDto.type)
        for (rule in rules) {
            val conditions = ObjectMapper().readValue(rule.conditions, object : TypeReference<List<Condition>>() {})
            val whereClause = buildWhereClause(conditions, checkDto.inn)
            val query = "SELECT COUNT(*) FROM ${rule.schema}.${rule.table} WHERE $whereClause"

            val count = jdbcTemplate.queryForObject(query, Int::class.java) ?: 0
            if (rule.countGreaterThanZero) {
                if (count > 0) {
                    violations.add(
                        ViolationDto(
                            ruleId = rule.id,
                            ruleName = rule.name,
                            message = rule.description
                        )
                    )
                }
            } else {
                if (count == 0) {
                    violations.add(
                        ViolationDto(
                            ruleId = rule.id,
                            ruleName = rule.name,
                            message = rule.description
                        )
                    )
                }
            }
        }

        logger.info("Found ${violations.size} violation(s) for inn ${checkDto.inn}")

        return violations
    }

    private fun buildWhereClause(conditions: List<Condition>, inn: String): String {
        val conditionsList = mutableListOf<String>()

        conditionsList.add("INN = '$inn'")

        conditions.forEach { condition ->
            conditionsList.add("${condition.field} ${condition.operator} ${condition.value}")
        }

        return conditionsList.joinToString(" AND ")
    }
}