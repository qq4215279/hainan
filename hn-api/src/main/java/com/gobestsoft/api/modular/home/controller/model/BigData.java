package com.gobestsoft.api.modular.home.controller.model;

import com.gobestsoft.common.modular.cms.model.Activity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 大数据记录
 */
@Data
public class BigData implements Serializable {

    private NewMediaData newMediaData;

    private ActivityOrEducationData hd;

    private ActivityOrEducationData px;

    private OrganizationData organizationData;

    private HelpData helpData;

    private HPMallData hpMallData;

    private SeekLegalAdviceData seekLegalAdviceData;

}


