package org.shop.api.service;

public class BaseBiz {

    /**
     * JPA页数从0开始
     * @param pageNo
     * @return
     */
    int fixPageIndex(int pageNo){
        return pageNo<=0?0:pageNo-1;
    }

}
