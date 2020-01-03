package com.dragon.fruit.entity.po.spider;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司信息实体
 */
@Data
public class CompanyEntity implements Serializable {

    private static final long serialVersionUID =1L;
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "来源库的ID")
    private Long companyId;
    @ApiModelProperty(value = "公司名称")
    private String company;
    @ApiModelProperty(value = "公司介绍")
    private String description;
    @ApiModelProperty(value = "是否认证  0 未认证  1 已认证")
    private Integer authentication;
    @ApiModelProperty(value = "所在地  关联城市表")
    private Integer domain;
    @ApiModelProperty(value = "企业类型")
    private String type;
    @ApiModelProperty(value = "经营状态")
    private String status;
    @ApiModelProperty(value = "注册资本")
    private String registeredCapital;
    @ApiModelProperty(value = "注册时间")
    private Date registerDate;
    @ApiModelProperty(value = "注册地址")
    private String address;
    @ApiModelProperty(value = "登记机关")
    private String registrationAuthority;
    @ApiModelProperty(value = "统一社会信用代码")
    private String creditCode;
    @ApiModelProperty(value = "组织机构代码")
    private String groupCode;
    @ApiModelProperty(value = "经营范围")
    private String businessScop;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    public CompanyEntity(){};
    public CompanyEntity(JSONObject companyObject) {

        this.companyId = companyObject.getLong("companyId");
        this.company = companyObject.getString("name");

        JSONObject companyEyeCheckInfoObject = companyObject.getJSONObject("companyEyeCheckInfo");
        if(null!=companyEyeCheckInfoObject){
            this.status = companyEyeCheckInfoObject.containsKey("regStatus")?companyEyeCheckInfoObject.getString("regStatus"):"";
            this.address = companyEyeCheckInfoObject.containsKey("regLocation")?companyEyeCheckInfoObject.getString("regLocation"):"";
            this.creditCode= companyEyeCheckInfoObject.containsKey("creditCode")?companyEyeCheckInfoObject.getString("creditCode"):"";
            this.groupCode = companyEyeCheckInfoObject.containsKey("orgNumber")?companyEyeCheckInfoObject.getString("orgNumber"):"";
            this.businessScop =companyEyeCheckInfoObject.containsKey("businessScope")? companyEyeCheckInfoObject.getString("businessScope"):"";
        }
        this.description = companyObject.getString("companyShareContent");
        this.authentication = 1;
        this.domain = 1;

        this.createTime = new Date();
        this.updateTime = new Date();
        //this.registerDate = companyEyeCheckInfoObject.getTimestamp("fromTime");


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Integer authentication) {
        this.authentication = authentication;
    }

    public Integer getDomain() {
        return domain;
    }

    public void setDomain(Integer domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationAuthority() {
        return registrationAuthority;
    }

    public void setRegistrationAuthority(String registrationAuthority) {
        this.registrationAuthority = registrationAuthority;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getBusinessScop() {
        return businessScop;
    }

    public void setBusinessScop(String businessScop) {
        this.businessScop = businessScop;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
