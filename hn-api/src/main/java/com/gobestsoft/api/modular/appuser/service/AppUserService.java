package com.gobestsoft.api.modular.appuser.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.api.modular.appuser.model.AppUserDto;
import com.gobestsoft.api.modular.appuser.model.AppUserMember;
import com.gobestsoft.api.modular.appuser.model.MobileCodeDto;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.basic.FileUpload;
import com.gobestsoft.api.modular.basic.RestBasic;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.appuser.model.UserIntegralEntity;
import com.gobestsoft.common.modular.appuser.model.VerificationMobile;
import com.gobestsoft.common.modular.cms.dao.CommentDao;
import com.gobestsoft.common.modular.dao.mapper.*;
import com.gobestsoft.common.modular.dao.model.AppIntegralPojo;
import com.gobestsoft.common.modular.dao.model.AppIntegralTaskPojo;
import com.gobestsoft.common.modular.dao.model.AppMessagePojo;
import com.gobestsoft.common.modular.dao.model.AppMessageRelationPojo;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberApplyMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.DeptMember;
import com.gobestsoft.common.modular.dept.model.DeptMemberInfoEntity;
import com.gobestsoft.common.modular.mst.dao.MstMemberDao;
import com.gobestsoft.common.modular.mst.dao.MstOrganizationDao;
import com.gobestsoft.common.modular.mst.model.MstOrganization;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.core.moblie.SMSUtil;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.util.*;
import com.gobestsoft.rabbitmq.RabbitSender;
import com.gobestsoft.rabbitmq.processor.ExcludeLoginProcessor;
import com.gobestsoft.rabbitmq.processor.MessageProcessor;
import com.gobestsoft.rabbitmq.processor.PointProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author gutongwei
 * <p>
 * 2017年11月27日
 */
@Service
public class AppUserService extends RestBasic {

    /**
     * 短信过期时间30分钟
     */
    private final long MobileCodeAuthTime = 60 * 30;

    @Autowired
    private AppUserDao appUserDao;

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private MstMemberDao mstMemberDao;//获取会员信息dao

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Autowired
    private AppMessageRelationMapper appMessageRelationMapper;

    @Autowired
    private CmsArticleMapper cmsArticleMapper;

    @Autowired
    private AppIntegralMapper appIntegralMapper;

    @Autowired
    private MstOrganizationDao mstOrganizationDao;

    @Autowired
    private AppIntegralTaskMapper taskMapper;


    @Autowired
    private DeptMemberMapper deptMemberMapper;

    @Autowired
    private DeptMemberApplyMapper deptMemberApplyMapper;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    /**
     * 获取用户的token值的名称
     *
     * @param auId
     * @returne
     */
    private String getUserTokenName(String auId) {
        return auId + "_TOKEN";
    }

    /**
     * 获取token的值的名称
     *
     * @param token
     * @return
     */
    private String getTokenName(String token) {
        return "TOKEN_" + token;
    }

    /**
     * 用户是否有新的积分值redisName
     *
     * @param auId
     * @return
     */
    private String getHasNewIntegralName(String auId) {
        return auId + "_HAS_NEW_INTEGRAL";
    }


    /**
     * App用户注册
     *
     * @param account
     * @param password
     * @param deviceInfo
     * @param platform
     * @return
     */
    @Transactional
    public AppUserDto register(String account, String password, String deviceInfo, Integer platform) {
        String generatePwd = MD5Util.encrypt(password + apiProperties.getSalt());
        String auId = UUIDUtil.getUUID();
        appUserDao.register(auId, account, "会员_" + account.substring(account.length() - 4, account.length()), generatePwd, DateUtil.getAllTime());
        AppUserEntity appUser = getAppUserByMobile(account);
        AppUserDto result = login(appUser, null, deviceInfo, platform);
        //'11', '账号注册' 添加积分
        completeTaskGiveIntegral(result, 8);
        appUserDao.deleteVerificationByMobile(account);
        return result;
    }

    /**
     * 发送手机验证码
     *
     * @param mobile
     */
    public void sendMobileCode(String mobile) {
        appUserDao.deleteVerificationByMobile(mobile);
        String code = UUIDUtil.getNumber(6);
        appUserDao.addVerification(mobile, code, DateUtil.getAllTime());
        new Thread(() -> SMSUtil.sendAuthCode(mobile, code)).start();
        addMobileCodeFrequency(mobile);
    }


    /**
     * 增加短信发送次数
     *
     * @param mobile
     */
    public void addMobileCodeFrequency(String mobile) {
        String cacheName = MobileCodeDto.cacheName + mobile;
        MobileCodeDto dto = null;
        if (redisUtils.exists(cacheName)) {
            dto = (MobileCodeDto) redisUtils.get(cacheName);
        }

        if (dto == null) {
            dto = new MobileCodeDto();
            dto.setFrequency(1);
        } else {
            dto.setFrequency(dto.getFrequency() + 1);
        }
        dto.setSendTime(Calendar.getInstance().getTime());

        redisUtils.set(cacheName, dto);
    }


