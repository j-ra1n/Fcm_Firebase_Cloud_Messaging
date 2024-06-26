package com.example.fcm.fcm.service;

import com.example.fcm.fcm.controller.request.FcmMultiTokenRequest;
import com.example.fcm.fcm.controller.request.FcmSingleTokenRequest;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;


    public void sendMessageSingleDevice(FcmSingleTokenRequest token) throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle(token.getTitle())
                .setBody(token.getMessage())   // .setImage(token.getImage()) 이미지 사용할때
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token.getToken())
                .build();

        String response = firebaseMessaging.send(message);
        log.info(">>>> [ 메세지가 성공적으로 전송되었습니다. ] : {} <<<<", response);
    }

    public void sendMessageMultiDevice(FcmMultiTokenRequest token) throws FirebaseMessagingException {

        Notification notification = Notification.builder()
                .setTitle(token.getTitle())
                .setBody(token.getMessage())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(token.getTokens())
                .build();

        BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
        log.info(">>>> [ {}개의 메세지가 성공적으로 전송되었습니다. ] : {}", response.getSuccessCount(), response);
    }

}