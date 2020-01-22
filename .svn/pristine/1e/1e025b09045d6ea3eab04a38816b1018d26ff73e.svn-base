package com.gobestsoft.admin.modular.schedule;

import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.mamage.moudle.dept.service.DeptMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class RehomeSchedule {

    @Autowired
    private DeptMemberMapper memberMapper;
    
    @Autowired
    private DeptMemberService memberService;

    @Scheduled(cron = "0 */5 * * * ?")
    public void getRehomeTicket(){
        memberMapper.getReHomeTicket();
    }

    @Scheduled(cron = "0 30 5 * * ?")
    public void cacheRealData(){
        memberService.cacheRealData();
    }
}
