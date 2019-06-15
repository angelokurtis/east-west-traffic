package com.kurtis.server

import io.grpc.ServerBuilder
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private const val PORT = 8080
private val logger = KotlinLogging.logger {}

@SpringBootApplication
class ServerApplication(val helloService: HelloService) : ApplicationRunner {
	override fun run(args: ApplicationArguments) {
		val server = ServerBuilder
				.forPort(PORT)
				.addService(helloService)
				.build()
		logger.info { "Running the gRPC Server on port $PORT" }

		server.start()
		server.awaitTermination()
	}
}

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
