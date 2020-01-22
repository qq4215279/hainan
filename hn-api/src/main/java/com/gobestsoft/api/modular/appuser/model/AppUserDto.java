package com.gobestsoft.api.modular.appuser.model;

import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class AppUserDto implements Serializable {


    public AppUserDto() {
        super();
    }

    public AppUserDto(String auid, String token, String avatar, String nickname, Integer sex, String mobile,
                      Integer integral, Integer experience, String realName,
                      String registerId, AppUserMember member_info, boolean applyMember) {
        super();
        this.auid = auid;
        this.avatar = WebSiteUtil.getBrowseUrl(avatar);
        this.nickname = nickname;

        this.mobile = mobile;
        this.token = token;
        this.integral = integral;
        this.experience = experience;
        this.real_name = realName;
        this.member_info = member_info;
        if (ToolUtil.isEmpty(member_info)) {
            this.member_info = null;
        }
        this.registerId = registerId;

        if (applyMember) {
            this.vip_level = 1;
        }
        if (member_info != null) {
            this.vip_level = 2;
        }

    }

    public String getSex(){
        if(sex==null){
            return "未知";
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(sex);

        if(StringUtils.isNotEmpty(sex)&& !isNum.matches()){
            return sex;
        }
        if ("1".equals(sex)) {
            this.sex = "男";
        } else if ("2".equals(sex)) {
            this.sex = "女";
        }else{
            this.sex="未知";
        }
        return sex;
    }


    private String auid;

    private String avatar;


    private String nickname;

    private String sex;

    private String mobile;

    private String token;

    private Integer integral;

    private Integer experience;

    private String real_name;

    private int vip_level;

    private int point;

    private AppUserMember member_info;


    private String registerId;

    private String city_name;

    private String city_code;

    private String isResidence;
}
