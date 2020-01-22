package com.gobestsoft.mamage.moudle.bigData.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gobestsoft.common.constant.model.*;
import com.gobestsoft.common.modular.dept.mapper.BigDataMapper;
import com.gobestsoft.core.reids.RedisConstants;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.BeanUtil;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class BigDataService {

    @Autowired
    BigDataMapper bigDataMapper;
    @Resource
    RedisUtils redisUtils;

    /**
     * 查询所有困难职工数
     * @param type 1困难职工 2、医疗互助
     * @return
     */
    public Integer getStraitenedCountAll(int type) {
        return bigDataMapper.selectStraitenedCount(null, null,type);
    }

    /**
     * 查询今天的所有困难职工人数
     * @param  type 1困难职工 2、医疗互助
     * @return
     */
    public Integer getStraitenedDaily(int type) {
        Calendar ca = Calendar.getInstance();

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

        String start_time = sf.format(ca.getTime());

        return bigDataMapper.selectStraitenedCount(start_time, start_time,type);
    }

    /**
     * 查询本周的所有困难职工人数
     * @param  type 1困难职工 2、医疗互助
     * @return
     */
    public Integer getStraitenedWeek(int type) {
        Calendar ca = Calendar.getInstance();


        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

        String start_time = sf.format(ca.getTime());

        ca.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        String end_time = sf.format(ca.getTime());

        return bigDataMapper.selectStraitenedCount(start_time, end_time,type);
    }
    /**
     * 查询本月的所有困难职工人数
     * @param  type 1困难职工 2、医疗互助
     * @return
     */
    public Integer getStraitenedMonth(int type) {
        Calendar ca = Calendar.getInstance();


        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

        String start_time = sf.format(ca.getTime());


        ca.set(Calendar.DAY_OF_MONTH,1);

        String end_time = sf.format(ca.getTime());
        return bigDataMapper.selectStraitenedCount(start_time, end_time,type);
    }


    /**
     * 获取困难帮扶的相关数据
     *
     * @return
     */
    public HelpData getKNBF_data() {
        HelpData helpData = new HelpData();

        //困难帮扶统计
        HelpItem knzg = new HelpItem();

        knzg.setTotal_count(getStraitenedCountAll(1));

        knzg.setDaily_count(getStraitenedDaily(1));

        knzg.setWeek_count(getStraitenedWeek(1));

        knzg.setMonth_count(getStraitenedMonth(1));

        //困难职工
        helpData.setKunNan(knzg);


        HelpItem ylhz = new HelpItem();

        ylhz.setDaily_count(getStraitenedDaily(2));

        ylhz.setWeek_count(getStraitenedWeek(2));

        ylhz.setMonth_count(getStraitenedMonth(2));

        ylhz.setTotal_count(getStraitenedCountAll(2));

        helpData.setYiLiao(ylhz);

        //帮扶金额 假数据！！！！！！！！！！！！！！！！！
        HelpItem bfje = new HelpItem();

        String[] exclude = {"total_count"};

        try {
            //复制帮扶的人数
            BeanUtil.tranferValues(knzg, bfje, exclude);
            bfje.setTotal_count(0);
            helpData.setBangFu(bfje);

        } catch (Exception e) {
            e.printStackTrace();
        }

        helpData.setReXian(new HelpItem());


        return helpData;
    }

    /**
     * 获取大数据真实数据
     *
     * @return
     */
    public BigData getBigData(String bigDataAdditionTime) {


        BigData bigData = new BigData();


        //困难帮扶         部分数据需要客户提供
        HelpData knbf_data = getKNBF_data();

        bigData.setHelpData(knbf_data);
        //新媒体
        NewMediaData newMediaData = bigDataMapper.selectUnionMediaData();

        if(newMediaData!=null){
            Integer anInt = redisUtils.getInt(RedisConstants.APP_OPEN_COUNT);
            newMediaData.setApp_visit_count(newMediaData.getApp_visit_count()+(anInt==null?0:anInt));
            newMediaData.setApp_signUp_count(bigDataMapper.selectAppUser());
        }


        //法律援助
        SeekLegalAdviceData seekLegalAdviceData = bigDataMapper.selectLawAdviceData();
        //组织建设
        OrganizationData organizationData = selectOrgBuildData(bigDataAdditionTime);

        bigData.setSeekLegalAdviceData(seekLegalAdviceData);

        bigData.setOrganizationData(organizationData);

        bigData.setNewMediaData(newMediaData);
        //普惠商城数据
        HPMallData mallData = getPuHuiMallData();

        bigData.setHpMallData(mallData);

        String [] arr = {"海口市","三亚市","琼中","白沙"};

        //活动数据暂时为假数据
        for (int i = 0; i < 2; i++) {
            ActivityOrEducationData bean = new ActivityOrEducationData();

            List<JSONObject> jsonObjectList = new ArrayList<>();

            if (i == 0) {

                for (int j = 0; j < 3; j++) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", arr[j]);
                    obj.put("times", 300-50*j);
                    obj.put("members", 14000-j*1000);
                    jsonObjectList.add(obj);
                }
                bean.setTotal_participation_number(120000);
                bean.setTotal_times(999);
                bean.setDataListSort(jsonObjectList);
                bigData.setHd(bean);

            } else {
                Calendar calendar = Calendar.getInstance();
                for (int j = 0; j < 6; j++) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", DateUtil.getMonthCnName(calendar));
                    obj.put("times", 300-50*j);
                    obj.put("members", 15000-500*j);
                    jsonObjectList.add(obj);
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                }
                bean.setTotal_participation_number(120000);
                bean.setTotal_times(999);
                bean.setDataListSort(jsonObjectList);
                bigData.setPx(bean);

            }

        }

        return bigData;

    }

    /**
     * 获取普惠商城的数据
     * @return
     */
    public HPMallData getPuHuiMallData() {

        HPMallData hpMallData = new HPMallData();

        String puhuiUrl = "http://shop.hnszgh.org:9087/b2b2c/public/index.php/api/getStatisticalData";

        String json = HttpUtil.sendGet(puhuiUrl);

        try {
            if (StringUtils.isNotEmpty(json)) {

                JSONObject jsonObject = JSON.parseObject(json);
                if (jsonObject.getInteger("code") == 200) {
                    JSONObject data = jsonObject.getJSONObject("data");

                    hpMallData.setProduct_count(data.getIntValue("goodsAmount"));
                    hpMallData.setLocate_vendor(data.getIntValue("shopAmount"));
                    hpMallData.setCover_member_count(data.getIntValue("userAmount"));
                    hpMallData.setTrade_count(data.getIntValue("tradeAmount"));
                    hpMallData.setTotal_amount(data.getDouble("tradeTotal"));

                    return hpMallData;
                } else {
                    throw new RuntimeException(jsonObject.getString("message"));
                }
            } else {
                throw new RuntimeException("获取普惠商城数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析普惠商城数据失败");
        }

    }


    /**
     * 获取建会数据
     * @param bigDataAdditionTime
     * @return
     */
    public OrganizationData selectOrgBuildData(String bigDataAdditionTime){

        OrganizationData organizationData = bigDataMapper.selectOrgBuildData(bigDataAdditionTime);
        return organizationData;
    }

}