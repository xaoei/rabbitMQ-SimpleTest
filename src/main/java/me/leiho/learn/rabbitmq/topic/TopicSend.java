package me.leiho.learn.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: xl
 * @Date: 2017.12.17
 */
public class TopicSend {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        connection=factory.newConnection();
        channel=connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String[] routingKeys=new String[]{
                "quick.orange.rabbit",
                "lazy.orange.elephant",
                "quick.orange.fox",
                "lazy.brown.fox",
                "quick.brown.fox",
                "quick.orange.male.rabbit",
                "lazy.orange.male.rabbit"
        };
        for(String severity :routingKeys){
            String message = "From "+severity+" routingKey's message!";
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println("TopicSend Sent '" + severity + "':'" + message + "'");
        }
    }
}
