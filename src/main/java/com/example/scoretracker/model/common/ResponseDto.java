package com.example.scoretracker.model.common;

import com.example.scoretracker.common.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> implements Serializable {

    private T data;

    private int status;

    private Integer error;

    private String msg;

    private Date currentTime = new Date();

    public ResponseDto(T data, int status, Integer error, String msg) {
        this.data = data;
        this.status = status;
        this.error = error;
        this.msg = msg;
    }

    public ResponseDto(int status, Integer error, String msg) {
        this.status = status;
        this.error = error;
        this.msg = msg;
    }

    public ResponseDto(T data) {
        this.data = data;
        this.status = 1;
        this.msg = Constant.MESSAGE.SUCCESS;
    }

    public ResponseDto(String msg) {
        this.status = 1;
        this.msg = msg;
    }

}