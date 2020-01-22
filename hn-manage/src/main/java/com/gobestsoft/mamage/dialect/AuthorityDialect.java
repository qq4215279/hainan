package com.gobestsoft.mamage.dialect;

import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.dialect.authority.ButtonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;

import java.util.HashSet;
import java.util.Set;

/**
 * create by gutongwei
 * on 2018/8/3 下午11:33
 */
public class AuthorityDialect extends SpringStandardDialect {

    public final String prefix = "qx";

    public String getPrefix() {
        return prefix;
    }


    @Autowired
    private ManageBasic manageBasic;

    @Override
    public Set<IProcessor> getProcessors() {
        Set<IProcessor> processors = new HashSet<>();
        processors.add(new ButtonProcessor(manageBasic));
        return processors;
    }
}
