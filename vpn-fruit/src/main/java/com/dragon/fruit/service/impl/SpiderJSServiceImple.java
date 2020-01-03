package com.dragon.fruit.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dragon.fruit.common.constant.SpiderConstant;
import com.dragon.fruit.common.utils.HttpClientUtils;
import com.dragon.fruit.dao.spider.CommonDao;
import com.dragon.fruit.entity.po.spider.*;
import com.dragon.fruit.service.ISpiderJSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program fruit
 * @description:
 * @author: Gaofei
 * @create: 2019-08-19
 */

@Service(value = "spiderJSService")
public class SpiderJSServiceImple implements ISpiderJSService {

    private static final Logger logger = LoggerFactory.getLogger(SpiderJSServiceImple.class);

    @Autowired
    private CommonDao commonDao;


    /**
     * 抓取职位分类信息
     */
    @Override
    public void spiderJobCategory() {
        JSONObject jsonObject = new JSONObject();
        jsonObject = HttpClientUtils.httpGet("https://api.qtshe.com/jobCenter/userApp/partJob/initList?appKey=QTSHE_ANDROID_USER&townId=1&version=4.32.2&versionCode=158&channel=7&downloadSource=7&deviceId=qts5386b8ee09e143b094b210dbfcc8d932&lon=121.575369&lat=25.084982&osVersionName=9&timestamp=1575953991907&sign=b1db20efffc313ce4241712f4827ac23&model=MI%208&brand=Xiaomi&product=Xiaomi");

        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray jobCategoryList = data.getJSONArray("classifications");
        //循环插入数据库
        for (Object jobcategory : jobCategoryList) {
            JSONObject objectData = (JSONObject) jobcategory;
            logger.info("分类 ：" + objectData.getString("name"));
            JSONArray detailCategoryList = objectData.getJSONArray("secondClassifications");
            //TODO 保存一级职位分类
            commonDao.saveJobTitle(objectData);
            for (Object secondCategory : detailCategoryList) {

                JSONObject secondData = (JSONObject) secondCategory;
                if (secondData.containsKey("parentId")) {
                    secondData.put("parentId", objectData.getLongValue("classificationId"));
                }
                //TODO 保存二级职位分类
                commonDao.saveJobTitleDetail(secondData);
            }
            // commonDao.saveProvince(city.toString());

        }
    }


    /**
     * 爬取城市信息
     */
    @Override
    public void spiderCity() {
        JSONObject jsonObject = new JSONObject();
        jsonObject = HttpClientUtils.httpGet("https://api.qtshe.com/misc/town/listTownOpened?appKey=QTSHE_ANDROID_USER&townId=73&version=4.32.2&versionCode=158&channel=7&downloadSource=7&deviceId=qts5386b8ee09e143b094b210dbfcc8d932&lon=121.575369&lat=25.084982&osVersionName=9&timestamp=1575954204599&sign=8274436e150b56df014bd18c2c346c56&model=MI%208&brand=Xiaomi&product=Xiaomi");
        if (jsonObject.containsKey("data")) {
            JSONArray jobCategoryList = jsonObject.getJSONArray("data");
            //循环插入城市信息到数据库
            for (Object city : jobCategoryList) {
                JSONObject cityData = (JSONObject) city;
                logger.info("市的名称" + cityData.getString("name"));
                commonDao.saveCity(cityData);
            }
        }


    }

