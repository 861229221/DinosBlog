package com.dino.blog.exception;

import com.dino.blog.enums.AppHttpCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created 10-19-2022  2:40 PM
 * Author  Dino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException {
    private int code;
    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
