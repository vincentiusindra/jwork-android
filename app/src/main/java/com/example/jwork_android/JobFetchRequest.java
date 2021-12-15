package com.example.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
Kelas JobBatalRequest menangani request untuk mendapatkan sebuah invoice
 */
public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://34.101.230.79:60006/invoice/jobseeker/";
    private Map<String,String> params;

    public JobFetchRequest(String jobseekerId, Response.Listener<String> listener) {
        super(Method.GET, URL+jobseekerId, listener, null);
        params = new HashMap<>();
    }

    @Override
    protected Map<String,String> getParams() {
        return params;
    }
}
