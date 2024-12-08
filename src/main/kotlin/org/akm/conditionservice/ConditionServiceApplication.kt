package org.akm.conditionservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConditionServiceApplication

fun main(args: Array<String>) {
    runApplication<ConditionServiceApplication>(*args)
}
