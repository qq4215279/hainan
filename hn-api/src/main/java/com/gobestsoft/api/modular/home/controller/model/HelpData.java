package com.gobestsoft.api.modular.home.controller.model;

import lombok.Data;

@Data
public class HelpData {
    /**
     * 困难职工
     */
    private HelpItem kunNan;
    /**
     * 帮扶金额
     */
    private HelpItem bangFu;
    /**
     * 热线办理
     */
    private HelpItem reXian;
    /**
     * 医疗互助
     */
    private HelpItem yiLiao;


}
