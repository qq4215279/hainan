package com.gobestsoft.common.modular.system.dao;

import com.gobestsoft.common.modular.dao.model.QualificationPojo;
import org.springframework.stereotype.Repository;

/**
 * 企业资质信息Dao层类
 * @author zhangdw
 * 2018年5月24日 上午11：15
 */
@Repository
public interface QualificationDao {
	
	/**
	 * 插入一条记录
	 * @param pojo 实体对象
	 */
	public void insert(QualificationPojo pojo);
	
	/**
	 * 根据id,更新一条记录
	 * @param pojo 实体对象
	 */
	public void updateById(QualificationPojo pojo);
	
    /**
     * 根据id,查询一条Qualification格式的记录
     * @param id 主键
     */
    public QualificationPojo selectById(Integer id);

    /**
     * 根据id,更新一条记录的状态
     * @param id
     */
    public void updateStatus(Integer id);

    /**
     * 根据当前用户user_id,查询出一条企业资质的基本信息
     * @param uid
     * @return
     */
	public QualificationPojo selectByUid(String uid);
}
