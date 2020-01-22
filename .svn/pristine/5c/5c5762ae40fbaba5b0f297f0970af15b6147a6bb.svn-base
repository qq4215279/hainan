package com.gobestsoft.api.modular.home.controller.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * 活动互动或者教育培训的统计数据
 */
@Data
public class ActivityOrEducationData {

    /**
     * 总次数
     */
    private Integer total_times;
    /**
     * 总参与人数
     */
    private Integer total_participation_number;

    /**
     * 分别存储前三位的活动数据或者最近6个月的培训数据
     * 限定键值对
     * name:xx,
     * times:xx
     * members:xx
     */
    private List<JSONObject> dataListSort;


}
