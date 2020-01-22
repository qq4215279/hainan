package com.gobestsoft.mamage.moudle.system.parameter;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * create by gutongwei
 * on 2018/8/10 上午10:34
 */
@Data
public class DictParameter {

    @NotEmpty(message = "字典名称不能为空")
    private String dictName;
    @NotEmpty(message = "字典识别码不能为空")
    private String groupCode;

    private DictEntry[] entrys;

}
