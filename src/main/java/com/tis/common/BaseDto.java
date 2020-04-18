package com.tis.common;

import lombok.Data;

@Data
public class BaseDto<D> {
    private D data;
    private boolean success;
    private String errorMsg;

    private BaseDto(D data, boolean success) {
        this.data = data;
        this.success = success;
    }

    private BaseDto(String errorMsg,boolean success) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public static <D> BaseDto<D> success(D data){
        return new BaseDto<>(data,true);
    }

    public static  BaseDto failed(String errorMsg)
    {
        return new BaseDto(errorMsg,false);
    }
}
