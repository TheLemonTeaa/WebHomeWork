package edu.hitwh.homework.pojo;

public class Result {
    private int code;
    private String msg;
    private Object data;
    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(1, "success", data);
    }
    public static Result success() {
        return new Result(1, "success", null);
    }
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }
}
