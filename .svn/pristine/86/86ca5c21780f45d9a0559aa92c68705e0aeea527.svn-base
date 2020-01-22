package com.gobestsoft.admin;

import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.core.reids.RedisUtils;
import com.gobestsoft.core.util.SpringContextHolder;
import com.gobestsoft.core.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan(basePackages = {"com.gobestsoft"})
@SpringBootApplication
public class HnAdminApplication extends WebMvcConfigurerAdapter implements ApplicationRunner {

//    @Autowired
//    EsMemberRepository esMemberRepository;
//
    @Autowired
    DeptMemberMapper memberMapper;


    public static void main(String[] args) {
        SpringApplication.run(HnAdminApplication.class, args);
        System.out.println("HnAdminApplication success");

    }
    @Override
    public void run(ApplicationArguments applicationArguments) {
        System.out.println("-------------->项目启动加载启动方法");
        RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
        String uuid = UUIDUtil.getUUID();
        if(redisUtils.tryGetDistributedLock("init_key_lock",uuid,360)){//redis分布式锁，防止多次初始化
            //所有初始化的方法写在下面
//            initMember();


        }
    }

    /**
     * 同步会员数据
     */
//    private void initMember(){
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//                Page page = new Page();
//
//                page.setSize(5000);
//
//                int i=1;
//                List<MemberModel> tempList = new ArrayList();
//                while (true){
//
//                    List<MemberModel> list = memberMapper.selectListEs(page);
//
//                    if(list==null || list.size()==0){
//                        break;
//                    }
//                    tempList.addAll(list);
//
//                    page.setCurrent(++i);
//                }
//                if(tempList.size()>0){
//                    esMemberRepository.save(tempList);
//                }
//
//
//            }
//        }).start();
//
//
//
//
//
//    }

}
