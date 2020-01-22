package com.gobestsoft.common.modular.mst.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 通用Dao层接口
 * @author zhangdw
 * 2018年5月11日 下午3点52分
 */
@Repository
public interface MstBaseDao<T> {
	
	/**
	 * 插入方法
	 * @param pojo 实体对象
	 */
	void insert(T pojo);
	
	 /**
     * 查询方法
     * @param id 主键
     */
    T selectById(@Param("id") Integer id);
	
	/**
	 * 更新方法，更新全部字段
	 * @param pojo 实体对象
	 */
	void updateAllColumnById(T pojo);
	
	/**
	 * 删除方法
	 * @param id 主键
	 */
	void remove(@Param("id") Integer id);
}