    /**
     * 爬取区县信息
     */
    @Override
    public void spiderCounty() throws InterruptedException {

        List<City> cityList = commonDao.listCity();
        //循环抓取保存城市下区县信息
        for (City city : cityList) {
            if (city.getId() > 304) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = HttpClientUtils.httpGet(SpiderConstant.CITY_URL + city.getId());
                if (jsonObject.containsKey("data")) {
                    JSONArray jobCategoryList = jsonObject.getJSONObject("data").getJSONArray("areas");
                    for (Object county : jobCategoryList) {
                        JSONObject areaData = (JSONObject) county;
                        logger.info("区县名称 ：" + areaData.getString("areaName"));
                        if (areaData.getInteger("areaId") > 0) {
                            commonDao.saveCounty(areaData);
                        }

                    }

                }
            }

        }


    }


    /**
     * 按市份抓取职位信息
     *
     * @param cityId
     */

    @Override
    public void spiderJobList(Long cityId) throws InterruptedException {

        City cityInfo = commonDao.findCityByID(cityId);
        //获取城市下的区县信息
        List<County> countyList = commonDao.listCountyList(cityId);
        for (County county : countyList) {
            JSONObject jsonObject = new JSONObject();
           // if(county.getAreaId()>49){
                jsonObject = HttpClientUtils.httpGet(SpiderConstant.JOB_URL + cityId
                        + SpiderConstant.AREA + county.getAreaId());
                if (jsonObject.containsKey("data")) {

                    JSONArray jobList = jsonObject.getJSONObject("data").getJSONArray("results");
                    logger.info("==================================================");
                    logger.info("抓取" + cityInfo.getName() + "  " + county.getAreaName() + "  下的职位信息 " + jobList.size() + " 条 ");
                    handlerJobData(jobList, cityInfo, county);
                    System.out.println(jobList.size());

                }
                Thread.sleep(10000);
           // }

        }

        logger.info("******************************************************************");
        logger.info("【】【】【】【】【】【】】【】】【】】【】 城市的ID为" + cityId + " 抓取结束   ");
        logger.info("******************************************************************");

    }


    /**
     * 抓取的数据入库
     *
     * @param jobList
     */
    private void handlerJobData(JSONArray jobList, City city, County county) throws InterruptedException {

        int count = 0;
        //循环插入数据库
        for (Object object : jobList) {
            JSONObject dataObject = (JSONObject) object;
            //查询数据是否存在
            if(dataObject.containsKey("partJobId")){
                JobEntity jobInfo = commonDao.findJobIDbySourceId(Long.parseLong(dataObject.get("partJobId").toString()));
                if (jobInfo == null) {

                    //根据job详情
                    JSONObject resultObject = new JSONObject();
                    resultObject = HttpClientUtils.httpGet(SpiderConstant.DETAIL_URL + city.getId()
                            + SpiderConstant.PART_JOBID + dataObject.getString("partJobId"));

                    JSONObject detailJob = resultObject.getJSONObject("data");
                    //执行保存职位信息动作
                    JobEntity jobEntity = new JobEntity(detailJob, city, county);
                    /*if(jobEntity.getJobDetail().contains("\uD83D\uDCAA")){
                        jobEntity.setJobDetail("1、接待顾客的咨询，了解顾客的需求并达成销售； \n" +
                                "2、负责做好货品销售记录、盘点、账目核对等工作，按规定完成各项销售统计工作； \n" +
                                "3、完成商品的来货验收、上架陈列摆放、补货、退货、防损等日常营业工作； \n" +
                                "4、做好所负责区域的卫生清洁工作。\n" +
                                "\n" +
                                "【招募对象】\n" +
                                "1.年满18周岁，男女不限，高中或以上学历。\n" +
                                "2、能适应快速的工作节奏。\n" +
                                "3、有良好的沟通能力和团队合作精神。招募职位：\n");
                    }*/
                    commonDao.saveJobInfo(jobEntity);
                    //TODO 获取公司信息
                    JSONObject companyObject = new JSONObject();
                    JSONObject tempObject = detailJob.getJSONObject("company");
                    Long companyId = tempObject.getLong("companyId");
                    companyObject = HttpClientUtils.httpGet(SpiderConstant.COMPANY_URL + companyId );
                    handlerCompanyData(companyObject);
                    count = count + 1;

                }
            }

            Thread.sleep(10000);

        }
        logger.info("最终有效入库的的  " + city.getName() + "  " + county.getAreaName() + "  下的职位信息 " + count + " 条 ");
        logger.info("==================================================");

    }

    /**
     * 处理公司信息
     * @param companyResultObject
     */
    private void handlerCompanyData(JSONObject companyResultObject) {
        JSONObject companyObject = companyResultObject.getJSONObject("data");
        //查询数据是否已经存在
        CompanyEntity companyEntity = commonDao.findCompanyByID(companyObject.getInteger("companyId"));
        if(companyEntity==null){
            //保存公司信息
            CompanyEntity newCompanyEntity = new CompanyEntity(companyObject);
            commonDao.saveCompany(newCompanyEntity);

        }




    }


}

