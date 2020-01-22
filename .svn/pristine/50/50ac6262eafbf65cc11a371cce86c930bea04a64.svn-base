package com.gobestsoft.common.base;

import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author: gutongwei
 * @Date: 2019/5/14 9:42 PM
 * @Description:
 **/
@Accessors(chain = true)
@Data
public abstract class StatisticsService<M extends StatisticsBaseDao<T>, T> {

    /**
     * 统计开始日期 yyyyMMdd
     */
    private String startDate;
    /**
     * 统计结束日期yyyyMMdd
     */
    private String endDate;

    /**
     * 组织ID
     */
    private Integer deptId;

    /**
     * 统计模式
     */
    private StatisticsMode statisticsMode = StatisticsMode.MONTH;

    @Autowired
    private M dao;

    @Autowired
    private RedisUtils redisUtils;


    private T t;

    /**
     * 统计模式
     */
    protected enum StatisticsMode {
        MONTH,
        DAY,
        YEAR
    }


    /**
     * 获取redis 缓存key
     *
     * @return
     */
    protected abstract String getRedisCacheKey();

    /**
     * 获取日期的缓存
     *
     * @param date
     * @return
     */
    private String getRedisCacheKeyByDate(String date) {
        if (deptId != null) {
            return getRedisCacheKey() + ":" + date + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":" + date;
    }

    /**
     * 获取月份的缓存
     *
     * @param month
     * @return
     */
    private String getRedisCacheKeyByMonth(String month) {
        if (deptId != null) {
            return getRedisCacheKey() + ":month:" + month + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":month:" + month;
    }

    /**
     * 获取缓存本月至今日之前key
     *
     * @param yesterday 昨天日期
     * @return
     */
    private String getRedisCacheKeyByMonthBeforeToday(String yesterday) {
        if (deptId != null) {
            return getRedisCacheKey() + ":month:before-today:" + yesterday + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":month:before-today:" + yesterday;
    }

    /**
     * 获取包含昨天的缓存
     * 日历模式
     *
     * @param yesterday
     * @return
     */
    private String getYesterdayCalendarHistoryKey(String yesterday) {
        if (deptId != null) {
            return getRedisCacheKey() + ":month:history:" + yesterday + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":month:history:" + yesterday;
    }

    /**
     * 获取月份的汇总缓存
     *
     * @param month
     * @return
     */
    private String summaryMonthCache(String month) {
        if (deptId != null) {
            return getRedisCacheKey() + ":month:summary:" + month + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":month:summary:" + month;
    }

    /**
     * 获取包含昨天本月的缓存
     * 汇总
     *
     * @param yesterday
     * @return
     */
    private String beforeYesterdayThisMonthSummaryCacheKey(String yesterday) {
        if (deptId != null) {
            return getRedisCacheKey() + ":summary:this-month-before:" + yesterday + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":summary:this-month-before:" + yesterday;
    }

    /**
     * 获取包含昨天的汇总缓存key
     * 汇总
     *
     * @param yesterday
     * @return
     */
    private String beforeYesterdaySummaryCacheKey(String yesterday) {
        if (deptId != null) {
            return getRedisCacheKey() + ":summary:before-yesterday:" + yesterday + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":summary:before-yesterday:" + yesterday;
    }


    /**
     * 获取今天统计
     * 不计入缓存
     *
     * @return
     */
    protected T getTodaySingleStatistics() {

        String cacheKey = getRedisCacheKey() + ":single:today-temporary";

        T result = (T) redisUtils.get(cacheKey);

        if (result == null) {
            result = this.dao.statisticsOne(deptId, DateUtil.todayStartTime(), DateUtil.todayEndTime());
            redisUtils.set(cacheKey, result, 180l);
        }

        return result;
    }

    /**
     * 填充数据
     *
     * @param selectEntities 查询结果
     * @param result         填充结果
     */
    protected abstract void fillList(List<T> selectEntities, List<T> result);

    /**
     * 列表查询之后事件
     *
     * @return
     */
    protected abstract void afterSelectList(List<T> statistics);

    /**
     * 根据开始日期，结束日期汇总数据
     *
     * @param includeBeforeStart 开始日期包含之前的
     * @return
     */
    protected final List<T> summaryStatisticsList(boolean includeBeforeStart) {
        //先去尝试获取今天之前所有的汇总缓存
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        String bYSCKey = beforeYesterdaySummaryCacheKey(DateUtil.format(yesterday.getTime(), "yyyyMMdd"));

        List<T> result = (List<T>) redisUtils.get(bYSCKey);

        if (result == null) {
            result = new ArrayList<>();

            Calendar startMonth = Calendar.getInstance();
            startMonth.setTime(DateUtil.parse(startDate, "yyyyMMdd"));

            Calendar endMonth = Calendar.getInstance();
            endMonth.setTime(DateUtil.parse(endDate, "yyyyMMdd"));

            //现在月份与开始月份的间隔时间
            int spaceMonth = DateUtil.getMonthSpace(endMonth.getTime(), startMonth.getTime());

            String month;
            String startTime;
            String endTime;

            List<T> statistics;
            //查询项目发布月之后，每个月的数据,不包含最后一个月
            for (int i = 0; i < spaceMonth; i++) {
                //查询项目发布月之前及当月的数据
                month = DateUtil.format(startMonth.getTime(), "yyyyMM");

                String redisKey = summaryMonthCache(month);

                statistics = (List<T>) redisUtils.get(redisKey);

                if (statistics == null) {

                    //开始时间
                    startTime = DateUtil.getDays(DateUtil.getMonthFirstDay(startMonth));
                    //结束时间
                    endTime = DateUtil.getDays(DateUtil.getMonthLastDay(startMonth));

                    if (includeBeforeStart && i == 0) {

                        statistics = this.dao.statistics(deptId, null, startTime + "235959");
                    } else {

                        statistics = this.dao.statistics(deptId, startTime + "000000", endTime + "235959");
                    }

                    afterSelectList(statistics);

                    redisUtils.set(redisKey, statistics);
                }

                fillList(statistics, result);

                startMonth.add(Calendar.MONTH, 1);
            }

            //查询昨天之前，当月的数据
            Calendar today = Calendar.getInstance();
            if (today.get(Calendar.DAY_OF_MONTH) > 1) {
                today.add(Calendar.DAY_OF_MONTH, -1);

                String yesterdayDate = DateUtil.format(today.getTime(), "yyyyMMdd");
                String redisKey = beforeYesterdayThisMonthSummaryCacheKey(yesterdayDate);

                statistics = (List<T>) redisUtils.get(redisKey);

                if (statistics == null) {
                    startTime = DateUtil.getDays(DateUtil.getMonthFirstDay(today));

                    statistics = this.dao.statistics(deptId, startTime + "000000", yesterdayDate + "235959");

                    afterSelectList(statistics);
                    redisUtils.set(redisKey, statistics);
                }

                fillList(statistics, result);
            }

            redisUtils.set(bYSCKey, result);
        }

        //查询今天
        List<T> todayStatistics = statisticsTodaySummaryList();

        afterSelectList(todayStatistics);

        fillList(todayStatistics, result);

        return result;
    }

    /***
     * 统计今天的汇总数据
     * @return
     */
    protected final List<T> statisticsTodaySummaryList() {

        String cacheKey = getRedisCacheKey() + ":summary:today-temporary";

        List<T> statistics = (List<T>) redisUtils.get(cacheKey);

        if (statistics == null) {

            statistics = this.dao.statistics(deptId, DateUtil.todayStartTime(), DateUtil.todayEndTime());
            afterSelectList(statistics);

            redisUtils.set(cacheKey, statistics, 180l);
        }

        return statistics;
    }


    /**
     * 获取月份的单个汇总缓存
     *
     * @param month
     * @return
     */
    private String singleMonthCache(String month) {
        if (deptId != null) {
            return getRedisCacheKey() + ":month:single:" + month + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":month:single:" + month;
    }

    /**
     * 获取单个包含昨天本月的缓存
     * 汇总
     *
     * @param yesterday
     * @return
     */
    private String beforeYesterdayThisMonthSingleCacheKey(String yesterday) {
        if (deptId != null) {
            return getRedisCacheKey() + ":single:this-month-before:" + yesterday + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":single:this-month-before:" + yesterday;
    }

    /**
     * 获取包含昨天的单个汇总缓存key
     * 汇总
     *
     * @param yesterday
     * @return
     */
    private String beforeYesterdaySingleCacheKey(String yesterday) {
        if (deptId != null) {
            return getRedisCacheKey() + ":single:before-yesterday:" + yesterday + ":dept-" + deptId;
        }
        return getRedisCacheKey() + ":single:before-yesterday:" + yesterday;
    }

    protected abstract T newInstance();


    protected abstract void summarySingle(T select, T result);

    /**
     * 单个汇总
     *
     * @return
     */
    public final T statisticsTodaySummarySingle() {
        //先去尝试获取今天之前所有的汇总缓存
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        String bYSCKey = beforeYesterdaySingleCacheKey(DateUtil.format(yesterday.getTime(), "yyyyMMdd"));

        T result = (T) redisUtils.get(bYSCKey);

        if (result == null) {
            result = newInstance();

            Calendar startMonth = Calendar.getInstance();
            startMonth.setTime(DateUtil.parse(startDate, "yyyyMMdd"));

            Calendar endMonth = Calendar.getInstance();
            endMonth.setTime(DateUtil.parse(endDate, "yyyyMMdd"));

            //现在月份与开始月份的间隔时间
            int spaceMonth = DateUtil.getMonthSpace(endMonth.getTime(), startMonth.getTime());

            String month;
            String startTime;
            String endTime;

            T single;
            //查询项目发布月之后，每个月的数据,不包含最后一个月
            for (int i = 0; i < spaceMonth; i++) {
                //查询项目发布月之前及当月的数据
                month = DateUtil.format(startMonth.getTime(), "yyyyMM");

                String redisKey = singleMonthCache(month);

                single = (T) redisUtils.get(redisKey);

                if (single == null) {

                    //开始时间
                    startTime = DateUtil.getDays(DateUtil.getMonthFirstDay(startMonth));
                    //结束时间
                    endTime = DateUtil.getDays(DateUtil.getMonthLastDay(startMonth));

                    if (i == 0) {

                        single = this.dao.statisticsOne(deptId, null, startTime + "235959");
                    } else {

                        single = this.dao.statisticsOne(deptId, startTime + "000000", endTime + "235959");
                    }

                    redisUtils.set(redisKey, single);
                }

                summarySingle(single, result);

                startMonth.add(Calendar.MONTH, 1);
            }

            //查询昨天之前，当月的数据
            Calendar today = Calendar.getInstance();
            if (today.get(Calendar.DAY_OF_MONTH) > 1) {
                today.add(Calendar.DAY_OF_MONTH, -1);

                String yesterdayDate = DateUtil.format(today.getTime(), "yyyyMMdd");
                String redisKey = beforeYesterdayThisMonthSingleCacheKey(yesterdayDate);

                single = (T) redisUtils.get(redisKey);

                if (single == null) {
                    startTime = DateUtil.getDays(DateUtil.getMonthFirstDay(today));

                    single = this.dao.statisticsOne(deptId, startTime + "000000", yesterdayDate + "235959");

                    redisUtils.set(redisKey, single);
                }

                summarySingle(single, result);
            }

            redisUtils.set(bYSCKey, result);
        }

        //查询今天
        T todayStatistics = getTodaySingleStatistics();

        summarySingle(todayStatistics, result);

        return result;
    }


    /**
     * 日历排序
     *
     * @return
     */
    public SortedMap<String, T> monthSort() {

        SortedMap<String, T> sortedMap = new TreeMap<String, T>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }

        });

        //先去尝试获取今天之前所有的汇总缓存
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        T single;
        String month;
        String startTime;
        String endTime;

        Calendar startMonth = Calendar.getInstance();
        startMonth.setTime(DateUtil.parse(startDate, "yyyyMMdd"));

        Calendar endMonth = Calendar.getInstance();
        endMonth.setTime(DateUtil.parse(endDate, "yyyyMMdd"));

        //现在月份与开始月份的间隔时间
        int spaceMonth = DateUtil.getMonthSpace(endMonth.getTime(), startMonth.getTime());


        //查询项目发布月之后，每个月的数据,不包含最后一个月
        for (int i = 0; i < spaceMonth; i++) {
            //查询项目发布月之前及当月的数据
            month = DateUtil.format(startMonth.getTime(), "yyyyMM");

            String redisKey = singleMonthCache(month);

            single = (T) redisUtils.get(redisKey);

            if (single == null) {

                //开始时间
                startTime = DateUtil.getDays(DateUtil.getMonthFirstDay(startMonth));
                //结束时间
                endTime = DateUtil.getDays(DateUtil.getMonthLastDay(startMonth));

                single = this.dao.statisticsOne(deptId, startTime + "000000", endTime + "235959");

                redisUtils.set(redisKey, single);
            }

            sortedMap.put(month, single);

            startMonth.add(Calendar.MONTH, 1);
        }


        T thisMonthSingle = newInstance();

        //查询昨天之前，当月的数据
        Calendar today = Calendar.getInstance();

        month = DateUtil.format(today.getTime(), "yyyyMM");

        if (today.get(Calendar.DAY_OF_MONTH) > 1) {
            today.add(Calendar.DAY_OF_MONTH, -1);

            String yesterdayDate = DateUtil.format(today.getTime(), "yyyyMMdd");
            String redisKey = beforeYesterdayThisMonthSingleCacheKey(yesterdayDate);

            single = (T) redisUtils.get(redisKey);

            if (single == null) {
                startTime = DateUtil.getDays(DateUtil.getMonthFirstDay(today));

                single = this.dao.statisticsOne(deptId, startTime + "000000", yesterdayDate + "235959");

                redisUtils.set(redisKey, single);
            }
            //合并本月
            summarySingle(single, thisMonthSingle);
        }

        //查询今天
        T todayStatistics = getTodaySingleStatistics();

        //合并本月
        summarySingle(todayStatistics, thisMonthSingle);


        sortedMap.put(month, thisMonthSingle);

        return sortedMap;

    }

}
