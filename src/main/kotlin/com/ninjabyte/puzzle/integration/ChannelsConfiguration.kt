package com.ninjabyte.puzzle.integration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageChannels

@Configuration
class ChannelsConfiguration {

    @Bean
    fun image() = MessageChannels.direct().getObject()

    @Bean
    fun databaseChannel() = MessageChannels.direct("db-inserter").getObject()

    @Bean
    fun errors() = MessageChannels.direct().getObject()
}