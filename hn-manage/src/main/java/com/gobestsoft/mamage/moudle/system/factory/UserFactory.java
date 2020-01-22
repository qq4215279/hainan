package com.gobestsoft.mamage.moudle.system.factory;

import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.mamage.moudle.system.transfer.UserDto;
import org.springframework.beans.BeanUtils;

/**
 * 用户创建工厂
 *
 * @author gobestsoft
 * @date 2017-05-05 22:43
 */
public class UserFactory {

    public static User createUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            user.setId(UUIDUtil.getUUID()); // sunbin
            return user;
        }
    }
    
    public static User updateUser(UserDto userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }
}
