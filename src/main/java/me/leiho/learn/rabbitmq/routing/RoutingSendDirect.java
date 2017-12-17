package me.leiho.learn.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: xl
 * @Date: 2017.12.17
 */
public class RoutingSendDirect {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        for (String routingKey:routingKeys){
            String message = "RoutingSendDirect send the message level:" + routingKey;
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes("UTF-8"));
            System.out.println("RoutingSendDirect send '"+routingKey +"' : '" + message+"'");
        }
        channel.close();
        connection.close();
    }
}
