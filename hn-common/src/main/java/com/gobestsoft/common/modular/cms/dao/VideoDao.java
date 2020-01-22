package com.gobestsoft.common.modular.cms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gobestsoft.common.modular.cms.model.NewsVideo;

/**
* @author 作者 : liqicheng
* @version 创建时间：2017年12月5日 上午11:34:24
* 
*/
@Repository
public interface VideoDao {

	 List<NewsVideo> getVideo(
             @Param("cityCode") String cityCode,
             @Param("infoType") Integer infoType,
             @Param("mediaType") Integer mediaType,
             @Param("status") Integer status,
             @Param("startIndex") int startIndex,
             @Param("pageSize") int pageSize);


}
