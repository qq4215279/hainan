package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("xlyz_xlam")
public class XlyzAmPojo {

    private String id;

    private String  title;

    private String  cover;

    private String  attachment;

    private String  createTime;

    private String  playTime;

    private String  createUid;
}
