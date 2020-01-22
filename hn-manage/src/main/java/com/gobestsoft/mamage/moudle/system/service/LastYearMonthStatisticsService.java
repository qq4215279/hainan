package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.base.StatisticsService;
import com.gobestsoft.common.modular.bigdata.dao.LastYearMonthStatisticsDao;
import com.gobestsoft.common.modular.bigdata.dao.model.LastYearMonthStatistics;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: gutongwei
 * @Date: 2019/5/15 5:42 PM
 * @Description:
 **/
@Service
public class LastYearMonthStatisticsService extends StatisticsService<LastYearMonthStatisticsDao, LastYearMonthStatistics> {
    @Override
    protected String getRedisCacheKey() {
        return "blackboard:last-year-month:statistics";
    }

    @Override
    protected void fillList(List<LastYearMonthStatistics> selectEntities, List<LastYearMonthStatistics> result) {

    }

    @Override
    protected void afterSelectList(List<LastYearMonthStatistics> statistics) {

    }

    @Override
    protected LastYearMonthStatistics newInstance() {
        return new LastYearMonthStatistics();
    }

    @Override
    protected void summarySingle(LastYearMonthStatistics select, LastYearMonthStatistics result) {

        if (select == null || result == null) return;

        result.setBindUserCount(result.getBindUserCount() + select.getBindUserCount());
        result.setMemberCount(result.getMemberCount() + select.getMemberCount());
        result.setRegisterCount(result.getRegisterCount() + select.getRegisterCount());

    }
}
