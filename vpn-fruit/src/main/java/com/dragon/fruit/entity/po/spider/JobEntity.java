package com.dragon.fruit.entity.po.spider;

import com.alibaba.fastjson.JSONObject;
import com.dragon.fruit.common.constant.Constant;

import java.util.Date;

/**
 * 工作实体
 */
public class JobEntity {

    private Long sourceId;
    private String title;
    private String money;
    private String areaName;
    private Long areaId;
    private  String cityName;
    private  Long location;
    private String provinceName;
    private Integer provinceId;
    private String cleanType;
    private Long companyId;
    private String type;
    private String jobRequired;
    private Integer taskType;
    private String jobDetail;
    private Integer lable;
    private Integer category;
    private Date createTime;
    private Date updateTime;
    private Date publishTime;
    public JobEntity(){};

    public JobEntity(JSONObject dataObject, City city, County county) {
        this.sourceId = dataObject.getLong("partJobId");
        this.title = dataObject.getString("title");
        //薪水  12元/小时
        this.money = dataObject.getString("salary");
        this.areaName = county.getAreaName();
        this.areaId  = county.getAreaId();
        this.cityName = city.getName();
        this.location = city.getId();
        this.provinceId = city.getProvinceId();
        //结算方式 日结  周结
        JSONObject cleanTypeObject = dataObject.getJSONObject("clearingForm");
        this.cleanType = cleanTypeObject.getString("value");
        //兼职类型  长期短期
        JSONObject cycleTypeObject = dataObject.getJSONObject("cycleType");
        this.type = cycleTypeObject.getString("value");
        //公司ID
        JSONObject companyObject = dataObject.getJSONObject("company");
        this.companyId = companyObject.getLong("companyId");
        this.jobRequired = "无限制";
        // jobtitle  文员  家教
        JSONObject classificationObject = dataObject.getJSONObject("classification");
        this.taskType = classificationObject.getInteger("classificationId");
        this.jobDetail = dataObject.getString("jobDesc");

        this.lable=(int)(Math.random()*3)+1;
        int categoryIndex = (int)(Math.random()* Constant.JOBCATEGORY.length);
        this.category=Constant.JOBCATEGORY[categoryIndex];
        this.createTime = new Date();
        this.updateTime = new Date();
        this.publishTime = new Date();

    }




    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCleanType() {
        return cleanType;
    }

    public void setCleanType(String cleanType) {
        this.cleanType = cleanType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJobRequired() {
        return jobRequired;
    }

    public void setJobRequired(String jobRequired) {
        this.jobRequired = jobRequired;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    public Integer getLable() {
        return lable;
    }

    public void setLable(Integer lable) {
        this.lable = lable;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
