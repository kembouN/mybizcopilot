package com.mybizcopilot.services;

import com.mybizcopilot.dto.requests.SendMessageRequest;

public interface IMessagesService {


    Void sendTextMessage(SendMessageRequest request);

    Void sendDocument(SendMessageRequest request);

    Void sendImage(SendMessageRequest request);

}
