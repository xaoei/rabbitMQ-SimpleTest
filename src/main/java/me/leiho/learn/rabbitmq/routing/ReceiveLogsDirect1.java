package me.leiho.learn.rabbitmq.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: xl
 * @Date: 2017.12.17
 */
public class ReceiveLogsDirect1 {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String[] routingKeys = new String[]{"info" ,"warning"};

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        for (String routingKey:routingKeys){
            channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
            System.out.println("ReceiveLogsDirect exchange:"+EXCHANGE_NAME+",queue:"+queueName+",bindRoutingKey:"+routingKey);
        }
        System.out.println("ReceiveLogsDirect waiting for messages");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("ReceiveLogsDirect receive '"+envelope.getRoutingKey()+"':'"+message+"'");
            }
        };
        channel.basicConsume(queueName,true,consumer);
    }
}
