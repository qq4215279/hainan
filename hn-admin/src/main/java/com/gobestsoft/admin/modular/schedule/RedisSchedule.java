package com.gobestsoft.admin.modular.schedule;

import com.gobestsoft.common.modular.bigdata.service.JobService;
import com.gobestsoft.common.modular.dao.mapper.SysStatisticMapper;
import com.gobestsoft.common.modular.dao.model.SysStatisticPojo;
import com.gobestsoft.common.modular.dept.dao.MemberDao;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptOrganizationMapper;
import com.gobestsoft.common.modular.dept.model.*;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.core.reids.RedisConstants;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.mamage.config.member.MemberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@EnableScheduling
@Component
public class RedisSchedule {

    @Resource
    RedisUtils redisUtils;

    @Autowired
    SysStatisticMapper sysStatisticMapper;

    @Autowired
    DeptMemberMapper deptMemberMapper;

    @Autowired
    DeptOrganizationMapper deptOrganizationMapper;

    @Autowired
    BlackboardDao blackboardDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    JobService jobService;


    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateAppData(){
        //定时刷新大屏数据
        sysStatisticMapper.bigData_func();
    }

    /**
     * redis转存数据库
     */
    @Scheduled(cron = "59 59 23 * * ?")
    public void tranferAppData(){

        String create_time = DateUtil.getAllTime();

        insertStatistic(RedisConstants.APP_OPEN_COUNT,create_time);

    }

    //保存记录
    private void insertStatistic(String key,String create_time){
        String uuid = UUIDUtil.getUUID();
        Integer count = redisUtils.getInt(key);
        if(redisUtils.tryGetDistributedLock("Statistic_"+key,uuid,10)){

            SysStatisticPojo pojo = new SysStatisticPojo(key, count, create_time);
            sysStatisticMapper.insert(pojo);
            redisUtils.set(key,0);
            redisUtils.releaseLock("Statistic_"+key,uuid);
        }

    }

    /**
     * 定时生成会员卡号
     */
    @Scheduled(cron = "0/30 * * * * ?")
    private void updateMemberCard(){

        String uuid = UUIDUtil.getUUID();
        if(redisUtils.tryGetDistributedLock("updateMemberCard",uuid,120)){
            //查询1000条
            List<DeptMemberInfoEntity> infoEntities = deptMemberMapper.selectMemberStationCardNull();
            int deptId = -999 ;
            String unitDistric = "";
            if(infoEntities!=null){
                for(DeptMemberInfoEntity p:infoEntities){

                    String sex = p.getSex();
                    String deptName = p.getDept_name();
                    if(p.getDept_id()!=null && p.getDept_id()!= deptId){
                        deptId = p.getDept_id();
                        DeptOrganization organization = deptOrganizationMapper.selectByDeptId(deptId);
                        if(organization!=null){ unitDistric = organization.getUnitDistrict();}
                    }
                    String memberCardNo = MemberUtil.getMemberCardNo(sex, deptName, unitDistric);
                    deptMemberMapper.updateStationCard(p.getMember_id()+"",memberCardNo);

                }
            }
            redisUtils.releaseLock("updateMemberCard",uuid);
        }

    }


    /**
     * 更新 组织会员人数 字段（本组织、下级组织）
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateMemberCount(){
        //更新各组织会员人数
        memberDao.updateMemberCountTable1();
        memberDao.updateBindMemberCountTable1();
        memberDao.updateMemberCountTable2();
        /*List<Integer> deptIds = blackboardDao.getAllDeptId();
        deptIds.forEach(deptId -> {
            blackboardDao.adjustMemberCount(deptId);
        });*/
    }

    /**
     * 定时同步 php 端数据，并进行缓存 10 份 (key以时间区别)
     */
    @Scheduled(cron = "10 1 1 * * ?")
    public void limitCacheGspShopInfo(){
        jobService.limitCacheGspShopInfo();
    }
}
