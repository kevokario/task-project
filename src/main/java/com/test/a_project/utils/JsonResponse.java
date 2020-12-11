/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.a_project.utils;

import java.util.HashMap;

/**
 *
 * @author litem
 */
public class JsonResponse {
 
    private int response_code;
    private String message;
    private HashMap hm;

    public JsonResponse() {
    }

    public JsonResponse(int response_code, String message, HashMap hm) {
        this.response_code = response_code;
        this.message = message;
        this.hm = hm;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap getHm() {
        return hm;
    }

    public void setHm(HashMap hm) {
        this.hm = hm;
    }

  
   
}
