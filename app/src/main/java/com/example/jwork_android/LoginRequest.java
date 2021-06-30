package com.example.jwork_android;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
Kelas BonusRequest menangani semua request yang berhubungan dengan proses login
 */
public class LoginRequest extends StringRequest {

    private static final String URL = "http://192.168.1.6:8080/jobseeker/login";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
