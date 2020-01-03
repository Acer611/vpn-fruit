package com.dragon.fruit.service.impl;

import com.dragon.fruit.dao.uuid.IRecomUserDao;
import com.dragon.fruit.entity.po.fruit.RecomUserLogEntity;
import com.dragon.fruit.service.IRecomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value="recomUserService")
public class RecomUserServiceImpl implements IRecomUserService {

    @Autowired
    private IRecomUserDao recomUserDao;

    @Override
    public Map<String, Object> list(Long userID) {
        Map<String,Object> map = new HashMap<>();
        List<RecomUserLogEntity>  recomUserLogEntityList = recomUserDao.list(userID);
        recomUserLogEntityList = handlerRecomUser(recomUserLogEntityList);
        map.put("code","200");
        map.put("message","success");
        map.put("data",recomUserLogEntityList);
        return  map;
    }

    /**
     * 处理返回结果
     * @param recomUserLogEntityList
     * @return
     */
    private List<RecomUserLogEntity> handlerRecomUser(List<RecomUserLogEntity> recomUserLogEntityList) {
        for (RecomUserLogEntity recomUser:recomUserLogEntityList) {
            if(recomUser.getNickName()==null){
                recomUser.setNickName("");
            }
            recomUser.setPhone(recomUser.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        }

        return recomUserLogEntityList;
    }
}
