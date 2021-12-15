package com.example.jwork_android;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
Kelas ApplyJobRequest menangani semua request pada saat melamar pekerjaan
 */
public class ApplyJobRequest extends StringRequest {
    private static final String ewalletURL = "http://34.101.230.79:60006/invoice/createEWalletPayment";
    private static final String bankURL = "http://34.101.230.79:60006/invoice/createBankPayment";
    private Map<String, String> params;

    public ApplyJobRequest(String jobIdList, String jobseekerId, String referralCode, Response.Listener<String> listener) {
        super(Method.POST, ewalletURL, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
        params.put("referralCode", referralCode);
    }

    public ApplyJobRequest(String jobIdList, String jobseekerId, Response.Listener<String> listener) {
        super(Method.POST, bankURL, listener, null);
        params = new HashMap<>();
        params.put("jobIdList", jobIdList);
        params.put("jobseekerId", jobseekerId);
        params.put("adminFee", "100");
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
