package org.akm.conditionservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityNotFoundException
import org.akm.conditionservice.dto.CheckDto
import org.akm.conditionservice.dto.EntryRuleDto
import org.akm.conditionservice.dto.ViolationDto
import org.akm.conditionservice.model.EntryRule
import org.akm.conditionservice.repostiory.RuleRepository
import org.akm.conditionservice.service.RuleEvaluationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/rules")
class RuleController(
    private val ruleRepository: RuleRepository,
    private val ruleEvaluationService: RuleEvaluationService
) {
    @PostMapping
    fun createRule(@RequestBody rule: EntryRuleDto): EntryRule {
        return ruleRepository.save(rule.toEntity())
    }

    @GetMapping
    fun getRules(): List<EntryRule> {
        return ruleRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getRule(@PathVariable id: String): EntryRule {
        return ruleRepository.findById(id).orElseThrow { EntityNotFoundException("No rule with id $id") }
    }

    @PostMapping("/evaluate")
    fun evaluateRule(@RequestBody checkDto: CheckDto): List<ViolationDto> {
        return ruleEvaluationService.evaluateRules(checkDto)
    }
}

private fun EntryRuleDto.toEntity(): EntryRule {
    return EntryRule(
        id = this.id,
        name = this.name,
        description = this.description,
        conditions = ObjectMapper().writeValueAsString(this.conditions),
        schema = this.schema,
        table = this.table,
        type = this.type,
        countGreaterThanZero = this.countGreaterThanZero,
    )
}
