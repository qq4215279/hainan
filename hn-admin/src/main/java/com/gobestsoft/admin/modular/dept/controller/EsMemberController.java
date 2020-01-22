package com.gobestsoft.admin.modular.dept.controller;

import com.gobestsoft.mamage.basic.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es/member")
public class EsMemberController extends BaseController {

//    @Autowired
//    EsMemberService esMemberService;
//
//    @RequestMapping("/list")
//    public Object list(@RequestParam(required = false) Integer deptId,
//                       @RequestParam(required = false)String memberRange){
//
//        Page page = defaultPage();
//
//        ObjectMap map = ObjectMap.getInstance();
//
//
//
//        if (ToolUtil.isEmpty(deptId)) {
//            deptId = getLoginUser().getDeptId();
//        }
//
//        if(ToolUtil.isEmpty(memberRange)){
//            memberRange = "01";
//        }
//        map.put("deptId",deptId);
//        map.put("memberRange",memberRange);
//        map.putRequest("unionName");
//        map.putRequest("name");
//        map.putRequest("nation");
//        map.putRequest("sex");
//
//        PageRequest pageRequest = new PageRequest(page.getCurrent()-1,page.getSize());
//
//        org.springframework.data.domain.Page<MemberModel> res = esMemberService.findList(map, pageRequest);
//
//        page.setRecords(res.getContent());
//
//        page.setTotal((int)res.getTotalElements());
//
//        return packForBT(page);
//
//    }
}
