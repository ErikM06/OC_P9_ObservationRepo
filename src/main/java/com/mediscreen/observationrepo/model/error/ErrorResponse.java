package com.mediscreen.observationrepo.model.error;


import java.util.List;

public class ErrorResponse {

    private String msg;

    private List<String> details;

    public ErrorResponse(String msg, List<String> details) {
        this.msg = msg;
        this.details = details;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
