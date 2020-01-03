package com.dragon.fruit.controller;

import com.dragon.fruit.service.ISpiderJSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "爬虫接口")
@RestController
@RequestMapping("/spider")
public class SpiderController {
    private static final Logger logger = LoggerFactory.getLogger(SpiderController.class);


    @Autowired
    ISpiderJSService spiderJSService;

    @ApiOperation(value = "爬取城市信息")
    @ResponseBody
    @GetMapping("/spiderCity")
    public void spiderCity(){
        logger.info("爬取急城市信息数据.......");
        spiderJSService.spiderCity();

    }


    @ApiOperation(value = "爬取区县信息")
    @ResponseBody
    @GetMapping("/spiderCounty")
    public void spiderCounty() throws InterruptedException {
        logger.info("爬取区县信息数据.......");
        spiderJSService.spiderCounty();

    }




   @ApiOperation(value = "按市抓取职位列表信息")
    @ResponseBody
    @GetMapping("/spiderJobList")
    public void spiderJobList(Long cityId) throws InterruptedException {

        logger.info("爬取职位列表信息数据.......");
        for (long i = 337; i < 339; i++) {
                spiderJSService.spiderJobList(i);
           }


    }


    @ApiOperation(value = "抓取职位分类信息")
    @ResponseBody
    @GetMapping("/spiderJobCategory")
    public void spiderJobCategory() throws InterruptedException {
        logger.info("抓取职位分类信息数据.......");
        spiderJSService.spiderJobCategory();

    }


}
