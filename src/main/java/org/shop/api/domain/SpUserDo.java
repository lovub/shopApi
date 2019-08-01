package org.shop.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="sp_user")
public class SpUserDo extends BaseDo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    /**
     * 微信唯一openid
     */
    @Column(name="openid")
    private String openId;

    /**
     * 微信昵称
     */
    @Column(name="nickname")
    private String nickName;

    /**
     * 姓名
     */
    @Column(name="realname")
    private String realName;

    /**
     * 微信号
     */
    @Column(name="wechat")
    private String wechat;

    /**
     * 用户头像
     */
    @Column(name="headimgurl")
    private String headImgUrl;

    /**
     * 生日
     */
    @Column(name="birth")
    private String birth;

    /**
     * 性别
     */
    @Column(name="sex")
    private String sex;

    /**
     * 手机号
     */
    @Column(name="phone")
    private String phone;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    /**
     * 区
     */
    @Column(name="area")
    private String area;

    @Column(name="create_time")
    private Date createDate;

    @Column(name="update_time")
    private Date updateTime;

    /**
     * 1-启用 0-禁用
     */
    @Column(name="state")
    private int state;

}
