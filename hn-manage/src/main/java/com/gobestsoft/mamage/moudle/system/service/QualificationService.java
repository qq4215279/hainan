package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.dao.model.QualificationPojo;
import com.gobestsoft.common.modular.system.dao.QualificationDao;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.config.properties.ManageProperties;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * 企业资质信息业务层
 * @author zhangdw
 * 2018年5月24日 上午11点15
 */
@Service
public class QualificationService{
	
	//企业资质审核所用的审核字典code:lib_approve_status;-1:待审核;0:审核不通过;1:审核通过
	private static final String[]  LIB_APPROVE_STATUS = {"-1","0","1"};

	@Resource
	QualificationDao qualificationDao;
	@Resource
    private UserMapper userMapper;
	@Resource
    private BlackboardService blackboardService;
	@Resource
	private ManageProperties manageProperties;

    /**
     * 根据当前登录用户uid,获取一条企业资质信息
     * @return
     */
	public QualificationPojo getQualificationByUid(String uid) {
		QualificationPojo qualification = qualificationDao.selectByUid(uid);
		if(ToolUtil.isEmpty(qualification)) {
			qualification = new QualificationPojo();
		}
		return qualification;
	}

	/**
	 * 上传图片
	 * @param picture
	 * @return
	 */
	public String upload(MultipartFile picture, String uid) {
		String pictureName = UUID.randomUUID().toString() + ".jpg";
		try {
			String fileSavePath = manageProperties.getFileUploadPath() + UploadConstants.AVATAR;
			File p = new File(fileSavePath);
			if (!p.exists()) {
				p.mkdirs();
			}
			picture.transferTo(new File(fileSavePath + pictureName));
			//更新当前登录人的头像路径
			updateUserAvatar(uid,pictureName);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new BusinessException(BizExceptionEnum.UPLOAD_ERROR);
		}
		return UploadConstants.AVATAR + pictureName;
	}

	/**
	 * 更新当前登录人的头像路径
	 * @param userId
	 * @param pictureName
	 */
	private void updateUserAvatar(String userId,String pictureName) {
		User user = userMapper.selectById(userId);
		user.setAvatar(UploadConstants.AVATAR + pictureName);
		userMapper.updateById(user);
	}
}
