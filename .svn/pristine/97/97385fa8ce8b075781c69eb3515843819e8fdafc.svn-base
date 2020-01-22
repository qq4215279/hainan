package com.gobestsoft.admin.rabbitMq;

import com.gobestsoft.mamage.moudle.app.service.MessageService;
import com.gobestsoft.mamage.moudle.dept.service.DeptMemberService;
import com.gobestsoft.rabbitmq.config.MQConstant;
import com.gobestsoft.rabbitmq.processor.ExcludeLoginProcessor;
import com.gobestsoft.rabbitmq.processor.MessageProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.gobestsoft.rabbitmq.processor.ExcludeLoginProcessor;
//import com.gobestsoft.rabbitmq.processor.MessageProcessor;

/**
 * create by gutongwei
 * on 2018/7/3 上午11:30
 */
@Component
public class GiveAdminReceive {

    private Logger logger = LoggerFactory.getLogger(GiveAdminReceive.class);

    @Autowired
    private MessageService messageService;

    /**
     * 监听默认消息列队
     * <p>
     * MQConstant.ADMIN_GIVE_API_QUEUE
     *
     * @param pro
     */
    @RabbitListener(queues = MQConstant.API_GIBE_ADMIN)
    public void receiveMessage(MessageProcessor pro) {
        if (pro != null) {
            logger.info("推送消息数据{}", pro);
            messageService.sendMessageByAuidOperation(pro.getCategory(), pro.getTitle(), pro.getContent(),
                    pro.getMode(), pro.getTargetId(), pro.getJumpUrl(), pro.isPush(), pro.getAuids(),"sys",pro.getOperation());
        }
    }



    /**
     * 监听默认消息列队
     * <p>
     * MQConstant.ADMIN_GIVE_API_QUEUE
     *
     * @param pro
     */
    @RabbitListener(queues = MQConstant.API_GIBE_ADMIN_EXCLUDE)
    public void receiveMessage(ExcludeLoginProcessor pro) {
        if (pro != null && StringUtils.isNotEmpty(pro.getRegistrationId())) {
            logger.info("推送消息数据{}", pro);
            messageService.loginExcludePush(pro.getRegistrationId());
        }
    }




}
