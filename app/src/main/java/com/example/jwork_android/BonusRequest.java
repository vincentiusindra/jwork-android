package com.example.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
Kelas BonusRequest menangani semua request yang berhubungan dengan penggunaan referral code
 */
public class BonusRequest extends StringRequest {

    private static final String URL = "http://192.168.1.6:8080/jobseeker/bonus/";
    private Map<String, String> params;

    public BonusRequest(String referralCode, Response.Listener<String> listener){
        super(Method.GET, URL+referralCode, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
