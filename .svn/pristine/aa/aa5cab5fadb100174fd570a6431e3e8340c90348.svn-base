package com.gobestsoft.common.modular.dao.model;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName("sys_statistic")
@Data
public class SysStatisticPojo {
    private String key;

    private Integer statistic;

    private String create_time;

    public SysStatisticPojo(){};

    public SysStatisticPojo(String key ,Integer statistic,String create_time) {
        this.create_time = create_time;
        this.statistic= statistic==null?0:statistic;
        this.key =key;
    }
}
