package com.ninjabyte.puzzle.scheduler

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
class SchedulerConfig : SchedulingConfigurer {
    private val POOL_SIZE = 10

    override fun configureTasks(scheduledTaskRegistrar: ScheduledTaskRegistrar) {
        val threadPoolTaskScheduler = ThreadPoolTaskScheduler()

        threadPoolTaskScheduler.poolSize = POOL_SIZE
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-")
        threadPoolTaskScheduler.initialize()

        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler)
    }
}