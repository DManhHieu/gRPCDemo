package com.dhieu.grpc;

import io.grpc.*;
import io.grpc.stub.*;

public class Client {
    public static void main(String[] args) throws Exception{
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext()
                .build();

        GreetingServiceGrpc.GreetingServiceStub stub = GreetingServiceGrpc.newStub(channel);

        // Construct a request
        GreetingServiceOuterClass.HelloRequest request =
                GreetingServiceOuterClass.HelloRequest.newBuilder()
                        .setName("Ray")
                        .build();

        // Make an Asynchronous call. Listen to responses w/ StreamObserver
        stub.greeting(request, new StreamObserver<GreetingServiceOuterClass.HelloResponse>() {
            public void onNext(GreetingServiceOuterClass.HelloResponse response) {
                System.out.println(response);
            }
            public void onError(Throwable t) {
                System.out.println("ERROR");
            }
            public void onCompleted() {
                System.out.println("Complete");
                // Typically you'll shutdown the channel somewhere else.
                // But for the purpose of the lab, we are only making a single
                // request. We'll shutdown as soon as this request is done.
                channel.shutdownNow();
            }
        });

    }
}
