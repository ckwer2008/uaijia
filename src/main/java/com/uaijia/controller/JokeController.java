package com.uaijia.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uaijia.config.ResultConstant;
import com.uaijia.core.util.CookieUtil;
import com.uaijia.core.util.WebUtil;
import com.uaijia.core.web.BaseController;
import com.uaijia.core.web.vo.AjaxResult;
import com.uaijia.entity.Joke;
import com.uaijia.joke.service.JokeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 段子
 * Created by chenkaiwen on 16/10/19.
 */
@Controller
@RequestMapping("/joke")
public class JokeController extends BaseController{

    @Resource
    private JokeService jokeService;

    /**
     * 段子列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<Joke> list(ModelMap model,HttpSession session,HttpServletRequest request,HttpServletResponse response){
        Set<String> pageSet = new HashSet();
        if(session.getAttribute("viewScope")==null ){
            String viewScope = CookieUtil.getCookie(request,"viewScope");
            if(StringUtils.isNotEmpty(viewScope) && viewScope.indexOf("##") > 0){
                String[] pages = viewScope.split("##");
                CollectionUtils.addAll(pageSet, pages);
            }
        }else{
            pageSet = (Set<String>)session.getAttribute("viewScope");
        }


        for (String servicePage:jokeService.getNewJokeMap().keySet()) {
            if(!pageSet.contains(servicePage)){
                pageSet.add(servicePage);

                //设置cookie
                StringBuffer viewScope = new StringBuffer();
                for (String pageStr:pageSet) {
                    viewScope.append(pageStr+"##");
                }
                CookieUtil.addCookie(response,"viewScope",viewScope.toString());

                session.setAttribute("viewScope",pageSet);
                return jokeService.getNewJokeMap().get(servicePage);
            }
        }

        int viewPage = 1;
        if(session.getAttribute("viewPage")==null){
            String libPageCookie = CookieUtil.getCookie(request,"viewPage");
            if(libPageCookie!=null && NumberUtils.isDigits(libPageCookie)) viewPage = Integer.parseInt(libPageCookie);
        }else {
            viewPage = (int)session.getAttribute("viewPage");
        }


        Page<Joke> pager = PageHelper.startPage(viewPage++, 20);
        jokeService.findAllLibJoke();

        session.setAttribute("viewPage",viewPage);
        CookieUtil.addCookie(response,"viewPage",viewPage+"");

        return pager.getResult();
    }

    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public AjaxResult detail(ModelMap model, @PathVariable("id") Long id){
        try{
            Joke joke = jokeService.find(id);
            return returnSuccess(joke);
        }catch (Exception e){
            e.printStackTrace();
            return returnFailue();
        }
    }


    @RequestMapping(value = "/handup/{id}")
    @ResponseBody
    public AjaxResult handup(ModelMap model, @PathVariable("id") Long id){
        try{
            jokeService.addDigg(id);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFailue();
        }
    }


    @RequestMapping(value = "/bury/{id}")
    @ResponseBody
    public AjaxResult bury(ModelMap model, @PathVariable("id") Long id){
        try{
            jokeService.addBury(id);
            return returnSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return returnFailue();
        }
    }




}
