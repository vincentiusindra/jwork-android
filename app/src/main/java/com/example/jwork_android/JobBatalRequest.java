package com.example.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
Kelas JobBatalRequest menangani request untuk membatalkan sebuah invoice
 */
public class JobBatalRequest extends StringRequest {
    private static final String URL = "http://192.168.1.6:8080/invoice/invoiceStatus/";
    private Map<String, String> params;
    private String invoiceStatus = "Cancelled";

    public JobBatalRequest (String invoiceId, Response.Listener<String> listener) {
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
