package com.dhieu.grpcchat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class ChatClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        ChatServiceGrpc.ChatServiceStub chatService = ChatServiceGrpc.newStub(channel);

        StreamObserver<Chat.ChatMessage> chat = chatService.chat(new StreamObserver<Chat.ChatMessageFromServer>() {
            @Override
            public void onNext(Chat.ChatMessageFromServer chatMessageFromServer) {
                System.out.println(chatMessageFromServer.getMessage().getFrom());
                System.out.println(chatMessageFromServer.getMessage().getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Disconnected");
            }

            @Override
            public void onCompleted() {

                System.out.println("Disconnected");

            }
        });

        for (int i = 0; i < 10; i++) {
            chat.onNext(Chat.ChatMessage.newBuilder().setFrom("DHieu").setMessage("hello").build());

        }
        channel.shutdown();
    }
}
