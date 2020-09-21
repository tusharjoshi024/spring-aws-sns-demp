# Amazon AWS SNS Demo with Spring Boot

Use AWS SNS service to send text messages to cell numbers from the Spring Boot Program. 

### Steps to create the AWS Access Keys
https://docs.aws.amazon.com/general/latest/gr/aws-security-credentials.html


### Steps to use Amazon AWS Java SDK
1. Obtain the AWS Credentials and programmatic access keys.

2. Set the environment variables as follows - 
   
    ###### For Linux   
        export AWS_ACCESS_KEY_ID = < your_aws_access_key_id >
        export AWS_SECRET_ACCESS_KEY = < your_aws_secret_access_key >
        
    ###### For Windows
        set AWS_ACCESS_KEY_ID = < your_aws_access_key_id >
        set AWS_SECRET_ACCESS_KEY = < your_aws_secret_access_key >
       
3. Add following dependencies in the build.gradle - 

    ###### build.gradle
        implementation platform('com.amazonaws:aws-java-sdk-bom:1.11.415')
        implementation 'com.amazonaws:aws-java-sdk-sns'
               
4. Create the component that makes use of SNS API from AWS SNS SDK

    ###### TextMessageClient.java
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
                        .withPhoneNumber(contact) //should have the format "+1XXX123XX08"
                        .withMessageAttributes(smsAttributes));
        
            }
        
        }
          
5. Usage -  

    ###### Service Layer
        @Autowired
        private TextMessageClient messageClient;
        
        public void textPerson(Long id, String message) {
            if(this.getPerson(id)==null || this.getPerson(id).getContact().isEmpty()){
                //handle the error here
            }
            else {
                messageClient.sendSMS(this.getPerson(id).getContact(),message);
            }
        }
    ###### Controller
        @GetMapping("/{id}/contact")
        public void textPerson(@PathVariable Long id, @RequestParam String message){
            service.textPerson(id,message);
        }           

### Reference Documentation - 
https://docs.aws.amazon.com/sns/latest/dg/sms_publish-to-phone.html