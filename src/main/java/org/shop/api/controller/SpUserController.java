package org.shop.api.controller;

import org.shop.api.controller.base.BaseRes;
import org.shop.api.controller.base.ResMsgEnum;
import org.shop.api.domain.SpUserDo;
import org.shop.api.service.SpUserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SpUserController extends BaseController {

    @Autowired
    private SpUserBiz userBiz;

    @GetMapping("/register")
    public BaseRes register(SpUserDo bean) {
        if (StringUtils.isEmpty(bean.getOpenId())) {
            return toRes(ResMsgEnum.OPEN_ID_MISSING);
        }
        return toRes(ResMsgEnum.SUCCESS, userBiz.register(bean));
    }

    @GetMapping("/editSex")
    public BaseRes modifySex(SpUserDo bean) {
        userBiz.updateSex(bean);
        return toRes(ResMsgEnum.SUCCESS);
    }

    @GetMapping("/editName")
    public BaseRes modifyName(SpUserDo bean) {
        userBiz.updateName(bean);
        return toRes(ResMsgEnum.SUCCESS);
    }

    @GetMapping("/editCityInfo")
    public BaseRes modifyCityInfo(SpUserDo bean) {
        userBiz.updateCityInfo(bean);
        return toRes(ResMsgEnum.SUCCESS);
    }

    @GetMapping("/editWechat")
    public BaseRes modifyWechat(SpUserDo bean) {
        userBiz.updateWechat(bean);
        return toRes(ResMsgEnum.SUCCESS);
    }

    /**
     * 查询某个spUser
     *
     * @param spUserId
     * @return
     */
    @GetMapping("/getSpUserById/{spUserId}")
    @ResponseBody
    public SpUserDo getSpUserById(@PathVariable int spUserId) {
        return userBiz.getSpUserById(spUserId);
    }

}
