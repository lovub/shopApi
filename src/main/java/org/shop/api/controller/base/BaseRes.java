package org.shop.api.controller.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
public class BaseRes<T> implements Serializable {

    private int retcode;

    private String retmsg;

    private T data;

}
