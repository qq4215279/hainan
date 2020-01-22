package com.gobestsoft.mamage.moudle.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.system.dao.MailDao;
import com.gobestsoft.common.modular.system.model.Mail;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 企业信息管理服务
 *
 * @author cxl
 * @date 2017-12-13 13:17
 */
@Service

public class MailService {

    @Resource
    MailDao mailDao;
    @Resource
    ManageProperties manageProperties;

    /**
     * 条件查询（邮件检索，开始时间，结束时间）
     *
     * @author cxl
     * @Date 2017/12/12 20:17
     */
    public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page, Integer searchType,
                                                       String content, String startDate, String endDate) {
        List<Map<String, Object>> mailList = new ArrayList<Map<String, Object>>();
        //TODO
//		if (searchType == 1) {
//			mailList = this.mailDao.searchForInbox(page,content, startDate, endDate,ShiroKit.getUser().getId());
//		} else if (searchType == 2) {
//			mailList = this.mailDao.searchForSendbox(page,content, startDate, endDate,ShiroKit.getUser().getId());
//		} else if (searchType == 3) {
//			mailList = this.mailDao.searchForDeletebox(page,content, startDate, endDate,ShiroKit.getUser().getId());
//		} else if (searchType == 4) {
//			mailList = this.mailDao.searchForDraftbox(page,content, startDate, endDate,ShiroKit.getUser().getId());
//		}
        return mailList;

    }

    /**
     * 条件查询（企业ID）
     *
     * @author cxl
     * @Date 2017/12/13 15:34
     */
    public Mail getDetail(String mailId, Integer searchType) {
        Mail mail = new Mail();
        //TODO
//        if (searchType == 1) {
//            mail = this.mailDao.getDetail(mailId, ShiroKit.getUser().getId());
//            mail.setAcceptName(ShiroKit.getUser().getName() + "(" + ShiroKit.getUser().getAccount() + ")");
//        } else if (searchType == 2) {
//            mail = this.mailDao.getDetail(mailId, null);
//            mail.setAcceptName(mail.getRecId());
//        } else if (searchType == 3) {
//            mail = this.mailDao.getDetail(mailId, null);
//            mail.setAcceptName(ShiroKit.getUser().getName() + "(" + ShiroKit.getUser().getAccount() + ")");
//        } else if (searchType == 4) {
//            mail = this.mailDao.getDetailDraftbox(mailId);
//        }
        return mail;
    }

    /**
     * 增加邮件信息
     *
     * @author cxl
     * @Date 2017/12/13 14:26
     */
    @Transactional
    public void addMail(Mail mail) {
        String systemDate = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        mail.setCreatTime(systemDate);
        String mailId = systemDate + UUIDUtil.getNumber(3);
        String userId[] = mail.getRecId().split(",");
        String recName = StringUtils.EMPTY;
        mail.setMailId(mailId);
        mail.setStatus("0");
        for (int i = 0; i < userId.length; i++) {
            String userName = mailDao.getUserName(userId[i]);
            if (i == 0) {
                recName = userName;
            } else {
                recName = recName + "," + userName;
            }
            mail.setAcceptId(userId[i]);
            //更新mail表
            mailDao.addMail(mail);
        }
        //更新mail_detail表
        //TODO
//        mail.setSendId(ShiroKit.getUser().getId());
        mail.setRecId(recName);
        mail.setIsDelect(0);
        mail.setIsDraft(0);
        mailDao.addMailDetail(mail);
    }

    /**
     * 保存邮件信息到草稿箱
     *
     * @author cxl
     * @Date 2017/12/19 09:26
     */
    @Transactional
    public void saveMail(Mail mail) {
        String systemDate = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        mail.setCreatTime(systemDate);
        if (ToolUtil.isNotEmpty(mail.getMailId())) {
            mailDao.editMailDetail(mail);
        } else {
            String mailId = systemDate + UUIDUtil.getNumber(3);
            mail.setMailId(mailId);
            String recName = StringUtils.EMPTY;
            //更新mail_detail表
            //TODO
//            mail.setSendId(ShiroKit.getUser().getId());
            mail.setRecId(recName);
            mail.setIsDelect(0);
            mail.setIsDraft(1);
            mailDao.addMailDetail(mail);
        }
    }

    /**
     * 修改邮件信息
     *
     * @author cxl
     * @Date 2017/12/19 11:05
     */
    @Transactional
    public void sendDraftMail(Mail mail) {
        String systemDate = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        mail.setCreatTime(systemDate);
        String userId[] = mail.getRecId().split(",");
        String recName = StringUtils.EMPTY;
        mail.setMailId(mail.getMailId());
        mail.setStatus("0");
        for (int i = 0; i < userId.length; i++) {
            String userName = mailDao.getUserName(userId[i]);
            if (i == 0) {
                recName = userName;
            } else {
                recName = recName + "," + userName;
            }
            mail.setAcceptId(userId[i]);
            //新增mail表
            mailDao.addMail(mail);
        }
        //更新mail_detail表
        mail.setRecId(recName);
        mail.setIsDraft(0);
        mailDao.updateMailDetail(mail);
    }

}
