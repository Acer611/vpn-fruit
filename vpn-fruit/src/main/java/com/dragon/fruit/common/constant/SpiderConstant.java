package com.dragon.fruit.common.constant;

/**
 * 爬虫常量信息
 */
public class SpiderConstant {

    /**
     * 获取所有省份的URL
     */
    public  static final String  PROVINCE_URL = "http://api.yh.jianzhimao.com/api/area?province=";

    /**
     * 区县信息
     */
    public  static final String  AREA = "&areaIds=";
    /**
     * 职位信息排序规则
     */
    public  static final String  ORDER = "&order=20";

    /**
     * 职位信息
     */
    public  static final String  JOB_URL = "https://api.qtshe.com/jobCenter/userApp/partJob/list?latitude=25.084865&pageSize=50&pageNum=1&longitude=121.575396&appKey=QTSHE_ANDROID_USER&version=4.32.2&versionCode=158&channel=7&downloadSource=7&deviceId=qts5386b8ee09e143b094b210dbfcc8d932&lon=121.575396&lat=25.084865&osVersionName=9&timestamp=1575952829163&sign=5ae989bc875ba6d4772825d3760cb6b5&model=MI%208&brand=Xiaomi&product=Xiaomi&townId=";

    /**
     * 省份信息
     */
    public  static final String  PROVINCE = "&province=";

    /**
     * 城市的URL
     */
    public  static final String  CITY_URL = "https://api.qtshe.com/jobCenter/userApp/partJob/initList?appKey=QTSHE_ANDROID_USER&version=4.32.2&versionCode=158&channel=7&downloadSource=7&deviceId=qts5386b8ee09e143b094b210dbfcc8d932&lon=121.575518&lat=25.084795&osVersionName=9&timestamp=1575961768168&sign=329e0fb335bc5fc80e40a57eced97b5d&model=MI%208&brand=Xiaomi&product=Xiaomi&townId=";


    /**
     * 职位详情URL
     */
    public static final String DETAIL_URL ="https://api.qtshe.com/jobCenter/userApp/partJob/v2/detail?appKey=QTSHE_ANDROID_USER&version=4.32.2&versionCode=158&channel=7&downloadSource=7&deviceId=qts5386b8ee09e143b094b210dbfcc8d932&osVersionName=9&timestamp=1575952925446&sign=c177e2ff71856468ce18a7739d220968&model=MI%208&brand=Xiaomi&product=Xiaomi&townId=";

    /**
     * 职位ID
     */
    public static final String PART_JOBID="&partJobId=" ;

    /**
     * 公司信息URL
     */
    public static  final  String COMPANY_URL = "https://api.qtshe.com/accountCenter/company/content/info/user?appKey=QTSHE_ANDROID_USER&townId=73&version=4.32.2&versionCode=158&channel=7&downloadSource=7&deviceId=qts5386b8ee09e143b094b210dbfcc8d932&osVersionName=9&timestamp=1575959000026&sign=9e3541a12643d38cb9e1df5b344545be&companyId=";
}
