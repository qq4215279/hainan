package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.modular.dao.model.SpiderArticlePojo;
import com.gobestsoft.common.modular.system.dao.SpiderDao;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 爬网管理
 */
@Controller
@RequestMapping("/spider")
public class SpiderController extends BaseController {

    private static String PREFIX = "/system/spider/";

    private final static String HOST = "http://www.hnszgh.org";

    @Resource
    SpiderDao spiderDao;


    /**
     * 跳转到列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "index";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String type) {
        Page<SpiderArticlePojo> page = defaultPage();
        Wrapper<SpiderArticlePojo> wrapper = new EntityWrapper<>();
        wrapper.eq(ToolUtil.isNotEmpty(type),"type",type).orderBy("create_time",false);
        List<SpiderArticlePojo> list = spiderDao.selectPage(page, wrapper);
        page.setRecords(list);
        return super.packForBT(page);
    }

    /**
     * 信息采集
     */
    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ResponseBody
    public Tip get(@RequestParam(required = false) Integer pageNum, Integer type) {
        String _url  = HOST + "/web/default/menu.jsp?menuId="+type;
        int allPageNum = getPageNum(_url);
        if(pageNum > allPageNum){
            return fail("该类型下只有"+allPageNum+"页数据");
        }

        if(pageNum == 0){
            for(int i=1; i<=pageNum; i++){
                String url = _url + "&pageNo="+i;
                addArticles(url);
            }
        }else{
            String url = _url + "&pageNo="+pageNum;
            addArticles(url);
        }

        return success();
    }

    private Element getBodyElement(String url){
        Element body;
        try {
            body = Jsoup.connect(url).timeout(3000).get().body();
        } catch (IOException e) {
            return null;
        }
        return body;
    }

    private int getPageNum(String url){
        int num = 1;
        try {
            Element main = getBodyElement(url).getElementById("sec_main");
            Element list7 = main.getElementsByClass("manu").first();
            String href = list7.getElementsByTag("a").last().attr("href");
            num = Integer.parseInt(href.substring(href.length()-1, href.length()));
        }catch (Exception e){

        }
        return num;
    }

    private void addArticles(String url){
        Element main = getBodyElement(url).getElementById("sec_main");
        Element list7 = main.getElementsByClass("List7").first();
        String hostTitle = main.getElementsByClass("sec_title").first().text();
        Elements lis = list7.getElementsByTag("li");
        for(Element li : lis){
            String createTime = li.getElementsByTag("span").first().text();
            String href = li.getElementsByTag("a").first().attr("href");
            String link = HOST + href;
            Element articleMain = getBodyElement(link).getElementById("sec_main");
            Element neirong = articleMain.getElementsByClass("neirong").first();
            String title = neirong.getElementsByTag("h4").first().text();
            String content = neirong.getElementsByClass("Article").first().html();
            if(content.contains("src=\"/image")){
                content = content.replaceAll("src=\"/image", "src=\""+HOST+"/image");
            }
            String desc = neirong.getElementsByClass("laiyuan").first().html();

            SpiderArticlePojo pojo = new SpiderArticlePojo();
            pojo.setTitle(title);
            pojo.setContent(content);
            pojo.setDesc(desc);
            pojo.setLink(link);
            pojo.setType(hostTitle);
            pojo.setCreateTime(createTime);
            if(link.contains("video")){
                String scriptText = neirong.getElementsByClass("sec2text").first().getElementsByTag("script").html();
                String key = "s1.addVariable(\"file\",\"";
                int index1 = scriptText.indexOf(key) + key.length();
                int index2 = scriptText.indexOf(".flv") + 4;
                String videoLink = scriptText.substring(index1, index2);
                pojo.setMedia(videoLink);
            }

            Integer id = spiderDao.getIdByPojo(pojo);

            if(ToolUtil.isEmpty(id)){
                spiderDao.insert(pojo);
            }else{
                pojo.setId(id);
                spiderDao.updateById(pojo);
            }
        }
    }

}
