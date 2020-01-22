package com.gobestsoft.mamage.moudle.system.service;

import com.gobestsoft.common.modular.dao.mapper.DeptDelRecordMapper;
import com.gobestsoft.common.modular.dao.mapper.MemberDelRecordMapper;
import com.gobestsoft.common.modular.dao.model.DeptDelRecordPojo;
import com.gobestsoft.common.modular.dao.model.MemberDelRecordPojo;
import com.gobestsoft.common.modular.dept.dao.MemberDao;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptOrganizationMapper;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.DeptMemberInfoEntity;
import com.gobestsoft.common.modular.dept.model.DeptOrganization;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.exception.BizExceptionEnum;
import com.gobestsoft.mamage.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 删除重复的会员或者组织
 */
@Service
public class RepeatDataService {


    @Autowired
    MemberDelRecordMapper memberDelRecordMapper;

    @Autowired
    DeptMemberMapper deptMemberMapper;

    @Autowired
    PersonInfoMapper personInfoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DeptDelRecordMapper deptDelRecordMapper;

    @Autowired
    MemberDao memberDao;

    @Autowired
    DeptOrganizationMapper deptOrganizationMapper;

    @Autowired
    DeptMapper deptMapper;

    /**
     * 删除会员
     * @param person_id 会员流水号
     * @param uid 操作人员流水号
     */
    public int delMember(Integer person_id,String uid){
        DeptMemberInfoEntity deptMemberInfoEntity = deptMemberMapper.getMemberInfo4Remove(person_id);
        //判空
        if(deptMemberInfoEntity==null)throw new BusinessException(BizExceptionEnum.DB_RESOURCE_NULL);

        if(deptMemberInfoEntity.getIsBind()!=null&& deptMemberInfoEntity.getIsBind()==1){
            throw  new RuntimeException("会员已被认证，暂时无法删除");
        }
        MemberDelRecordPojo pojo = new MemberDelRecordPojo();
        pojo.setDept_id(deptMemberInfoEntity.getDept_id());
        pojo.setDept_name(deptMemberInfoEntity.getDept_name());
        pojo.setMobile(deptMemberInfoEntity.getMobile());
        pojo.setName(deptMemberInfoEntity.getName());
        pojo.setOperation_user(uid);
        pojo.setCertificate_num(deptMemberInfoEntity.getCertificate_num());
        pojo.setOperation_time(DateUtil.getAllTime());
        //插入删除记录
        memberDelRecordMapper.insert(pojo);
        //删除旧的会员信息
        Integer deleteById = personInfoMapper.deleteById(person_id);
        if(deleteById==0)throw new RuntimeException("数据变化,删除失败");
        deptMemberMapper.deleteById(deptMemberInfoEntity.getDept_id());
        new Thread(new Runnable() {
            @Override
            public void run() {
                memberDao.updateMemberCountTable1();
                memberDao.updateBindMemberCountTable1();
                memberDao.updateMemberCountTable2();
            }
        }).start();
        return deleteById;

    }


    /**
     * 删除未激活且注册账号
     */
    public Integer delOrganize(String account, String uid){
        String time = DateUtil.getAllTime();
        User user = new User();
        user.setAccount(account);

        User user1 = userMapper.selectOne(user);
        //判空
        if(user1==null)throw new BusinessException(BizExceptionEnum.DB_RESOURCE_NULL);
        DeptDelRecordPojo deptDelRecordPojo = new DeptDelRecordPojo();
        deptDelRecordPojo.setDept_id(user1.getDeptid() == null ? 0 : user1.getDeptid());
        deptDelRecordPojo.setAccount(account);
        deptDelRecordPojo.setOperation_time(time);
        deptDelRecordPojo.setOperation_user(uid);
        deptDelRecordMapper.insert(deptDelRecordPojo);

        Integer cnt = userMapper.delete(account);
        return cnt;
    }

    /**
     * 删除重复组织
     */
    public Integer delOrganizeCode(String deptId, String uid){
        int cnt = 1;
        String time = DateUtil.getAllTime();
        DeptOrganization deptOrganization = new DeptOrganization();
        deptOrganization.setDeptId(Integer.valueOf(deptId));
        DeptOrganization deptOrganization1 = deptOrganizationMapper.selectOne(deptOrganization);
        //判空
        if(deptOrganization1==null)throw new BusinessException(BizExceptionEnum.DB_RESOURCE_NULL);
        //判断是否激活
        User user = new User();
        user.setAccount(deptOrganization1.getUnionName());
        User user1 = userMapper.selectOne(user);
        if(user1 != null){
            cnt = 2;
            return cnt;
        }

        Dept dept = deptMapper.selectById(deptId);
        if(dept != null){
            deptMapper.deleteById(Integer.valueOf(deptId));
            deptOrganizationMapper.deleteById(deptOrganization1.getId());
        } else {
            deptOrganizationMapper.deleteById(deptOrganization1.getId());
        }

        DeptDelRecordPojo deptDelRecordPojo = new DeptDelRecordPojo();
        deptDelRecordPojo.setDept_id(Integer.valueOf(deptId));
        deptDelRecordPojo.setAccount(deptOrganization1.getUnionName());
        deptDelRecordPojo.setOperation_time(time);
        deptDelRecordPojo.setOperation_user(uid);
        deptDelRecordMapper.insert(deptDelRecordPojo);

        return cnt;
    }

}
