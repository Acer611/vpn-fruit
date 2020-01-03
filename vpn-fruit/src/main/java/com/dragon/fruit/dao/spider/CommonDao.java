package com.dragon.fruit.dao.spider;

import com.alibaba.fastjson.JSONObject;
import com.dragon.fruit.entity.po.spider.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonDao {


    /**
     * 插入职位信息
     * @param jobEntity
     */
    @Insert("insert into job_position(sourceId,title,money,areaName,areaId,cityName,location,provinceId,cleanType,companyId,type,jobRequired,taskType,jobDetail,lable,category,createTime,updateTime,publishTime) " +
            "values(#{sourceId},#{title},#{money},#{areaName},#{areaId},#{cityName},#{location},#{provinceId},#{cleanType},#{companyId},#{type},#{jobRequired},#{taskType},#{jobDetail},#{lable},#{category},#{createTime},#{updateTime},#{publishTime})")
    void saveJobInfo(JobEntity jobEntity);

    /**
     * 保存一级职位分类信息
     * @param objectData
     */
    @Insert("insert into job_title(title,status,classId,classDesc,classLevel) values(#{name},0,#{classificationId},#{classifyDesc},#{classLevel})")
    void saveJobTitle(JSONObject objectData);

    /**
     * 保存二级职位分类
     * @param secondData
     */
    @Insert("insert into job_title_detail(name,status,classificationId,classifyDesc,parentId,classLevel) " +
            "values(#{name},0,#{classificationId},#{classifyDesc},#{parentId},#{classLevel})")
    void saveJobTitleDetail(JSONObject secondData);

    /**
     * 保存城市信息
     * @param cityData
     */
    @Insert("insert into t_city(id,alipayCode,alipayName,code,getuiNum,name,provinceId,spellCode) values(#{id},#{alipayCode},#{alipayName},#{code},#{getuiNum},#{name},#{provinceId},#{spellCode})")
    void saveCity(JSONObject cityData);

    /**
     * 获取所有城市信息
     * @return
     */
    @Select("select * from t_city ")
    List<City> listCity();

    /**
     * 保存区县信息
     * @param areaData
     */
    @Insert("insert into t_county(areaName,areaId,townId) values(#{areaName},#{areaId},#{townId})")
    void saveCounty(JSONObject areaData);

    /**
     * 获取市下的区县信息
     * @param cityId
     * @return
     */
    @Select("SELECT * from t_county where townId=#{cityId}")
    List<County> listCountyList(Long cityId);

    /**
     * 查找城市信息
     * @param cityId
     * @return
     */
    @Select("select * from t_city where id=#{cityId} ")
    City findCityByID(Long cityId);

    /**
     * 查询职位信息是否存在
     * @param id
     * @return
     */
    @Select("select * from job_position where sourceId=#{id} ")
    JobEntity findJobIDbySourceId(long id);

    /**
     * 查询公司信息
     * @param companyId
     * @return
     */
    @Select("select * from t_company where companyId=#{companyId}")
    CompanyEntity findCompanyByID(Integer companyId);

    /**
     * 保存公司信息
     * @param newCompanyEntity
     */
    @Insert("insert into t_company(companyId,company,description,authentication,status,address,creditCode,groupCode,businessScop,createTime,updateTime) " +
            "values(#{companyId},#{company},#{description},#{authentication},#{status},#{address},#{creditCode},#{groupCode},#{businessScop},#{createTime},#{updateTime})")
    void saveCompany(CompanyEntity newCompanyEntity);
}
