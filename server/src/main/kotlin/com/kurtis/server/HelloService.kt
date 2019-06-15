package com.kurtis.server

import com.kurtis.grpc.HelloRequest
import com.kurtis.grpc.HelloResponse
import com.kurtis.grpc.HelloServiceGrpc
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class HelloService : HelloServiceGrpc.HelloServiceImplBase() {

    override fun hello(request: HelloRequest, responseObserver: StreamObserver<HelloResponse>) {
        logger.info { "Greetings for ${request.firstName}" }

        val greeting = StringBuilder()
                .append("Hello, ")
                .append(request.firstName)
                .append(" ")
                .append(request.lastName)
                .toString()

        val response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}