package com.dragon.fruit.dao.uuid;

import com.dragon.fruit.entity.po.fruit.RecomUserLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IRecomUserDao {

    /**
     * 保存推荐人信息
     * @param recomUserLogEntity
     */
    @Insert(" insert into recomUserLog(recomID,pid,phone,nickName,days,createTime,type) " +
            " values(#{recomID},#{pid},#{phone},#{nickName},#{days},#{createTime},#{type})")
    public void saveRecomUser(RecomUserLogEntity recomUserLogEntity);

    /**
     * 查询当前用户下的推荐信息
     * @param userID
     * @return
     */
    @Select("select * from recomUserLog where pid = #{userID} order by createTime desc")
    List<RecomUserLogEntity> list(Long userID);

}
