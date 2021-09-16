package com.dhieu.grpc;

import io.grpc.*;
import io.grpc.stub.*;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) throws Exception{
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext()
                .build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);

        // Construct a request
        GreetingServiceOuterClass.HelloRequest request =
                GreetingServiceOuterClass.HelloRequest.newBuilder()
                        .setName("DHieu")
                        .build();

        GreetingServiceOuterClass.HelloResponse response = stub.greeting(request);
        System.out.println(response);

    }
}
