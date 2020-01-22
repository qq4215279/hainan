package com.gobestsoft.common.modular.smp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.smp.model.Show;
import com.gobestsoft.common.modular.smp.model.Entity.ShowEntity;

/**
 * 秀吧记录Mapper
 * @author zhangdw
 * 2018年6月5日 18:19
 */
@Repository
public interface ShowMapper extends BaseMapper<Show>{

	/**
	 * 秀吧列表查询
	 * @param pageBounds
	 * @param auid 秀吧记录的创建人id
	 * @param currentid 当前登录人id
	 * @return
	 */
	List<ShowEntity> list(@Param("page") RowBounds pageBounds,
                          @Param("auid") String auid,
                          @Param("currentid") String currentid);
	
	/**
	 * 多条件分页查询方法
	 * @param page
	 * @param fileType
	 * @return
	 */
	public List<Map<String,Object>> selectListPage(
            @Param("page") Page<Map<String, Object>> page,
            @Param("fileType") Integer fileType);

	/**
	 * 伪删除一条数据
	 * @param id
	 */
	public void remove(Integer id);

	
}
