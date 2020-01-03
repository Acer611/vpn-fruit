package com.dragon.fruit.service;

import java.util.Map;

/**
 * 推荐用户的信息列表
 */
public interface IRecomUserService {
    /**
     * 获取当前用户下的推荐列表信息
     * @param userID
     * @return
     */
    Map<String,Object> list(Long userID);
}
