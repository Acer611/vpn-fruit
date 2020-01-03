package com.dragon.fruit.entity.po.spider;

/**
 * 城市的实体信息
 */
public class City {

    private Long id;
    private String alipayCode;
    private String alipayName;
    private String code;
    private String getuiNum;
    private String name;
    private Integer provinceId;
    private String spellCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlipayCode() {
        return alipayCode;
    }

    public void setAlipayCode(String alipayCode) {
        this.alipayCode = alipayCode;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGetuiNum() {
        return getuiNum;
    }

    public void setGetuiNum(String getuiNum) {
        this.getuiNum = getuiNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }
}
