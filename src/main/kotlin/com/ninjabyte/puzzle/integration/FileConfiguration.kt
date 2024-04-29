package com.ninjabyte.puzzle.integration

import com.ninjabyte.puzzle.entities.Photo
import com.ninjabyte.puzzle.integration.transformer.PhotoFromFileHandler
import jakarta.persistence.EntityManagerFactory
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.StandardIntegrationFlow
import org.springframework.integration.dsl.integrationFlow
import org.springframework.integration.file.FileReadingMessageSource
import org.springframework.integration.file.FileWritingMessageHandler
import org.springframework.integration.file.dsl.Files
import org.springframework.integration.file.filters.AcceptOnceFileListFilter
import org.springframework.integration.file.filters.CompositeFileListFilter
import org.springframework.integration.file.filters.IgnoreHiddenFileListFilter
import org.springframework.integration.file.filters.SimplePatternFileListFilter
import org.springframework.integration.file.support.FileExistsMode
import org.springframework.integration.jpa.dsl.Jpa
import org.springframework.integration.jpa.support.PersistMode
import org.springframework.messaging.MessageHandler
import java.io.File


@Configuration
class FileConfiguration(
    private val channels: ChannelsConfiguration,
    private val photoFromFileHandler: PhotoFromFileHandler,
    private val entityManagerFactory: EntityManagerFactory
) {

    private val input = File("raw")
    private val output = File("photos")

    @Bean
    fun filesFlow(): StandardIntegrationFlow {
        // https://stackoverflow.com/questions/49916429/watcheventtype-delete-doesnt-seem-to-work
        val filters: CompositeFileListFilter<File?> = CompositeFileListFilter()
        filters.addFilter(IgnoreHiddenFileListFilter())
        filters.addFilter(AcceptOnceFileListFilter())
        filters.addFilter(SimplePatternFileListFilter("*.png"))


        // filters.addFilter(myCustomRemovalFilter)


        // TODO add watcher
        return integrationFlow(
            Files.inboundAdapter(this.input)
                .autoCreateDirectory(true)
                .filter(filters)
                .useWatchService(true)
                .watchEvents(
                    FileReadingMessageSource.WatchEventType.CREATE
                ),
            { poller { it.fixedDelay(1000).maxMessagesPerPoll(1) } }
        ) {
            channel(channels.image())
            handle(photoFromFileHandler, "persist")
        }
    }


    @Bean
    fun imageFlow(): StandardIntegrationFlow =
        integrationFlow(channels.image()) {
            handle(
                Files.outboundAdapter(output)
                    .autoCreateDirectory(true)
                    .deleteSourceFiles(true)
                    .fileExistsMode(FileExistsMode.REPLACE)
            )
            // or
            // handle(targetDirectory())
        }


    @Bean
    @Transactional
    fun jpaOutboundAdapterFlow(): IntegrationFlow =
        integrationFlow(channels.databaseChannel()) {
            handle(
                Jpa.outboundAdapter(entityManagerFactory)
                    .entityClass(Photo::class.java)
                    .persistMode(PersistMode.PERSIST)
            )
        }


    @Bean
    fun targetDirectory(): MessageHandler {
        val handler = FileWritingMessageHandler(output)
        handler.setFileExistsMode(FileExistsMode.REPLACE)
        handler.setExpectReply(false)
        return handler
    }

}