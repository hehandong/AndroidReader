package com.hehandong.androidreader.Retrofit.module;

import java.io.Serializable;

/**
 * @Author dong
 * @Date 2019-06-04 17:44
 * @Description TODO
 * GitHub：https://github.com/hehandong
 * Email：hehandong@qq.com
 * @Version 1.0
 */
public class WanBaseModel<T> implements Serializable {
    public T data;
    public int errorCode;
    public String errorMsg;
}
