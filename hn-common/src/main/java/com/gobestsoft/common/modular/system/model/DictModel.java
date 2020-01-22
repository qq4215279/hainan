package com.gobestsoft.common.modular.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * create by gutongwei
 * on 2018/7/16 下午1:49
 */
@Data
@JsonIgnoreProperties(value = { "handler" })
public class DictModel implements Serializable {

    private String group_code;

    private String code;

    private String name;

    private List<DictModel> dict;

}
