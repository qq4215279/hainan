package com.gobestsoft.common.modular.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dept.model.DeptMemberTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by duanmu on 2018/10/16.
 */
@Repository
public interface DeptMemberTransferMapper extends BaseMapper<DeptMemberTransfer> {

    /**
     * 转出工会审核页多条件分页查询
     * @param page
     * @param status
     * @param deptId
     * @param name
     * @return
     */
    List<Map<String, Object>> selectCheckByCondition(@Param("page") Page<Map<String, Object>> page,
                                                          @Param("status") String status,
                                                          @Param("deptId") Integer deptId,
                                                          @Param("name") String name);

    /**
     * 转入工会审核页多条件分页查询
     * @param page
     * @param status
     * @param deptId
     * @param name
     * @return
     */
    List<Map<String, Object>> selectSecondCheckByCondition(@Param("page") Page<Map<String, Object>> page,
                                                     @Param("status") String status,
                                                     @Param("deptId") Integer deptId,
                                                     @Param("name") String name);

}
