package org.shop.api.controller;

import org.shop.api.controller.base.BaseRes;
import org.shop.api.controller.base.ResMsgEnum;
import com.google.gson.Gson;

public class BaseController {

    public <T>BaseRes<T> buildRes(ResMsgEnum code, T data){
        BaseRes res = new BaseRes();
        res.setRetmsg(code.DESC);
        res.setRetcode(code.CODE);
        if(data!=null){
            res.setData(data);
        }
        return res;
    }

    public <T> BaseRes<T> toRes(ResMsgEnum code){
        return buildRes(code,null);
    }

    public <T> BaseRes<T> toRes(ResMsgEnum code, T data){
        return buildRes(code,data);
    }

}
