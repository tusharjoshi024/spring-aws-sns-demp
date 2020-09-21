package com.example.springsnsdemo.client;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TextMessageClient {

    public void sendSMS(String contact, String message){

        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("MFAApp") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional") //Sets the type to Transactional.
                .withDataType("String"));

        AmazonSNSClient snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.defaultClient();

        snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(contact) //with the format "+1XXX123XX08"
                .withMessageAttributes(smsAttributes));

    }

}
