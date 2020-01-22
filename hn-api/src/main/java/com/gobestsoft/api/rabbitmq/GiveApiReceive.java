package com.gobestsoft.api.rabbitmq;

import com.gobestsoft.common.modular.dao.mapper.AppIntegralMapper;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.rabbitmq.config.MQConstant;
import com.gobestsoft.rabbitmq.processor.PointProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//import com.gobestsoft.rabbitmq.processor.ExcludeLoginProcessor;
//import com.gobestsoft.rabbitmq.processor.MessageProcessor;

/**
 * create by gutongwei
 * on 2018/7/3 上午11:30
 */
@Component
public class GiveApiReceive {

    @Autowired
    private AppIntegralMapper appIntegralMapper;


    private Logger logger = LoggerFactory.getLogger(GiveApiReceive.class);

    @Resource
    private RedisUtils redisUtils;

    @RabbitListener(queues = MQConstant.DEFAULT_LETTER_SENDER_QUEUE_NAME)
    public void receiveMessage(PointProcessor pro) {
        if (pro != null) {
            logger.info("队列中更新积分{}", pro);

            appIntegralMapper.integralHouston(pro.getAuid(), DateUtil.getAllTime(), pro.getTaskId());
            appIntegralMapper.taskComplete(pro.getAuid(), pro.getTaskId(), DateUtil.getAllTime());

        }
    }




}
