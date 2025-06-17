package com.mybizcopilot.services.impl;

import com.mybizcopilot.dto.requests.SendMessageRequest;
import com.mybizcopilot.entities.Client;
import com.mybizcopilot.entities.MessageType;
import com.mybizcopilot.entities.Messages;
import com.mybizcopilot.entities.StatutMessage;
import com.mybizcopilot.repositories.ClientRepository;
import com.mybizcopilot.repositories.MessageRepository;
import com.mybizcopilot.services.IMessagesService;
import com.mybizcopilot.validator.ObjectValidator;
import jakarta.transaction.Transactional;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@Service
public class MessagesService implements IMessagesService {

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Value("${app.ultramsg.url}")
    private String ultraUrl;

    @Value("${app.ultramsg.instance}")
    private String instance;

    @Value("${app.ultramsg.token}")
    private String ultraMsgToken;

    private final Logger log = LoggerFactory.getLogger(MessagesService.class);

    @Autowired
    private ObjectValidator<SendMessageRequest> messageValidator;

    /**
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Void sendTextMessage(SendMessageRequest request) {
        try{
            if (!hasInternetConnection())
                throw new RestClientException("Vous n'êtes pas connecté à internet");

            messageValidator.validate(request);

            for (Integer receiver: request.getReceivers()) {
                Client client = clientRepository.findById(receiver).get();

                if (client != null && !checkNumberIsValid(client).isEmpty()) {
                    RequestBody body = new FormBody.Builder()
                            .add("token", ultraMsgToken)
                            .add("to", checkNumberIsValid(client))
                            .add("body", request.getMessage())
                            .add("priority", "5")
                            .build();

                    Request apiRequest = new Request.Builder()
                            .url("https://api.ultramsg.com/instance124909/messages/chat")
                            .post(body)
                            .build();


                    Response response = okHttpClient.newCall(apiRequest).execute();

                    if (response.isSuccessful()){
                        messageRepository.save(
                                Messages.builder()
                                        .statutMsg(StatutMessage.SENT)
                                        .message(request.getMessage())
                                        .messageType(MessageType.TEXT)
                                        .receiver(client)
                                        .build()
                        );
                    }

                }else {
                    messageRepository.save(
                            Messages.builder()
                                    .statutMsg(StatutMessage.FAIL)
                                    .messageType(MessageType.TEXT)
                                    .message(request.getMessage())
                                    .receiver(client)
                                    .build()
                    );

                }
                log.error("Envoi impossible pour ");

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param request
     * @return
     */
    @Override
    public Void sendDocument(SendMessageRequest request) {
        return null;
    }

    /**
     * @param request
     * @return
     */
    @Override
    public Void sendImage(SendMessageRequest request) {
        return null;
    }

    private String checkNumberIsValid(Client client){
        try{
            String numero = client.getTelephoneUn().isEmpty() ? client.getTelephoneDeux() : client.getTelephoneUn();
            if (!numero.isEmpty()){
                Request request = new Request.Builder()
                        .url("https://api.ultramsg.com/instance124909/contacts/check?token=gqtxxm2jq9p8zjye&chatId="+numero+"&nocache=")
                        .get()
                        .build();
                Response response = okHttpClient.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return "";
                }else{
                    String responseBody = response.body().string();
                    return responseBody.contains("\"status\":\"valid\"") ? numero : "";
                }
            }
            return "";
        }catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean hasInternetConnection() {
        Request request = new Request.Builder()
                .url("https://www.ultramsg.com")
                .head()
                .build();

        try{
            Response response = okHttpClient.newCall(request).execute();
            return response.isSuccessful();
        }catch (IOException e){
            return false;
        }
    }
}
