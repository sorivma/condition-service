package org.akm.conditionservice.dto

import com.fasterxml.jackson.databind.JsonNode
import org.akm.conditionservice.model.RuleType

data class CheckDto(
    val inn: String,
    val data: JsonNode?,
    val type: RuleType,
)