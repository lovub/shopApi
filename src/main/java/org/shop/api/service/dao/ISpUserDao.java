package org.shop.api.service.dao;

import org.shop.api.domain.SpUserDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ISpUserDao extends JpaRepository<SpUserDo, Long> {

    SpUserDo findByOpenId(String openId);

    @Modifying
    @Query(value = "update SpUserDo p set p.realName = ?2,p.updateTime = ?3  where p.userId = ?1")
    void updateNameByUserId(long userId, String realName, Date date);

    @Modifying
    @Query(value = "update SpUserDo p set p.sex = ?2,p.updateTime = ?3 where p.userId = ?1")
    void updateSexByUserId(long userId, String sex,Date date);

    @Modifying
    @Query(value = "update SpUserDo p set p.wechat = ?2,p.updateTime = ?3 where p.userId = ?1")
    void updateWechatByUserId(long userId, String wechat,Date date);

    @Modifying
    @Query(value = "update SpUserDo p set p.province = ?2,p.city = ?3,p.area = ?4,p.updateTime = ?5 where p.userId = ?1")
    void updateCityInfoByUserId(long userId, String province, String city, String area,Date date);

}
