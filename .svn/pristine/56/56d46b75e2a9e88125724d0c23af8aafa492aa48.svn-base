package com.gobestsoft.common.modular.bigdata.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.constant.BigDataConstant;
import com.gobestsoft.common.modular.appuser.dao.AppUserDao;
import com.gobestsoft.common.modular.appuser.model.AppUserEntity;
import com.gobestsoft.common.modular.dept.mapper.BigDataMapper;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.reids.RedisCacheCoreFactory;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@EnableScheduling
public class JobService {

    // 普惠活动大屏数据 缓存数量限制
    private static final int Gsp_Active_Cache_Limit_Num = 10;

    @Resource
    private BigDataMapper bigDataMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private AppUserDao appUserDao;

    @Resource
    RedisUtils redisUtils;

    @Resource
    RedisCacheCoreFactory cacheFactory;

    /**
     * 获取普惠活动大屏数据
     */
    public Map<String, Object> getGspShopInfoMysql() {
        Map<String, Object> info = new HashMap<>();

        // 活动参与人数
        info.put("joinActivityNum", bigDataMapper.getGspShopJoinActivityNum());

        // 组织活动排行
        List<String> orgIds = bigDataMapper.getGspShopAllOrgIds();
        List<String> orgList = new ArrayList<>();
        orgIds.forEach( orgId -> {
            for (String id : orgId.split(",")) {
                if(ToolUtil.isNotEmpty(id)){
                    orgList.add(id);
                }
            }
        });
        Map<String, Integer> orgNode = ToolUtil.frequencyOfListElements(orgList);

        // 查询所有二级工会
        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper.eq("level", 2);
        List<Dept> depts = deptMapper.selectList(wrapper);

        Map<String, Integer> orgIdNum = new HashMap<>();
        for(Dept parent : depts){
            List<String> sons = deptMapper.selectSonDeptIdByPid(parent.getId());
            int count = 0;
            for(String key : orgNode.keySet()){
                if(ToolUtil.isNotEmpty(sons)){
                    if(sons.contains(key)){
                        count += orgNode.get(key);
                    }
                }
            }
            orgIdNum.put(parent.getSimplename(), count);
        }
        List<Map<String, Integer>> orgActivityOrder = new LinkedList<>();
        ToolUtil.sortMap(orgIdNum).forEach( (key,index) -> {
            Map<String, Integer> item = new HashMap<>();
            item.put(key, orgIdNum.get(key));
            orgActivityOrder.add(item);
        });
        info.put("orgActivityOrder", orgActivityOrder);

        // 用户参与活动
        List<Map<String, Object>> memberList = bigDataMapper.getGspShopJoinMembers();
        memberList.forEach( item -> {
            String time = DateUtil.format(new Date((Long) item.get("time") * 1000), "yyyy-MM-dd HH:mm");
            item.put("time", time);
            String appUid = (String) item.get("appUid");
            if(appUid.startsWith("app_")){
                appUid = appUid.substring(4);
            }
            AppUserEntity user = appUserDao.getAppUserByAuid(appUid);
            if(ToolUtil.isNotEmpty(user)){
                item.put("memberName", ToolUtil.isNotEmpty(user.getReal_name()) ? user.getReal_name() : user.getNickname());
                item.put("sex", user.getSex());
            }else{
                item.put("memberName", "会员");
                item.put("sex", 1);
            }
        });

        info.put("memberList", memberList);

        // 各种数据： 依次为 总金额、总点击量、商品数量、订单数量
        List<Object> nums = new LinkedList<>();
        nums.add(bigDataMapper.getGspShopTotalAllFree());
        int num4 = bigDataMapper.getGspShopAllOrderNum();
        nums.add(num4 * 6);
        nums.add(bigDataMapper.getGspShopAllItemNum());
        nums.add(num4);
        info.put("nums", nums);

        // 普惠活动
        Map<String, Object> activities = new HashMap<>();
        List<Map<String, Object>> all = new LinkedList<>();
        Long now = System.currentTimeMillis() / 1000;
        // 普惠活动 进行中
        List<Map<String, Object>> ing = new LinkedList<>();
        LinkedList<String> actives = bigDataMapper.getGspShopIngActives(now);
        actives.forEach( item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("activeName", item);
            map.put("status", 0);
            ing.add(map);
        });
        activities.put("ingActive", ing);
        // 普惠活动 已结束
        List<Map<String, Object>> end = new LinkedList<>();
        actives = bigDataMapper.getGspShopEndActives(now);
        actives.forEach( item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("activeName", item);
            map.put("status", 1);
            end.add(map);
        });
        activities.put("endActive", end);

        all.addAll(ing);
        all.addAll(end);
        activities.put("allActive", all);

        info.put("activities", activities);

        return info;
    }

    /**
     * 定时同步 php 端数据，并进行缓存 10 份 (key以时间区别)
     */
    public void limitCacheGspShopInfo(){
        Map<String, Object> map = this.getGspShopInfoMysql();
        Long now = Long.parseLong(DateUtil.getAllTime());
        cacheFactory.cacheModel(BigDataConstant.bigData_gsp_active + now, map);

        Set<String> keys = redisUtils.getKeyLike(BigDataConstant.bigData_gsp_active);
        // 只保留10份
        if(ToolUtil.isNotEmpty(keys) && keys.size() > Gsp_Active_Cache_Limit_Num){
            int i=0;
            for (String key : keys) {
                i++;
                if(i > Gsp_Active_Cache_Limit_Num){
                    redisUtils.remove(key);
                }
            }
        }
    }
}
