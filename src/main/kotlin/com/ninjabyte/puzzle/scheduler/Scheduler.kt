package com.ninjabyte.puzzle.scheduler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class Scheduler {

    // @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    fun fortyFiveMinutesUpdates() {
        logger.info("Pexel service hourly Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()))
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(Scheduler::class.java)
        private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    }
}
