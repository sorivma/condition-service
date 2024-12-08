package org.akm.conditionservice.repostiory


import org.akm.conditionservice.model.EntryRule
import org.akm.conditionservice.model.RuleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RuleRepository: JpaRepository<EntryRule, String> {
    fun getAllByType(type: RuleType): Iterable<EntryRule>
}
