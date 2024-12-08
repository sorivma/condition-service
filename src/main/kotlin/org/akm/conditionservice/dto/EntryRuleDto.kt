package org.akm.conditionservice.dto

import com.fasterxml.jackson.databind.JsonNode
import jakarta.persistence.Column
import jakarta.persistence.Id
import org.akm.conditionservice.model.RuleType

data class EntryRuleDto(
    @Id
    val id: String,
    val name: String,
    val description: String,
    val conditions: JsonNode,
    val schema: String,
    @Column(name = "table_name")
    val table: String,
    val type: RuleType,
    val countGreaterThanZero: Boolean,
)