    /**
     * 发送短信是否超过次数
     *
     * @param mobile
     * @return
     */
    public boolean isMoreThanTimes(String mobile) {
        String cacheName = MobileCodeDto.cacheName + mobile;
        if (redisUtils.exists(cacheName)) {
            MobileCodeDto dto = (MobileCodeDto) redisUtils.get(cacheName);
            if (dto != null && dto.getFrequency() >= apiProperties.getMobileMostTime()) {
                long nowTime = Calendar.getInstance().getTimeInMillis();
                long lastSentTime = dto.getSendTime().getTime();
                long expiredTime = 1000 * 60 * 60;
                if (nowTime - lastSentTime > expiredTime) {
                    redisUtils.remove(cacheName);
                } else if (nowTime - lastSentTime < expiredTime && dto.getFrequency() >= 5) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 使用帐号名密码登录
     *
     * @param account    账号/手机号
     * @param password   密码
     * @param registerId 极光ID
     * @param deviceInfo 设备信息
     * @param platform   设备类型：0：安卓。1:iOS;
     * @return
     */
    public AppUserDto login(String account, String password, String registerId, String deviceInfo, Integer platform) {
        AppUserEntity appUser = getAppUserByMobile(account);
        if (appUser != null) {
            String userPassword = MD5Util.encrypt(password + apiProperties.getSalt());
            if (userPassword.equals(appUser.getPassword())) {
                return login(appUser, registerId, deviceInfo, platform);
            }
        }
        return null;
    }

    /**
     * 使用手机号验证码登录
     *
     * @param mobile     手机号
     * @param code       数据验证码
     * @param registerId 极光ID
     * @param deviceInfo 设备信息
     * @param platform   设备类型：0：安卓。1:iOS;
     * @return
     */
    public AppUserDto loginByMobile(String mobile, String code, String registerId, String deviceInfo, Integer platform) {
        AppUserEntity appUser = getAppUserByMobile(mobile);
        if (appUser != null) {
            if (authMobileCode(mobile, code)) {
                appUserDao.deleteVerificationByMobile(mobile);
                return login(appUser, registerId, deviceInfo, platform);
            }
        }
        return null;
    }

    /**
     * 返回用户信息，并且存至redis
     *
     * @param appUser    用户信息
     * @param registerId 极光ID
     * @param deviceInfo 设备信息
     * @param platform   设备类型
     * @return
     */
    private AppUserDto login(AppUserEntity appUser, String registerId, String deviceInfo, Integer platform) {
        if (StringUtils.isEmpty(registerId)) {
            registerId = "";
        }
        if (StringUtils.isNotEmpty(appUser.getRegisterId()) && !appUser.getRegisterId().equals(registerId)) {
            loginExcludePush(appUser.getRegisterId());
        }
        String token = UUIDUtil.getUUID() + UUIDUtil.getNumber(6);
        if (StringUtils.isNotEmpty(registerId)) {
            appUserDao.cleanRegisterId(registerId);
        }
        appUserDao.bindDeviceInfo(appUser.getAuId(), registerId, deviceInfo, platform);
        AppUserDto result = getAppUserDto(appUser, token);
        // 删除用户之前的token并且替换最新
        if (redisUtils.exists(getUserTokenName(appUser.getAuId()))) {
            String oldToken = (String) redisUtils.get(getUserTokenName(appUser.getAuId()));
            redisUtils.remove(getTokenName(oldToken));
        }
        redisUtils.set(getTokenName(token), result);
        redisUtils.set(getUserTokenName(appUser.getAuId()), token);
        return result;
    }

    /**
     * 根据用户ID，修改用户缓存信息
     *
     * @param auId
     * @return
     */
    public AppUserDto setTokenInfoByAuId(String auId) {
        AppUserEntity appUser = appUserDao.getAppUserByAuid(auId);
        if (appUser != null && redisUtils.exists(getUserTokenName(auId))) {
            String token = (String) redisUtils.get(getUserTokenName(auId));
            if (StringUtils.isNotEmpty(token)) {
                AppUserDto result = getAppUserDto(appUser, token);
                Integer point = appUserDao.selectTotalPointByUser(auId);
                if (point != null) {
                    result.setPoint(point);
                }

                if (result != null) {
                    redisUtils.set(getTokenName(token), result);
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    public AppUserDto getUserInfoByToken(String token) {
        String tokenName = getTokenName(token);
        if (!redisUtils.exists(tokenName)) {
            return null;
        }
        try {
            AppUserDto dto = (AppUserDto) redisUtils.get(tokenName);

            Integer point = appUserDao.selectTotalPointByUser(dto.getAuid());
            if (point != null) {
                dto.setPoint(point);
            }
            Integer integral = appUserDao.getUserIntegral(dto.getAuid());
            dto.setIntegral(integral);

            redisUtils.set(getTokenName(token), dto);

            return dto;
        } catch (Exception ex) {
            return null;
        }

    }


    /**
     * 根据卡号获取用户信息
     *
     * @param stationCard
     * @return
     */
    public AppUserDto getUserInfoByCardNo(String stationCard) {
        Map<String, Object> map = new HashMap<>();

        map.put("station_card", stationCard);

        List<DeptMember> deptMembers = deptMemberMapper.selectByMap(map);

        if (CollectionUtils.isEmpty(deptMembers)) {
            throw new RuntimeException("用户不存在");
        }
        if (deptMembers.size() > 1) {
            throw new RuntimeException("会员卡号异常，请联系管理员");
        }

        DeptMember deptMember = deptMembers.get(0);

        String auid = appUserDao.selectAuid(deptMember.getId() + "");

        if (auid == null) {
            throw new RuntimeException("用户不存在");
        }

        AppUserEntity appUser = appUserDao.getAppUserByAuid(auid + "");

        appUser.setMember_id(deptMember.getId());

        AppUserDto appUserDto = getAppUserDto(appUser, null);
        if (appUserDto == null || appUserDto.getMember_info() == null) {
            throw new RuntimeException("用户信息表中无法找到匹配的项");//personInfo中的数据被删除
        }
        return getAppUserDto(appUser, null);

    }


    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    public boolean validUserInfoByToken(String token) {
        if (getUserInfoByToken(token) != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户Entity和Token返回Dto
     *
     * @param appUser
     * @param token
     * @return
     */
    private AppUserDto getAppUserDto(AppUserEntity appUser, String token) {
        if (appUser == null) return null;
        AppUserMember memberInfo = getAppUserMember(appUser.getMember_id());

        String city_name = null;
        String city_code = null;
        boolean isApplyMember = false;
        if (memberInfo == null) {
            isApplyMember = isApplyMember(appUser.getAuId());

        } else {
            Map<String, Object> map = deptMemberMapper.selectOrganizationByDeptId(memberInfo.getDept_id());
            if (map != null) {
                city_code = map.get("unit_district") + "";
                city_name = map.get("unit_district_name") + "";
                memberInfo.setWork_unit(map.get("unit_name") + "");
            }

            String isfarmer = personInfoMapper.selectByMemId(memberInfo.getMember_id());
            if (ToolUtil.isEmpty(isfarmer)) {
                isfarmer = "0";
            }
            memberInfo.setIs_farmer(isfarmer);
        }
        AppUserDto result = new AppUserDto(appUser.getAuId(), token, WebSiteUtil.getBrowseUrl(appUser.getAvatar()),
                EmojiUtil.parseToUnicode(appUser.getNickname()),
                appUser.getSex(), appUser.getAccount(), appUserDao.getUserIntegral(appUser.getAuId()),
                appUserDao.getUserExperience(appUser.getAuId()),
                appUser.getReal_name(), appUser.getRegisterId(),
                memberInfo, isApplyMember);
        result.setCity_code(city_code);
        result.setCity_name(city_name);
        if (memberInfo != null) {
            result.setSex(memberInfo.getSex());
            result.setReal_name(memberInfo.getName());
        }


        return result;
    }


    /**
     * 根据用户手机获取用户信息
     *
     * @return
     */
    public AppUserEntity getAppUserByMobile(String mobile) {
        return appUserDao.getAppUserByMobile(mobile);
    }

    /**
     * 忘记密码-修改密码
     *
     * @param password
     * @param mobile
     * @param code
     * @return
     */
    @Transactional
    public void modifyPassword(String password, String mobile, String code) {
        String generatePwd = MD5Util.encrypt(password + apiProperties.getSalt());
        appUserDao.modifyPassword(generatePwd, mobile);
        deleteVerificationByMobile(mobile);
    }

    /**
     * 修改用户信息
     *
     * @param fileUpload
     * @param nickname
     * @param realName
     * @param sex
     * @return
     */
    @Transactional
    public Map<String, String> setUserInfo(AppUserDto appUserDto, FileUpload fileUpload, String nickname,
                                           String realName, Short sex) {
        appUserDao.setUserInfo(fileUpload == null ? "" : fileUpload.getSave_path(),
                EmojiUtil.parseToAliases(nickname),
                realName, sex, appUserDto.getAuid());
        Map<String, String> result = new HashMap<>();
        if (!StringUtils.isEmpty(realName) && appUserDto.getMember_info() == null) {
            result.put("real_name", realName);
        }
        if (!StringUtils.isEmpty(nickname)) {
            result.put("nickname", nickname);
        }
        if (sex != null && appUserDto.getMember_info() == null) {
            result.put("sex", String.valueOf(sex));
        }
        if (fileUpload != null && !StringUtils.isEmpty(fileUpload.getAccess_url())) {
            result.put("avatar", fileUpload.getAccess_url());
            //'3', '上传头像' 添加积分
            completeTaskGiveIntegral(appUserDto, 3);
        }
        setTokenInfoByAuId(appUserDto.getAuid());
        return result;
    }

    /**
     * 删除app_user
     *
     * @param auid
     */
    public void deleteByAuid(String auid) {
        appUserDao.deleteByAuid(auid);
    }

    /**
     * 获取验证信息
     *
     * @param mobile
     * @return
     */
    private VerificationMobile getVerificationByMobile(String mobile) {
        return appUserDao.getVerificationByMobile(mobile);
    }

    /**
     * 验证码是否有效
     *
     * @param mobile
     * @param code
     * @return
     */
    public boolean authMobileCode(String mobile, String code) {
        VerificationMobile verificationMobile = getVerificationByMobile(mobile);
        if (verificationMobile == null) {
            return false;
        }
        if (code.equals(verificationMobile.getCode())) {
            long createTime = DateUtil.parseDateTime(verificationMobile.getCreate_time()).getTime();
            long nowTime = System.currentTimeMillis() / 1000;
            if (nowTime < (createTime + MobileCodeAuthTime)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除手机号的验证码
     *
     * @param mobile
     */
    public void deleteVerificationByMobile(String mobile) {
        appUserDao.deleteVerificationByMobile(mobile);
    }

    /**
     * 获取用户ID
     *
     * @param auId
     * @return
     */
    public int getUserIntegral(String auId) {
        return appUserDao.getUserIntegral(auId);
    }

    /**
     * 用户绑定极光推送注册号
     *
     * @param auId
     * @param registerId 极光ID
     * @param platform   设备类型：0:安卓。1：IOS。
     */
    public void bindAppUserRegisterId(String auId, String registerId, Integer platform) {
        appUserDao.bindDeviceInfo(auId, registerId, null, platform);
    }


    /**
     * 获取用户是否正在申请会员中
     *
     * @param auid
     * @return
     */
    private boolean isApplyMember(String auid) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -30);
        Wrapper wrapper = new EntityWrapper().eq("auid", auid).eq("apply_status", "-1").gt("create_time", DateUtil.format(now.getTime(), "yyyyMMddHHmmsss"));
        if (deptMemberApplyMapper.selectCount(wrapper) <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 用户会员信息
     *
     * @param member_id
     * @return
     */
    public AppUserMember getAppUserMember(Integer member_id) {
        if (member_id == null) {return null;}

        AppUserMember member = null;
        DeptMemberInfoEntity entity = deptMemberMapper.getMemberInfo(member_id);
        if (entity != null) {
            member = new AppUserMember();
            member.setMember_id(entity.getMember_id());
            member.setDept_id(entity.getDept_id());
            member.setDept_name(entity.getDept_name());
            member.setMember_card(entity.getMember_card());
            member.setStation_card(entity.getStation_card());
            member.setSex(entity.getSex());
            member.setName(entity.getName());
            member.setBirthday(entity.getBirthday());
            member.setNation(entity.getNation());
            member.setWork_situation(entity.getWork_situation());
            member.setCertificate_type(entity.getCertificate_type());
            member.setCertificate_num(entity.getCertificate_num());
            member.setEducation(entity.getEducation());
            member.setTechnology_level(entity.getTechnology_level());
            member.setMobile(entity.getMobile());
            member.setHousehold(entity.getHousehold());
            member.setDomicile(entity.getDomicile());
            member.setPolitical_status(entity.getPolitical_status());
            member.setWork_unit(entity.getWork_unit());
            member.setNative_place(entity.getNative_place());
            member.setHomeplace(entity.getHomeplace());
        }
        return member;
    }

    /**
     * 根据工会id,查询出对应的pids
     *
     * @param unionId
     * @return
     */
    private String getPidsByUnionId(Integer unionId) {
        if (ToolUtil.isEmpty(unionId)) {
            return "";
        }
        MstOrganization mstOrganization = mstOrganizationDao.selectById(unionId);
        if (!(ToolUtil.isNotEmpty(mstOrganization) && ToolUtil.isNotEmpty(mstOrganization.getDeptId()))) {
            return "";
        }
        Map<String, Object> dept = deptDao.deptByPids(mstOrganization.getDeptId());
        if (ToolUtil.isEmpty(dept)) {
            return "";
        }
        return dept.get("pids").toString();
    }

    /**
     * 判断app用户是否存在
     *
     * @return
     */
    public boolean authAPPUserExist(String account) {
        AppUserEntity user = getAppUserByMobile(account);
        if (user != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据用户姓名、身份证、auid参数验证会员是否已经认证会员
     *
     * @param name
     * @param cardNo
     * @param auid
     * @return
     */
    public Map<String, Object> checkUserMessage(String name, String cardNo, String auid) {
        Map<String, Object> map = new HashMap<String, Object>();
        AppUserEntity appUser = appUserDao.getAppUserByAuid(auid);
        if (ToolUtil.isEmpty(appUser)) {
            map.put("status", 0);//当前用户不存在
            return map;
        }
        if (ToolUtil.isNotEmpty(appUser.getMember_id())) {
            map.put("status", 1);//已经认证会员
            return map;
        }
        List<DeptMember> mstMember = mstMemberDao.selectByIdcardNumber(cardNo, name);
        if (ToolUtil.isEmpty(mstMember)) {
            map.put("status", 2);//信息输入错误
            return map;
        }
        long appUserCount = appUserDao.selectAuidByMemberId(mstMember.get(0).getId());
        if (appUserCount > 0) {
            map.put("status", 3);//姓名、身份证号已经被认证
            return map;
        }
        map.put("appUser", appUser);
        map.put("mstMember", mstMember.get(0));
        return map;
    }

    /**
     * 认证会员
     */
    public AppUserDto certifiedMember(Object appUserObj, Object deptMemberObj, String token) {
        AppUserEntity appUser = new AppUserEntity();
        DeptMember deptMember = new DeptMember();
        if (appUserObj instanceof AppUserEntity) {
            appUser = (AppUserEntity) appUserObj;
        }
        if (deptMemberObj instanceof DeptMember) {
            deptMember = (DeptMember) deptMemberObj;
        }
        //appUser.setReal_name(deptMember.getName());
        //appUser.setSex(Integer.valueOf(deptMember.getSex()));
        appUser.setMember_id(deptMember.getId());
        AppUserMember appUserMember = getAppUserMember(appUser.getMember_id());
        appUser.setReal_name(appUserMember.getName());
        appUserDao.updateByAuid(appUser);
        return bindAppUserDtoByAppUserAndMember(appUser, token);
    }

    /**
     * 根据用户信息和会员信息，绑定AppUserDto对象
     *
     * @param appUser
     * @param token
     * @return
     */
    private AppUserDto bindAppUserDtoByAppUserAndMember(AppUserEntity appUser, String token) {
        AppUserDto result = setTokenInfoByAuId(appUser.getAuId());
        //'2', '身份信息绑定' 添加积分
        completeTaskGiveIntegral(result, 2);
        return result;
    }


    /**
     * 获取消息分类列表
     *
     * @param auId
     * @return
     */
    public Object getUserMessageCategory(String auId) {
        RedisCacheModel redisCacheModel = cacheFactory.getCacheModel(CacheConstant.getMessageCache(auId));
        if (redisCacheModel != null) {
            return redisCacheModel.getData();
        }

        String createTime = appUserDao.getAccountCreateTime(auId);
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtil.getAllTime();
        }
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> messageCategory = appMessageMapper.getUserMessageCategory(auId, createTime);

        for (int i = 0; i < messageCategory.size(); i++) {
            Map<String, Object> m = messageCategory.get(i);
            Map<String, Object> r = new HashMap<>();
            r.put("category_id", m.get("category_id"));
            r.put("unRead_count", m.get("unRead_count"));
            r.put("name", m.get("name"));
            r.put("icon", WebSiteUtil.getBrowseUrl(String.valueOf(m.get("icon"))));
            AppMessagePojo lastUnreadMessage = appMessageMapper.getLastMessage(auId, createTime, (Integer) m.get("category_id"));
            if (lastUnreadMessage == null) {
                continue;
            }
            r.put("create_time", DateUtil.parseAndFormat(lastUnreadMessage.getCreateTime(), "yyyyMMddHHmmss", "MM月dd日 HH:mm"));
            r.put("unRead_title", lastUnreadMessage.getTitle());
            result.add(r);
        }
        cacheFactory.cacheModel(CacheConstant.getMessageCache(auId), result, 5);
        return result;
    }


    /**
     * 获取消息列表
     * <p>
     * create by gutongwei
     * 2018/6/12
     *
     * @param auid      用户id
     * @param rowBounds
     * @return
     */
    public List<Map<String, Object>> getMessages(Integer categoryId, String auid, BasicRowBounds rowBounds) {
        String createTime = appUserDao.getAccountCreateTime(auid);
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtil.getAllTime();
        }
        List<Map<String, Object>> result = appMessageMapper.getMessagesByAuid(categoryId, auid, createTime, rowBounds);
        result.forEach(r -> {
            if (r.containsKey("create_time") && ToolUtil.isNotEmpty(r.get("create_time"))) {
                r.put("create_time", DateUtil.parseAndFormat(String.valueOf(r.get("create_time")),
                        "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
            }
            if (r.containsKey("cover") && r.get("cover") != null) {
                r.put("cover", WebSiteUtil.getBrowseUrl(String.valueOf(r.get("cover"))));
            }
            if (r.containsKey("operation") && StringUtils.isNotEmpty(String.valueOf(r.get("operation")))) {
                JSONObject operation = JSON.parseObject(String.valueOf(r.get("operation")));
                Integer mode = Integer.valueOf(r.get("operation_mode").toString());

                if (categoryId == 2 && mode == 7) {
                    //新闻资讯消息
                    Map<String, Object> reply = commentDao.selectApplyById(operation.get("id") + "");

                    if (reply != null) {

                        Map<String, Object> comment = commentDao.selectOneById(reply.get("comment_id") + "", auid);
                        operation.put("thumbs_up_total", comment.get("thumbs_up_total") + "");
                        operation.put("avatar", reply.get("avatar") == null ? "" : WebSiteUtil.getBrowseUrl(reply.get("avatar") + ""));
                        operation.put("create_time", DateUtil.parseAndFormat(comment.get("create_time") + "", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
                        operation.put("content", comment.get("content") + "");
                        operation.put("is_give_thumbs_up", comment.get("is_give_thumbsUp") + "");
                        operation.put("reply_content", reply.get("content") + "");
                        operation.put("nickname", reply.get("nickname") + "");
                        operation.put("id", reply.get("comment_id") + "");
                    }

                }
                r.put("operation", operation);
            }
        });
        return result;
    }

    /**
     * 消息已读
     * create by gutongwei
     * 2018/6/12
     *
     * @param auid      用户id
     * @param messageId 消息ID
     */
    public void readMessage(String auid, int messageId) {
        redisUtils.remove(CacheConstant.getMessageCache(auid));
        AppMessageRelationPojo pojo = getMessageRelation(auid, messageId);
        if (pojo != null) {
            if (pojo.getIsOpen() == 0) {
                pojo.setOpenTime(DateUtil.getAllTime());
                pojo.setIsOpen(1);
                pojo.setDelFlg(0);
                appMessageRelationMapper.updateById(pojo);
            }
        } else {
            pojo = new AppMessageRelationPojo();
            pojo.setIsOpen(1);
            pojo.setOpenTime(DateUtil.getAllTime());
            pojo.setAuid(auid);
            pojo.setDelFlg(0);
            pojo.setMessageId(messageId);
            appMessageRelationMapper.insert(pojo);
        }
    }

    /**
     * 获取消息关联表
     *
     * @param auid      用户ID
     * @param messageId 消息ID
     * @return
     */
    private AppMessageRelationPojo getMessageRelation(String auid, int messageId) {
        Wrapper wrapper = new EntityWrapper().eq("message_id", messageId).eq("auid", auid).eq("del_flg", 0);
        List<AppMessageRelationPojo> pojos = appMessageRelationMapper.selectList(wrapper);
        if (pojos != null && pojos.size() > 0) {
            return pojos.get(0);
        }
        return null;
    }

    /**
     * 删除消息
     * create by gutongwei
     * 2018/6/12
     *
     * @param auid      用户id
     * @param messageId 消息ID
     */
    public void delMessage(String auid, int messageId) {
        redisUtils.remove(CacheConstant.getMessageCache(auid));
        AppMessageRelationPojo pojo = getMessageRelation(auid, messageId);
        if (pojo != null) {
            pojo.setOpenTime(DateUtil.getAllTime());
            pojo.setIsOpen(1);
            pojo.setDelFlg(1);
            appMessageRelationMapper.updateById(pojo);
        } else {
            pojo = new AppMessageRelationPojo();
            pojo.setIsOpen(1);
            pojo.setOpenTime(DateUtil.getAllTime());
            pojo.setAuid(auid);
            pojo.setDelFlg(1);
            pojo.setMessageId(messageId);
            appMessageRelationMapper.insert(pojo);
        }
    }


    /**
     * 获取用户任务
     * create by gutongwei
     * 2018/6/21
     *
     * @param auid 用户id
     * @return
     */
    public List<Map<String, Object>> integralTask(String auid) {
        List<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> noviceTask = new HashMap<>();
        noviceTask.put("type", 0);
        noviceTask.put("tasks", appIntegralMapper.getUserIntegralTasks(auid, 0, null, null));
        result.add(noviceTask);

        Map<String, Object> allDaysTask = new HashMap<>();
        allDaysTask.put("type", 1);
        allDaysTask.put("tasks", appIntegralMapper.getUserIntegralTasks(auid, 1, DateUtil.getDays() + "000000",
                DateUtil.getDays() + "235959"));
        result.add(allDaysTask);
        redisUtils.remove(getHasNewIntegralName(auid));
        return result;
    }

    /**
     * 完成任务给予积分
     * create by gutongwei
     * 2018/6/21
     *
     * @param appUserDto
     * @param taskId
     */
    @Transactional
    public void completeTaskGiveIntegral(AppUserDto appUserDto, int taskId) {
        //签到任务
        String today = DateUtil.getDays();
        AppIntegralTaskPojo task = taskMapper.selectById(taskId);
        if (task != null) {
            //终生任务 任务完成次数大于等于频率
            if (task.getType() == 0 && appIntegralMapper.taskIsComplete(taskId, appUserDto.getAuid(), null, null) >= task.getFrequency()) {
                return;
            }
            //每日任务 任务完成次数大于等于频率
            if (task.getType() == 1 && appIntegralMapper.taskIsComplete(taskId, appUserDto.getAuid(), today + "000000", today + "235959") >= task.getFrequency()) {
                return;
            }
            //生日奖励
            if (taskId == 1 && appUserDto.getMember_info() != null && StringUtils.isNotEmpty(appUserDto.getMember_info().getCertificate_num())) {
                Calendar now = Calendar.getInstance();
                Calendar birthday = Calendar.getInstance();
                birthday.setTime(DateUtil.parse(appUserDto.getMember_info().getCertificate_num().substring(6, 14), "yyyyMMdd"));
                birthday.set(Calendar.YEAR, now.get(Calendar.YEAR));
                String birthdayStr = DateUtil.format(birthday.getTime(), "yyyyMMdd");
                if (birthdayStr.equals(today)) {
                    int birthdayTaskId = 9;
                    AppIntegralTaskPojo birthdayTask = taskMapper.selectById(birthdayTaskId);
                    if (appIntegralMapper.taskIsComplete(taskId, appUserDto.getAuid(), birthdayStr + "000000", birthdayStr + "235959") <= birthdayTask.getFrequency()) {
//                        appIntegralMapper.integralHouston(appUserDto.getAuid(), DateUtil.getAllTime(), birthdayTaskId);
//                        appIntegralMapper.taskComplete(appUserDto.getAuid(), birthdayTaskId, DateUtil.getAllTime());
                        PointProcessor processor = new PointProcessor(appUserDto.getAuid(), birthdayTaskId);
                        rabbitSender.sendMessage(processor);

                    }
                }
            }
            //添加用户积分
//            appIntegralMapper.integralHouston(appUserDto.getAuid(), DateUtil.getAllTime(), taskId);
//            appIntegralMapper.taskComplete(appUserDto.getAuid(), taskId, DateUtil.getAllTime());
//            redisUtils.set(getHasNewIntegralName(appUserDto.getAuid()), 1);
            PointProcessor processor = new PointProcessor(appUserDto.getAuid(), taskId);
            rabbitSender.sendMessage(processor);
        }
    }

    /**
     * 根据用户auid更新积分
     *
     * @param auid
     * @param taskId
     */
    public void completeTaskGiveIntegral(String auid, Integer taskId) {
        String oldToken = (String) redisUtils.get(getUserTokenName(auid));

        if (StringUtils.isNotEmpty(oldToken)) {
            AppUserDto appUserDto = getUserInfoByToken(oldToken);
            if (appUserDto == null) {
                return;
            }
            completeTaskGiveIntegral(appUserDto, taskId);
        }


    }

    private boolean isExists(Integer[] ids, Integer target) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * 积分消费
     * create by gutongwei
     * 2018/6/21
     *
     * @param value    消费值
     * @param targetId 目标id
     * @param type     消费类型
     * @param auid     用户ID
     */
    public void addInteger(int value, String targetId, int type, String auid) {
        AppIntegralPojo pojo = new AppIntegralPojo();
        pojo.setAuid(auid);
        pojo.setCreateTime(DateUtil.getAllTime());
        pojo.setTargetId(targetId);
        pojo.setValue(value);
        pojo.setType(type);
        appIntegralMapper.insert(pojo);
    }

    /**
     * 用户获取积分列表
     * create by gutongwei
     * 2018/6/21
     *
     * @param auId      用户id
     * @param type      积分类型【0：任务进账】【1：兑换积分商品】【2：参加活动取得】
     * @param taskId    积分任务ID
     * @param rowBounds
     * @return
     */
    public List<UserIntegralEntity> getUserIntegralList(String auId, Integer type, Integer taskId, RowBounds rowBounds) {
        if (type == null && taskId != null) {
            type = 0;
        }
        redisUtils.set(getHasNewIntegralName(auId), 0);
        return appIntegralMapper.getUserIntegralList(auId, type, taskId, rowBounds);
    }

    /**
     * 查看用户是否含有新消息
     *
     * @param auid
     * @return
     */
    public int newMessageCount(String auid) {
        String createTime = appUserDao.getAccountCreateTime(auid);
        if (StringUtils.isEmpty(createTime)) {
            createTime = DateUtil.getAllTime();
        }
        return appMessageMapper.getNotReadMessagesByAuid(auid, createTime);
    }

    /**
     * 是否含有新积分入账
     * create by gutongwei
     * 2018/6/22
     *
     * @param auid
     * @return
     */
    public int hasNewIntegral(String auid) {
        if (redisUtils.exists(getHasNewIntegralName(auid)) && 1 == Integer.valueOf(redisUtils.get(getHasNewIntegralName(auid)).toString())) {
            redisUtils.remove(getHasNewIntegralName(auid));
            return 1;
        }
        return 0;
    }


    /**
     * 用户被挤下线发送推送消息
     *
     * @param registerId
     */
    private void loginExcludePush(String registerId) {
        if (StringUtils.isNotEmpty(registerId)) {
            ExcludeLoginProcessor processor = new ExcludeLoginProcessor();
            processor.setRegistrationId(registerId);
            rabbitSender.apiGiveAdminMessage(processor);
        }
    }

    /**
     * 发生消息
     * create by gutongwei
     * 2018/6/12
     *
     * @param auid     用户id
     * @param category 消息类型
     * @param title    标题
     * @param content  消息内容
     * @param mode     用户操作：0：无操作。1：重新登录。2：打开资讯。3：跳转网页。
     * @param targetId 目标ID
     * @param jumpUrl  跳转地址
     * @param isPush   是否推送
     */
    public void sendMessageByAuid(String auid, int category, String title, String content, int mode, String targetId, String jumpUrl, boolean isPush) {
        MessageProcessor processor = new MessageProcessor();
        processor.setPush(isPush);
        processor.setMode(mode);
        processor.setCategory(category);
        processor.setTitle(title);
        processor.setAuids(new String[]{auid});
        processor.setContent(content);
        processor.setJumpUrl(jumpUrl);
//        if(category==2&&mode==7){
//            //新闻资讯消息
//            Map<String, Object> reply = commentDao.selectApplyById(targetId);
//
//            if(reply!=null){
//
//                targetId = reply.get("comment_id")+"";
//            }
//
//        }
        processor.setTargetId(targetId);
        rabbitSender.apiGiveAdminMessage(processor);
    }


    public void logout(AppUserDto appUserDto) {
        appUserDao.bindDeviceInfo(appUserDto.getAuid(), "", "", null);
    }

    /**
     * 保存留言信息
     *
     * @param map
     * @return
     */
    public void insertLeaveMessage(Map map) {
        map.put("id", UUIDUtil.getUUID());
        map.put("createTime", DateUtil.getAllTime());
        appUserDao.insertLeaveMessage(map);
    }

    /**
     * 更新用户指定消息类型的消息已读
     *
     * @param auid
     * @param cat_id
     */
    public void updateMessageReadByCat(String auid, Integer cat_id) {
        appUserDao.updateMessageReadByCat(auid, cat_id);
    }


    /**
     * 修改手机号
     *
     * @param mobile
     */
    public void updateMobile(String mobile, String oldMobile, String auid, String token) {

        appUserDao.updateMobile(mobile, oldMobile);
        appUserDao.updateAccount(mobile, auid);

    }

    /**
     * 确认能否注销用户
     *
     * @return
     */
    public BaseResult validUserState(String uid) {
        //困难帮扶
        int c1 = appUserDao.selectCountStraitened(uid);

        int c2 = appUserDao.selectCountSupport(uid);

        int c3 = appUserDao.selectCountMemberApply(uid);

        int c4 = appUserDao.selectCountMemberTransferApply(uid);

        boolean f1 = (c1 > 0 || c2 > 0 || c3 > 0 || c4 > 0);

        boolean f2 = false;//后期从接口判断是否有未完成的订单

        if (f1 && !f2) {
            return new BaseResult(201, "有未完成的法律援助或困难帮扶申请", null);
        }

        if (f2 && !f1) {
            return new BaseResult(202, "有未完的商城", null);
        }
        if (f1 == true && f2 == true) {

            return new BaseResult(203, "有未完成的申请/商城订单", null);
        }
        return new BaseResult(200, null, null);


    }

}
