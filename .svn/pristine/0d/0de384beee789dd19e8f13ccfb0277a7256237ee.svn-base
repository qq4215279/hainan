package com.gobestsoft.rabbitmq;

import com.gobestsoft.rabbitmq.config.MQConstant;
import com.gobestsoft.rabbitmq.processor.AbstractProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Rabbit消息接收者
 * Created by gutongwei on 2018/2/7.
 */
//@Component
public class RabbitReceive {
    private Logger logger = LoggerFactory.getLogger(RabbitReceive.class);

    /**
     * 监听默认消息列队
     * <p>
     * MQConstant.ADMIN_GIVE_API_QUEUE
     *
     * @param str
     */
//    @RabbitListener(queues = MQConstant.ADMIN_GIVE_API_QUEUE)
    public void receiveMessage(String str) {
        System.out.println(str);
    }

    /**
     * 监听延迟的任务
     * MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME
     *
     * @param processor
     */
//    @RabbitListener(queues = MQConstant.DEFAULT_LETTER_RECEIVE_QUEUE_NAME)
    public void receiveDelayMessage(AbstractProcessor processor) {
    }
}