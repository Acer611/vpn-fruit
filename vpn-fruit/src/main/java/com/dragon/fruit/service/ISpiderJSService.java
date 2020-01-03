package com.dragon.fruit.service;


/**
 * @dec 爬虫的业务逻辑层接口
 * @author  Gaofei
 * @date 2019-08-19
 *
 */
public interface ISpiderJSService {




    /**
     * 爬取城市信息
     */
    void spiderCity();

    /**
     * 爬取区县信息
     */
    void spiderCounty() throws InterruptedException;

    /**
     * 按市抓取职位列表信息
     * @param cityId
     */
    void spiderJobList(Long cityId) throws InterruptedException;

    /**
     * 抓取职位分类信息
     */
    void spiderJobCategory();
}
