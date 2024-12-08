package org.akm.conditionservice.dto

data class ViolationDto(
    val ruleId: String,
    val ruleName: String,
    val message: String,
)
