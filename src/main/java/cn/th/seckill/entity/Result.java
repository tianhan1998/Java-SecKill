package cn.th.seckill.entity;

import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 封装后端返回至前端对象
 * @param <T> 要返回的数据
 */
public class Result<T> {
    private Integer code;
    private List msg;
    private T data=null;
    private Object pageInfo;

    public Result(Integer code,String msg) {
        this.msg= Collections.singletonList(msg);
        this.code=code;
    }
    public Result(Integer code,String msg,T data){
        this.msg= Collections.singletonList(msg);
        this.code=code;
        this.data=data;
    }
    public Result(Integer code,Collection msg) {
        this.msg= (List) msg;
        this.code=code;
    }
    public Result(Integer code,Collection msg,T data){
        this.msg= (List) msg;
        this.code=code;
        this.data=data;
    }
    public static Result<Object> successResult(String msg,Object a){
        return new Result<>(200,msg,a);
    }
    public static Result<Object> successResult(String msg){
        return new Result<>(200,msg);
    }
    public static Result<Object> successResult(Collection msg,Object a){
        return new Result<>(200,msg,a);
    }
    public static Result<Object> successResult(Collection msg){
        return new Result<>(200,msg);
    }
    public static Result<Object> failResult(String msg,Object a){
        return new Result<>(404,msg,a);
    }
    public static Result<Object> failResult(String msg){
        return new Result<>(404,msg);
    }
    public static Result<Object> failResult(Collection msg,Object a){
        return new Result<>(404,msg,a);
    }
    public static Result<Object> failResult(Collection msg){
        return new Result<>(404,msg);
    }
    public static Result<Object> exceptionResult(String msg,Object a){
        return new Result<>(500,msg,a);
    }
    public static Result<Object> exceptionResult(String msg){
        return new Result<>(500,msg);
    }
    public static Result<Object> exceptionResult(Collection msg,Object a){
        return new Result<>(500,msg,a);
    }
    public static Result<Object> exceptionResult(Collection msg){
        return new Result<>(500,msg);
    }
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Object getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Object object) {
        this.pageInfo= object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List getMsg() {
        return msg;
    }

    public void setMsg(List msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
