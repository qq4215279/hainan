package com.gobestsoft.core.util;

import com.thoughtworks.xstream.XStream;

/**
 * Created by duanmu on 2018/9/15.
 */
public class XstreamUtil {

    public static Object toBean(Class<?> clazz, String xml) {
        Object xmlObject = null;
        XStream xstream = new XStream();
        xstream.setupDefaultSecurity(xstream);
        xstream.processAnnotations(clazz);
        xstream.autodetectAnnotations(true);
        xmlObject = xstream.fromXML(xml);
        return xmlObject;
    }
}
