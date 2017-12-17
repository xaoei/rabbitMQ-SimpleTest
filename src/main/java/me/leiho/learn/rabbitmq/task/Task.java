package me.leiho.learn.rabbitmq.task;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: xl
 * @Date: 2017.12.17
 */
public class Task {
    private static final String TASK_QUEUE_NAME="task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
        for (int i = 0;i < 10;i ++){
            String message = "Hello lester,this msg from RabbitMQ "+i+".";
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
            System.out.println("Producer Send '"+message+"'");
        }
        channel.close();
        connection.close();
    }
}
