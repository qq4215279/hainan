package com.gobestsoft.common.modular.appuser.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.appuser.model.VerificationMobile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * App用户模块
 *
 * @author gutongwei
 * <p>
 * 2017年11月25日
 */
@Repository
public interface AppUserDao {

    /**
     * App用户列表
     *
     * @param account
     * @param nickName
     */
    List<AppUserEntity> appuUserList(
            @Param("page") Page<AppUserEntity> page
//			@Param("auId") String auId
            , @Param("account") String account, @Param("nickname") String nickname,
            @Param("begDate") String begDate, @Param("endDate") String endDate, @Param("isMember") Integer isMember
    );


    /**
     * 根据auid 获取App用户
     *
     * @param auid
     */
    HashMap<String, Object> appUserByAuid(
            @Param("auid") String auid
    );


    /**
     * App用户注册
     *
     * @param auId
     * @param account
     * @param nickName
     * @param password
     */
    void register(@Param("auId") String auId, @Param("account") String account, @Param("nickName") String nickName,
                  @Param("password") String password, @Param("createTime") String createTime);

    /**
     * 根据用户手机获取用户信息
     *
     * @return
     */
    AppUserEntity getAppUserByMobile(@Param("mobile") String mobile);


    /**
     * 根据用户手机获取用户信息
     *
     * @return
     */
    AppUserEntity getAppUserByAuid(@Param("auid") String auid);


    /**
     * 修改用户密码
     *
     * @param password
     * @param mobile
     */
    void modifyPassword(@Param("password") String password, @Param("mobile") String mobile);

    /**
     * 修改用户头像
     *
     * @param avatar
     * @param auId
     */
    void setUserInfo(@Param("avatar") String avatar, @Param("nickname") String nickname,
                     @Param("realName") String realName, @Param("sex") Short sex, @Param("auId") String auId);

    /**
     * 插入验证码
     *
     * @param mobile
     * @param code
     * @param createTime
     */
    void addVerification(@Param("mobile") String mobile, @Param("code") String code,
                         @Param("createTime") String createTime);

    /**
     * 获取验证信息
     *
     * @param mobile
     * @return
     */
    VerificationMobile getVerificationByMobile(@Param("mobile") String mobile);

    /**
     * 删除手机号验证码表
     *
     * @param mobile
     */
    void deleteVerificationByMobile(@Param("mobile") String mobile);

    /**
     * 获取用户当前积分
     *
     * @param auId
     * @return
     */
    Integer getUserIntegral(@Param("auId") String auId);

    /**
     * 获取用户经验
     *
     * @param auId
     * @return
     */
    Integer getUserExperience(@Param("auId") String auId);


    /**
     * 清除极光ID
     *
     * @param registerId
     */
    void cleanRegisterId(@Param("registerId") String registerId);

    /**
     * 用户绑定设备信息
     *
     * @param auId       用户ID
     * @param registerId 用户极光账号
     * @param deviceInfo 用户设备信息
     * @param platform   用户这边类型：0：安卓。1：ios
     */
    void bindDeviceInfo(@Param("auId") String auId, @Param("registerId") String registerId,
                        @Param("deviceInfo") String deviceInfo, @Param("platform") Integer platform);

    /**
     * 根据auid删除app_user
     * @param auid
     */
    void deleteByAuid(@Param("auid") String auid);

    /**
     * 根据会员id,判断该会员信息是否被其他用户认证
     *
     * @param memberId
     * @return
     */
    long selectAuidByMemberId(@Param("memberId") Integer memberId);

    /**
     * 更新app_user表的会员id、真实姓名、性别字段
     *
     * @param appUser
     */
    void updateByAuid(@Param("appUser") AppUserEntity appUser);

    /**
     * 保存用户留言信息
     *
     * @param map
     */
    void insertLeaveMessage(Map map);

    /**
     * 查询app用户留言列表
     *
     * @param
     */
    List appuUserLeaveMessageList(@Param("page") Page<Map<String, Object>> page, Map map);

    /**
     * 根据id 获取App用户留言信息
     *
     * @param id
     */
    HashMap<String, Object> appUserLeaveMessageById(
            @Param("id") String id
    );


    /**
     * 获取账号的创建时间
     *
     * @param auid
     * @return
     */
    String getAccountCreateTime(@Param("auid") String auid);

    /**
     * 查询所有有效经验
     * @param auid
     * @return
     */
    Integer selectTotalPointByUser(@Param("auid") String auid);


    /**
     * 更新当前用户指定消息类型的消息的已读状态
     * @param auid
     * @param cat_id
     */
    void updateMessageReadByCat(@Param("auid") String auid,@Param("cat_id") int cat_id);

    /**
     * 更换账号
     * @param mobile
     * @param auid
     */
    void updateAccount(@Param("mobile")String mobile,@Param("auid")String auid);
    /**
     * 更换手机号
     * @param mobile
     * @param oldMobile
     */
    void updateMobile(@Param("mobile")String mobile,@Param("oldMobile")String oldMobile);

    /**
     * 未审核完的困难帮扶
     * @param uid
     * @return
     */
    int selectCountStraitened(@Param("uid") String uid);

    /**
     * 未审核完的法律援助
     * @param uid
     * @return
     */
    int selectCountSupport(@Param("uid") String uid);

    /**
     * 入会
     * @param uid
     * @return
     */
    int selectCountMemberApply(@Param("uid") String uid);

    /**
     * 转会
     * @param uid
     * @return
     */
    int selectCountMemberTransferApply(@Param("uid") String uid);



    String selectAuid(@Param("memberId")String memberId);

}
