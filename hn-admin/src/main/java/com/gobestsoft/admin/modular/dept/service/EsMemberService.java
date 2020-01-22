package com.gobestsoft.admin.modular.dept.service;

import com.gobestsoft.core.util.ToolUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EsMemberService {

//    @Resource
//    EsMemberRepository esMemberRepository;
//
//    public org.springframework.data.domain.Page<MemberModel> findList(ObjectMap map, PageRequest pageRequest){
//
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//
//        if(hasValue("name",map)){
//            builder.withQuery(QueryBuilders.fuzzyQuery("name",map.getStr("name")));
//        }
//        if(hasValue("memberCard",map)){
//            builder.withQuery(QueryBuilders.fuzzyQuery("memberCard",map.getStr("memberCard")));
//        }
//        if(hasValue("sex",map)){
//            builder.withQuery(QueryBuilders.fuzzyQuery("sex",map.getStr("sex")));
//        }
//        if(hasValue("nation",map)){
//            builder.withQuery(QueryBuilders.fuzzyQuery("nation",map.getStr("nation")));
//        }
//        if(hasValue("unionName",map)){
//            builder.withQuery(QueryBuilders.fuzzyQuery("unionName",map.getStr("unionName")));
//        }
//        if(hasValue("memberRange",map)&& "01".equals(map.getStr("memberRange"))){
//
//            builder.withQuery(QueryBuilders.matchQuery("deptId",map.getStr("deptId")));
//        }
//        if(hasValue("memberRange",map)&& "02".equals(map.getStr("memberRange"))){
//
//            builder.withQuery(QueryBuilders.wildcardQuery("pids","*,"+map.getStr("deptId")+"*"));
//        }
//        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
//
//        //分页查询
//        org.springframework.data.domain.Page<MemberModel> res = esMemberRepository.search(builder.withPageable(pageRequest).build());
//
//        return res;
//
//    }

    private boolean hasValue(String key, Map map){

        return map.containsKey(key)&& ToolUtil.isNotEmpty(map.get(key));

    }

}
