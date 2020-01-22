package com.gobestsoft.common.modular.appuser.model;

import com.gobestsoft.core.util.DateUtil;
import lombok.Data;

@Data
public class UserIntegralEntity {

    private int id;

    private String name;

    private String create_time;

    private int value;

    public String getCreate_time() {
        return DateUtil.parseAndFormat(create_time, "yyyyMMddHHmmss", "yyyy-MM-dd");
    }

}
