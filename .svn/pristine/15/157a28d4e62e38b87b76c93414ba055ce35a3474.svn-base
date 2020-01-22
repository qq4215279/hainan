package com.gobestsoft.common.modular.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gobestsoft.common.modular.system.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 管理员表 Mapper 接口
 * </p>
 *
 * @author gobestsoft
 * @since 2017-07-11
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    Integer delete(@Param("account") String account);

}
