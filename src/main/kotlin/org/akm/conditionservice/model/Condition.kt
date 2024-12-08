package org.akm.conditionservice.model

data class Condition(
    val field: String,
    val operator: String,
    val value: String
) {
    constructor() : this("", "", "") {}
}