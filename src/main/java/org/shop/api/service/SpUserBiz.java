package org.shop.api.service;

import org.shop.api.domain.SpUserDo;
import org.shop.api.service.dao.ISpUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SpUserBiz {

    @Autowired
    private ISpUserDao userDao;

    //根据openId查询用户
    public SpUserDo findByOpenId(String openId) {
        return userDao.findByOpenId(openId);
    }

    //平台内注册
    public long register(SpUserDo bean) {
        SpUserDo user = userDao.findByOpenId(bean.getOpenId());
        if (user != null) {
            return user.getUserId();
        }
        bean.setCreateDate(new Date());
        userDao.save(bean);
        return bean.getUserId();
    }

    //修改性别
    public void updateSex(SpUserDo bean) {
        userDao.updateSexByUserId(bean.getUserId(), bean.getSex(), new Date());
    }

    //修改微信号
    public void updateWechat(SpUserDo bean) {
        userDao.updateWechatByUserId(bean.getUserId(), bean.getWechat(), new Date());
    }

    //修改姓名
    public void updateName(SpUserDo bean) {
        userDao.updateNameByUserId(bean.getUserId(), bean.getRealName(), new Date());
    }

    //修改地区
    public void updateCityInfo(SpUserDo bean) {
        userDao.updateCityInfoByUserId(bean.getUserId(), bean.getProvince(), bean.getCity(), bean.getArea(), new Date());
    }

    /**
     * @param spUserId
     * @return
     */
    public SpUserDo getSpUserById(int spUserId) {
        return userDao.selectSpUserById(spUserId);
    }

}
