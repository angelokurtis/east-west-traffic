package com.kurtis.server

import com.google.common.util.concurrent.ThreadFactoryBuilder
import io.grpc.ServerBuilder
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

private const val PORT = 6565
private val logger = KotlinLogging.logger {}

@Component
class GRPCServer(val helloService: HelloService) {

    private val executor = Executors.newSingleThreadScheduledExecutor(ThreadFactoryBuilder()
            .setNameFormat("grpc-server")
            .setDaemon(true)
            .setUncaughtExceptionHandler { t, e -> logger.error("Thread " + t.name + " execution failed", e) }
            .build())

    @PostConstruct
    fun init() {
        logger.info { "Starting gRPC Server" }
        this.executor.scheduleAtFixedRate({ this.startGRPCServer() }, 0, 2, TimeUnit.SECONDS)
    }

    @PreDestroy
    fun finish() {
        logger.info { "Stopping gRPC Server" }
        this.executor.shutdown()
    }

    private fun startGRPCServer() {
        val server = ServerBuilder.forPort(PORT)
                .addService(helloService)
                .build()
        logger.info { "Running the gRPC Server on port $PORT" }

        server.start()
        server.awaitTermination()
    }

}