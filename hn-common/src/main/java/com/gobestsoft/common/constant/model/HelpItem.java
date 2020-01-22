package com.gobestsoft.common.constant.model;

import lombok.Data;

/**
 * 困难帮扶子项
 */
@Data
public class HelpItem  {
    /**
     * 总数量
     */
    private int total_count;
    /**
     * 日办理人数
     */
    private int daily_count;
    /**
     * 周办理人数
     */
    private int week_count;
    /**
     * 月办理人数
     */
    private int month_count;

}

