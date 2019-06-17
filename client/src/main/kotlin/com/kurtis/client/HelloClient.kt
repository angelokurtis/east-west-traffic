package com.kurtis.client

import com.kurtis.grpc.HelloRequest
import com.kurtis.grpc.HelloServiceGrpc
import org.springframework.stereotype.Component

@Component
class HelloClient(val helloService: HelloServiceGrpc.HelloServiceBlockingStub) {

    fun hello(): String? {
        val request = HelloRequest.newBuilder()
                .setFirstName("Tiago")
                .setLastName("Angelo")
                .build()
        val response = helloService.hello(request)
        return response.greeting
    }
}
