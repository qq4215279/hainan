package com.gobestsoft.mamage.constant.dictmap;


import com.gobestsoft.mamage.constant.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author gobestsoft
 * @date 2017-05-06 15:43
 */
public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId","字典ID");
        put("dictGroupCode","字典识别码");
        put("dictCode","字典值");
        put("dictName","字典名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
