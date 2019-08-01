package org.shop.api.exception;

import org.shop.api.controller.base.BaseRes;
import org.shop.api.controller.base.ResMsgEnum;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SysExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseRes errorHandler(Exception ex) {
        ex.printStackTrace();
        BaseRes resp = new BaseRes();
        resp.setRetcode(ResMsgEnum.FAILURE.CODE);
        resp.setRetmsg(ResMsgEnum.FAILURE.DESC);
        return resp;
    }

}
