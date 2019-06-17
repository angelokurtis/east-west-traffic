package com.kurtis.client

import com.kurtis.grpc.HelloServiceGrpc
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val logger = KotlinLogging.logger {}

@Configuration
class GRPCConfig {

    @Bean
    fun channel(@Value("\${grpc.address}") address: String, @Value("\${grpc.port}") port: Int): Channel {
        logger.info { "Initializing channel for //$address:$port" }
        return ManagedChannelBuilder.forAddress(address, port)
                .usePlaintext()
                .build()
    }

    @Bean
    fun helloService(channel: Channel): HelloServiceGrpc.HelloServiceBlockingStub {
        return HelloServiceGrpc.newBlockingStub(channel)
    }
}
