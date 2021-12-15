package com.example.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
Kelas JobBatalRequest menangani request untuk menyelesaikan sebuah invoice
 */
public class JobSelesaiRequest extends StringRequest {
    private static final String URL = "http://34.101.230.79:60006/invoice/invoiceStatus/";
    private Map<String, String> params;
    private String invoiceStatus = "Finished";

    public JobSelesaiRequest (String invoiceId, Response.Listener<String> listener) {
        super(Method.PUT, URL+invoiceId, listener, null);
        params = new HashMap<>();
        params.put("id", invoiceId);
        params.put("status", invoiceStatus);
    }

    @Override
    protected Map<String, String> getParams() {
        return params;
    }
}

