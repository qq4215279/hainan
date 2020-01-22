package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.base.StatisticsService;
import com.gobestsoft.common.modular.bigdata.dao.MemberStatisticsDao;
import com.gobestsoft.common.modular.bigdata.dao.model.MemberStatistics;
import com.gobestsoft.core.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: gutongwei
 * @Date: 2019/5/15 4:11 PM
 * @Description:
 **/
@Service
public class MemberStatisticsService extends StatisticsService<MemberStatisticsDao, MemberStatistics> {

    @Override
    protected String getRedisCacheKey() {
        return "blackboard:member:statistics";
    }

    @Override
    protected void fillList(List<MemberStatistics> selectEntities, List<MemberStatistics> result) {

    }

    @Override
    protected void afterSelectList(List<MemberStatistics> statistics) {

    }

    @Override
    protected MemberStatistics newInstance() {
        return new MemberStatistics();
    }

    @Override
    protected void summarySingle(MemberStatistics select, MemberStatistics result) {
        if (result != null && select != null) {
            result.setMen(result.getMen() + select.getMen());
            result.setWomen(result.getWomen() + select.getWomen());
        }
    }


    public MemberStatistics summarySingle() {
        //设置项目开始时间
        setStartDate("20180601");

        //设置项目结束时间
        setEndDate(DateUtil.getDays());
        return statisticsTodaySummarySingle();
    }

}
