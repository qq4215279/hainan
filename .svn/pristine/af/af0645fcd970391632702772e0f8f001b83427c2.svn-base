package com.gobestsoft.mamage.moudle.bigData.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gobestsoft.common.constant.BigDataConstant;
import com.gobestsoft.common.constant.model.*;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.SpringContextHolder;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.bigData.service.BigDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("bigDataMng")
public class BigDataMngController extends BaseController {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    private String PREFIX="bigData/";
    @Autowired
    private BigDataService bigDataService;

    private static RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);

    private static  String citys[]={"海南省","海口市","三亚市","三沙市","儋州市",

            "五指山市","文昌市","琼海市","万宁市","东方市",

            "定安县","屯昌县","澄迈县","临高县",

            "白沙黎族自治县","昌江黎族自治县","乐东黎族自治县","陵水黎族自治县","保亭黎族苗族自治县","琼中黎族苗族自治县" };

    static{

        for(String city:citys){
            redisUtils.set(BigDataConstant.bigData_city_hn +Arrays.asList(citys).indexOf(city),city);
        }
    }

    @RequestMapping("/list")
    public String list(Model model){


        BigData bigdata = (BigData) redisUtils.get(BigDataConstant.bigData_hn );

        if(bigdata==null){
            bigdata = bigDataService.getBigData("20181001");
        }


        redisUtils.set(BigDataConstant.bigData_hn , bigdata);


        model.addAttribute("bigData", bigdata);

        return PREFIX+"list";

    }


    @RequestMapping("/updateData")
    @ResponseBody
    public Tip updateData(@RequestParam(name="type") int type,
                          @RequestParam(name="jsonString") String jsonString){

        BigData bigData = (BigData) redisUtils.get(BigDataConstant.bigData_hn );

        if(bigData==null){
            bigData = bigDataService.getBigData("20181001");
        }


        try{

            switch (type){
                case BigDataConstant.NewMediaData:
                    NewMediaData newMediaData = JSON.parseObject(jsonString, NewMediaData.class);
                    bigData.setNewMediaData(newMediaData);
                    break;

                case BigDataConstant.hd:
                    ActivityOrEducationData hd = JSON.parseObject(jsonString, ActivityOrEducationData.class);
                    bigData.setHd(hd);
                    break;

                case  BigDataConstant.px:
                    ActivityOrEducationData px = JSON.parseObject(jsonString, ActivityOrEducationData.class);
                    bigData.setPx(px);
                    break;


                case BigDataConstant.helpData:
                    HelpData helpData = JSON.parseObject(jsonString, HelpData.class);
                    bigData.setHelpData(helpData);
                    break;

                case BigDataConstant.hpMallData:
                    HPMallData hpMallData = JSON.parseObject(jsonString, HPMallData.class);
                    bigData.setHpMallData(hpMallData);
                    break;

                case BigDataConstant.organizationData:
                    OrganizationData organizationData = JSON.parseObject(jsonString, OrganizationData.class);
                    bigData.setOrganizationData(organizationData);
                    break;

                case BigDataConstant.seekLegalAdviceData:
                    SeekLegalAdviceData seekLegalAdviceData = JSON.parseObject(jsonString, SeekLegalAdviceData.class);
                    bigData.setSeekLegalAdviceData(seekLegalAdviceData);
                    break;
            }

            redisUtils.set(BigDataConstant.bigData_hn, bigData);



        }catch (Exception e){
            e.printStackTrace();
            return fail("更新失败");
        }

        return success();
    }









    protected BigData create() {

        BigData bigData = new BigData();
        //帮扶

        HelpData helpData = new HelpData();

        HelpItem item = new HelpItem();

        helpData.setBangFu(item);
        helpData.setKunNan(new HelpItem());
        helpData.setReXian(new HelpItem());
        helpData.setYiLiao(new HelpItem());

        bigData.setHelpData(helpData);

        //活动或者教育培训统计信息

        //惠普商城

        HPMallData hpMallData = new HPMallData();

        bigData.setHpMallData(hpMallData);

        //新媒体

        NewMediaData newMediaData = new NewMediaData();

        bigData.setNewMediaData(newMediaData);
        //组织建设

        OrganizationData organizationData = new OrganizationData();

        bigData.setOrganizationData(organizationData);

        //维权咨询
        SeekLegalAdviceData seekLegalAdviceData = new SeekLegalAdviceData();

        bigData.setSeekLegalAdviceData(seekLegalAdviceData);

        ActivityOrEducationData hd = new ActivityOrEducationData();

        List<JSONObject> dataList=new ArrayList<>();

        for(int i=0;i<3;i++){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",citys[i+1]);
            dataList.add(jsonObject);
        }
        hd.setDataListSort(dataList);

        bigData.setHd(hd);

        ActivityOrEducationData px = new ActivityOrEducationData();

        Calendar ca = Calendar.getInstance();

        List<JSONObject> dataList_= new ArrayList<>();

        for(int j=1;j<7;j++){

            JSONObject jsonObject = new JSONObject();
            ca.add(Calendar.MONTH,-1);
            jsonObject.put("name", DateUtil.getMonthCnName(ca));
            dataList_.add(jsonObject);
        }

        Collections.reverse(dataList_);

        px.setDataListSort(dataList_);

        bigData.setPx(px);

        return bigData;
    }




    /**
     * 将数据
     * @param object
     * @throws IllegalAccessException
     */
    protected void addRandom(Object object) throws IllegalAccessException {

        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        for (Field f : fields) {

            f.setAccessible(true);
            if (f.getType().getName().equals("java.lang.Integer")) {
                System.out.println(f.getName());
                Object o1 = f.get(object);
                Integer add=(o1==null?0:Integer.valueOf(o1.toString()));
                f.set(object, add);
            }
            if (f.getName().indexOf("dataListSort") > -1) {
                List<JSONObject>  list=  ( List<JSONObject>)f.get(object);
                for(JSONObject obj:list){
                    obj.put("times",0);
                    obj.put("members",0);
                }

            }
            if(f.getType().getName().contains("java")){
                continue;
            }else{
                addRandom(f.get(object));
            }

        }
    }

}
