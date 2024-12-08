package org.akm.conditionservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "entry_rules")
data class EntryRule(
    @Id
    val id: String,
    val name: String,
    val description: String,
    val conditions: String,
    val schema: String,
    @Column(name = "table_name")
    val table: String,
    val type: RuleType,
    val countGreaterThanZero: Boolean
) {
}

