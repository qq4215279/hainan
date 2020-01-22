package com.gobestsoft.common.modular.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.cms.model.BannerEntity;
import com.gobestsoft.common.modular.dao.model.AppBannerPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface AppBannerMapper extends BaseMapper<AppBannerPojo> {

	/**
	 * create by xiashasha
	 * on 2019-09-14
	 * 
	 * 获取banner
	 * @param count 
	 * @param type
	 * @return
	 */
	List<AppBannerPojo> bannerList(@Param("category") String category, @Param("count") int count);

	/**
	 * 查询所有专题类型
	 * @return
	 */
	List<Map>selectSeminarList(@Param("page") Page page,@Param("query_name") String query_name);


}