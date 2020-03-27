package com.imooc.controller;

import com.imooc.service.StuService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/25 9:54
 */
//@Controller
@RestController
//忽略这个 API 类
@ApiIgnore
public class HelloController {
    final static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private StuService stuService;

    @GetMapping("hello")
    public Object hello(){
        logger.info("info -- 李艳茹是🐖");
        logger.debug("debug -- 李艳茹是🐖");
        logger.warn("warn -- 李艳茹是🐖");
        logger.error("error -- 李艳茹是🐖");
        return "hello world";
    }


    @GetMapping("/getStu")
    public Object getStuInfo(int id) {
        return stuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu() {
        stuService.saveStu();
        return "OK";
    }

    @PostMapping("/updateStu")
    public Object updateStu(int id) {
        stuService.updateStu(id);
        return "OK";
    }

    @PostMapping("/deleteStu")
    public Object deleteStu(int id) {
        stuService.deleteStu(id);
        return "OK";
    }

    //测试session
    @ApiOperation(value = "测试Session", notes = "测试Session", httpMethod = "POST")
    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "changxueyi ONE");
        //设置过期时间
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
   //     session.removeAttribute("userInfo");
        return "ok";
    }



}