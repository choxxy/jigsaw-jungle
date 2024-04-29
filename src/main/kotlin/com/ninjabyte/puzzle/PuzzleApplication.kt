package com.ninjabyte.puzzle

import com.ninjabyte.puzzle.services.StorageService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


//@SpringBootApplication
//class PuzzleApplication
//
//@Bean
//fun init(storageService: StorageService): CommandLineRunner {
//    return CommandLineRunner { args: Array<String?>? ->
//        storageService.deleteAll()
//        storageService.init()
//    }
//}
//
//
//fun main(args: Array<String>) {
//    SpringApplication.run(PuzzleApplication::class.java, *args)
//}


@SpringBootApplication
class PuzzleApplication {
//    @Bean
//    fun commandLineRunner(storageService: StorageService): CommandLineRunner {
//        return CommandLineRunner { args: Array<String?>? ->
//            storageService.deleteAll()
//            storageService.init()
//        }
//    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(PuzzleApplication::class.java, *args)
        }
    }
}
