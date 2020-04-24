package cn.th.seckill.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Result<T> {
    private Integer code;
    private List msg=new ArrayList<>();
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data=data;
    }

    public Result<T> successResult(String msg){
        this.msg.add(msg);
        this.code=200;
        return this;
    }

    public Result<T> failResult(String msg){
        this.msg.add(msg);
        this.code=404;
        return this;
    }

    public Result<T> exceptionResult(String msg){
        this.msg.add(msg);
        this.code=500;
        return this;
    }

    public Result<T> exceptionResult(Collection msg){
        this.msg= (List) msg;
        this.code=500;
        return this;
    }
    public Result<T> successResult(Collection msg){
        this.msg= (List) msg;
        this.code=200;
        return this;
    }

    public Result<T> failResult(Collection msg){
        this.msg= (List) msg;
        this.code=404;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
