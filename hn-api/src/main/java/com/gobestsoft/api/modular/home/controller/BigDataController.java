package com.gobestsoft.api.modular.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.api.modular.basic.BasicRowBounds;
import com.gobestsoft.api.modular.home.controller.Service.BigDataService;
import com.gobestsoft.api.modular.home.controller.Service.DocileService;
import com.gobestsoft.common.constant.BigDataConstant;
import com.gobestsoft.common.constant.model.*;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping
@RestController
@Slf4j
public class BigDataController extends BaseController {

    @Autowired
    private BigDataService bigDataService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    BlackboardDao blackboardDao;
    @Autowired
    private DocileService docileService;

    @RequestMapping("/getBigDataV2")
    public BaseResult getBigData(@RequestParam(name = "target_id", required = false) String target_id, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        BaseResult baseResult = new BaseResult();

        baseResult.setCode(200);

        if (StringUtils.isEmpty(target_id)) {
            target_id = "0";
        }


        String big_data_key = BigDataConstant.bigData_hn + target_id;

        BigData bigData = (BigData) redisUtils.get(big_data_key);

        if (bigData == null) {
//            bigData = bigDataService.getBigData(target_id);
            bigData = create();
        }
//        try{
//
//            addRandom(bigData);
//        }catch (Exception e){
//            e.printStackTrace();
//            baseResult.setData(bigData);
//            return baseResult;
//        }
        redisUtils.set(big_data_key, bigData, 50l);

        baseResult.setData(bigData);

        return baseResult;
    }

    @RequestMapping("/getBigData")
    public BaseResult getBigDataV2(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        BaseResult baseResult = new BaseResult();

        baseResult.setCode(200);

        String big_data_key = BigDataConstant.bigData_hn;

        BigData bigData = (BigData) redisUtils.get(big_data_key);

        if (bigData == null) {
            String bigDataAdditionTime = (String) getAppConfig("bigDataAdditionTime");

            if (StringUtils.isEmpty(bigDataAdditionTime)) {
                return fail("建会统计丢失参数");
            }

            bigData = bigDataService.getBigData(bigDataAdditionTime);
            try {
                redisUtils.set(big_data_key, bigData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        baseResult.setData(bigData);

        return baseResult;
    }

    /**
     * 创建含有测试数据的实体 已作废
     *
     * @return
     */
    private BigData create() {

        BigData bigData = new BigData();
        //帮扶
        bigData.setHelpData(bigDataService.getKNBF_data());
        //活动或者教育培训统计信息
//        等待提供接口
        for (int i = 0; i < 2; i++) {
            ActivityOrEducationData bean = new ActivityOrEducationData();

            List<JSONObject> jsonObjectList = new ArrayList<>();

            if (i == 0) {

                for (int j = 0; j < 3; j++) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", "海口市");
                    obj.put("times", 0);
                    obj.put("members", 0);
                    jsonObjectList.add(obj);
                }
                bean.setDataListSort(jsonObjectList);
                bigData.setHd(bean);

            } else {
                Calendar calendar = Calendar.getInstance();
                for (int j = 0; j < 6; j++) {
                    JSONObject obj = new JSONObject();
                    obj.put("name", DateUtil.getMonthCnName(calendar));
                    obj.put("times", 0);
                    obj.put("members", 0);
                    jsonObjectList.add(obj);
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                }
                bean.setDataListSort(jsonObjectList);
                bigData.setPx(bean);

            }

        }
        //惠普商城
        HPMallData hpMallData = bigDataService.getPuHuiMallData();
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
        return bigData;
    }

    /**
     * 获取工会所有会员信息
     *
     * @param deptId
     * @param pageIndex
     * @param response
     * @return
     */
    @RequestMapping("/getBigData/getDeptMember")
    public @ResponseBody
    BaseResult getDeptMember(@RequestParam(name = "deptId", required = false) String deptId, Integer pageIndex, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        BaseResult baseResult = new BaseResult();
        List<Map<String, Object>> list = bigDataService.getDeptMember(new BasicRowBounds(pageIndex, 20), deptId);
        baseResult.setCode(200);
        baseResult.setData(list);
        return baseResult;
    }

    /**
     * 建会信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getBigData/getDeptOrgInfo")
    public Object getDeptOrgInfo(@RequestParam(required = false) final Integer deptId, HttpServletResponse response) {

        BaseResult baseResult = new BaseResult();
        baseResult.setCode(200);

        response.setHeader("Access-Control-Allow-Origin", "*");
        String key = "api_deptOrgInfo" + (deptId == null ? 1 : deptId);
        Map<String, Object> result = (Map<String, Object>) redisUtils.get(key);
        Date date = (Date) redisUtils.get("api_date_" + (deptId == null ? 1 : deptId));

        if (result == null || date == null) {
            result = init(deptId);
        } else if ((System.currentTimeMillis() - date.getTime()) / 1000 > 120) {
            log.info("超时更新");
            String uuid = UUIDUtil.getUUID();
            boolean b = redisUtils.tryGetDistributedLock("local_key_" + deptId, uuid, 10);
            if (b) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        init(deptId);
                    }
                }).start();
            }
        }
        baseResult.setData(result);
        return baseResult;
    }

    private Map<String, Object> init(Integer deptId) {
        Map<String, Object> result = new HashMap<>();
        result.put("memberStatistics", bigDataService.getmemberTop10Statistics(deptId == null ? 1 : deptId));
        result.put("deptCountGroup", bigDataService.getDeptCountGroup(deptId == null ? 1 : deptId));
        result.put("timeRangeMemberStatistics", bigDataService.getMemberCountByTimeRange(deptId == null ? 1 : deptId));
        result.put("memberInfoByCity", bigDataService.getMemberInfoByCity(deptId == null ? 1 : deptId));
        result.put("memberEducationStatistics", bigDataService.getMemberInfoByEducation(deptId == null ? 1 : deptId));
        result.put("memberAgeStatistics", bigDataService.getMemberInfoByAge(deptId == null ? 1 : deptId));
        result.put("memberNationStatistics", bigDataService.getMemberInfoByNation(deptId == null ? 1 : deptId));
        result.put("memberCountBySex", bigDataService.getMemberCountBySex(deptId == null ? 1 : deptId));
        result.put("totalMemberCount", bigDataService.getMemberCount(deptId == null ? 1 : deptId));
        result.put("totalDeptCount", bigDataService.getDeptCount(deptId == null ? 1 : deptId));
        redisUtils.set("api_deptOrgInfo" + (deptId == null ? 1 : deptId), result);
        redisUtils.set("api_date_" + (deptId == null ? 1 : deptId), new Date());
        return result;
    }

    @RequestMapping("/getBigData/getDeptMemberDocile")
    public Object getDeptMemberDocile() {
        return docileService.getMemberInfoByCity(1);
    }

    @RequestMapping("/getBigData/getGspShopInfo")
    public Object getGspShopInfo() {
        return bigDataService.getGspShopInfo();
    }
}
