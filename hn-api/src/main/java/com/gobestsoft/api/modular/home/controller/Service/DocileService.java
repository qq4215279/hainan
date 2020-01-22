package com.gobestsoft.api.modular.home.controller.Service;

import com.gobestsoft.api.modular.home.controller.model.DeptMemberDocile;
import com.gobestsoft.common.base.StatisticsBaseEntity;
import com.gobestsoft.common.base.StatisticsService;
import com.gobestsoft.common.modular.bigdata.dao.DocileDao;
import com.gobestsoft.core.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gutongwei
 * @Date: 2019/5/15 10:33 AM
 * @Description:
 **/
@Service
public class DocileService extends StatisticsService<DocileDao, StatisticsBaseEntity> {

    @Override
    protected String getRedisCacheKey() {
        return "big-data:dept-member:docile";
    }

    @Override
    protected void fillList(List<StatisticsBaseEntity> selectEntities, List<StatisticsBaseEntity> result) {
        for (StatisticsBaseEntity entity : selectEntities) {
            StatisticsBaseEntity t = null;

            for (StatisticsBaseEntity d : result) {
                if (d.getName().equals(entity.getName())) {
                    t = d;
                }
            }

            if (t == null) {
                result.add(entity);
            } else {
                t.setValue(t.getValue() + entity.getValue());
            }

        }
    }

    @Override
    protected void afterSelectList(List<StatisticsBaseEntity> statistics) {
        List<StatisticsBaseEntity> result = new ArrayList<>();

        if (statistics != null) {

            Integer otherCount = statistics.stream().mapToInt((o) -> o.getValue()).sum();

            for (String city : citys) {

                int count = 0;
                for (StatisticsBaseEntity o : statistics) {
                    Integer value = o.getValue();
                    String name = o.getName();
                    if (name.contains(city)) {
                        try {
                            count += value;
                        } catch (Exception e) {

                        }
                    }
                }

                result.add(new StatisticsBaseEntity(city, count));
                otherCount -= count;
            }

            result.add(new StatisticsBaseEntity("其他", otherCount));
        }

        statistics.clear();
        statistics.addAll(result);
    }

    @Override
    protected StatisticsBaseEntity newInstance() {
        return new StatisticsBaseEntity();
    }

    @Override
    protected void summarySingle(StatisticsBaseEntity select, StatisticsBaseEntity result) {

    }

    //匹配可能存在的机关
    private static final String citys[] = {
            "海口", "三亚", "三沙", "儋州",
            "五指山", "文昌", "琼海", "万宁", "东方",
            "定安", "屯昌", "澄迈", "临高",
            "白沙", "昌江", "乐东", "陵水", "保亭", "琼中",
            //省份
            "北京", "天津", "上海", "重庆", "河北", "山西",
            "辽宁", "吉林", "黑龙江", "江苏", "浙江",
            "安徽", "福建", "江西", "山东", "河南", "湖北",
            "湖南", "广东", "四川", "贵州", "云南",
            "陕西", "甘肃", "青海", "蒙古", "广西", "西藏",
            "宁夏", "新疆", "香港", "澳门", "台湾"

    };


    public List<DeptMemberDocile> getMemberInfoByCity(Integer deptId) {
        List<DeptMemberDocile> result = new ArrayList<>();
        //设置项目开始时间
        setStartDate("20180601");
        //设置项目结束时间
        setEndDate(DateUtil.getDays());
        setDeptId(deptId);

        List<StatisticsBaseEntity> statistics = summaryStatisticsList(true);
        Integer memberCount = statistics.stream().mapToInt(StatisticsBaseEntity::getValue).sum();
        statistics.sort((o1, o2) -> {
            Integer value1 = o1.getValue();
            Integer value2 = o1.getValue();
            return value2 - value1;
        });

        //如果超过10个,就取前9，最后一个拼为其他
        if (statistics.size() >= 10) {
            int index = 0;
            List<StatisticsBaseEntity> list = new ArrayList<>();
            for (StatisticsBaseEntity m : statistics) {
                if (index > 9) {
                    break;
                }
                list.add(m);
                memberCount -= m.getValue();
                index++;
            }
            if (memberCount > 0) {
                statistics = list;
                statistics.add(new StatisticsBaseEntity("其他", memberCount));
            }
        }

        statistics.forEach(s -> result.add(new DeptMemberDocile(s.getName(), s.getValue())));

        return result;
    }

}
