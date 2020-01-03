package com.dragon.fruit.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dragon.fruit.service.IRecomUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "推荐用户信息列表")
@RestController
@CrossOrigin
@RequestMapping("/api/recom")
public class RecomUserController {

    @Autowired
    IRecomUserService recomUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询当前用户下的可用下推荐信息
     *
     * @return
     */
    @ApiOperation(value = "查询当前用户推荐用户信息")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map list(@RequestHeader(required = false, value = "token") String token) {

        String userID = stringRedisTemplate.opsForValue().get("User_GetUserByToken:" + token);
        Map<String, Object> map = recomUserService.list(Long.parseLong(userID));

        return map;
    }
}
