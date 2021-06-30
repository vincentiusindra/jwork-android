package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelesaiJobActivty extends AppCompatActivity {
    private static int jobseekerId;
    private int invoiceId;
    private String invoiceDate;
    private String paymentType;
    private int totalFee;
    private String jobseekerName;
    private String jobName;
    private int jobFee;
    private String invoiceStatus;
    private String referralCode;
    private JSONObject bonus;
    private CardView cardView;
    private TextView textViewTitle;
    private TextView staticTextViewJobseekerName;
    private TextView textViewJobseekerName;
    private TextView staticTextViewInvoiceDate;
    private TextView textViewInvoiceDate;
    private TextView staticTextViewPaymentType;
    private TextView textViewPaymentType;
    private TextView staticTextViewInvoiceStatus;
    private TextView textViewInvoiceStatus;
    private TextView staticTextViewReferralCode;
    private TextView textViewReferralCode;
    private TextView staticTextViewJobName;
    private TextView textViewJobName;
    private TextView textViewFee;
    private CardView cardView2;
    private TextView staticTextViewTotalFee;
    private TextView textViewTotalFee;
    private Button buttonCancel;
    private Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai_job_activty);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerId = extras.getInt("jobseekerId");
        }
        hideStuff();
        fetchJob();
        buttonCancel.setOnClickListener(v -> {
            Response.Listener<String> cancelResponseListener = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            Toast.makeText(SelesaiJobActivty.this, "The job has been canceled!", Toast.LENGTH_LONG).show();
            JobBatalRequest jobBatalRequest = new JobBatalRequest(String.valueOf(invoiceId), cancelResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivty.this);
            queue.add(jobBatalRequest);
            Intent intent = new Intent(SelesaiJobActivty.this, MainActivity.class);
            startActivity(intent);
        });
        buttonFinish.setOnClickListener(v -> {
            Response.Listener<String> finishResponseListener = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
            Toast.makeText(SelesaiJobActivty.this, "The job is applied!", Toast.LENGTH_LONG).show();
            JobSelesaiRequest jobSelesaiRequest = new JobSelesaiRequest(String.valueOf(invoiceId), finishResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivty.this);
            queue.add(jobSelesaiRequest);
            Intent intent = new Intent(SelesaiJobActivty.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void hideStuff(){
        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.INVISIBLE);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setVisibility(View.INVISIBLE);
        staticTextViewJobseekerName = findViewById(R.id.staticTextViewJobseekerName);
        staticTextViewJobseekerName.setVisibility(View.INVISIBLE);
        textViewJobseekerName = findViewById(R.id.textViewJobseekerName);
        textViewJobseekerName.setVisibility(View.INVISIBLE);
        staticTextViewInvoiceDate = findViewById(R.id.staticTextViewInvoiceDate);
        staticTextViewInvoiceDate.setVisibility(View.INVISIBLE);
        textViewInvoiceDate = findViewById(R.id.textViewInvoiceDate);
        textViewInvoiceDate.setVisibility(View.INVISIBLE);
        staticTextViewPaymentType = findViewById(R.id.staticTextViewPaymentType);
        staticTextViewPaymentType.setVisibility(View.INVISIBLE);
        textViewPaymentType = findViewById(R.id.textViewPaymentType);
        textViewPaymentType.setVisibility(View.INVISIBLE);
        staticTextViewInvoiceStatus = findViewById(R.id.staticTextViewInvoiceStatus);
        staticTextViewInvoiceStatus.setVisibility(View.INVISIBLE);
        textViewInvoiceStatus = findViewById(R.id.textViewInvoiceStatus);
        textViewInvoiceStatus.setVisibility(View.INVISIBLE);
        staticTextViewReferralCode = findViewById(R.id.staticTextViewReferralCode);
        staticTextViewReferralCode.setVisibility(View.INVISIBLE);
        textViewReferralCode = findViewById(R.id.textViewReferralCode);
        textViewReferralCode.setVisibility(View.INVISIBLE);
        staticTextViewJobName = findViewById(R.id.staticTextViewJobName);
        staticTextViewJobName.setVisibility(View.INVISIBLE);
        textViewJobName = findViewById(R.id.textViewJobName);
        textViewJobName.setVisibility(View.INVISIBLE);
        textViewFee = findViewById(R.id.textViewFee);
        textViewFee.setVisibility(View.INVISIBLE);
        cardView2 = findViewById(R.id.cardView2);
        cardView2.setVisibility(View.INVISIBLE);
        staticTextViewTotalFee = findViewById(R.id.staticTextViewTotalFee);
        staticTextViewTotalFee.setVisibility(View.INVISIBLE);
        textViewTotalFee = findViewById(R.id.textViewTotalFee);
        textViewTotalFee.setVisibility(View.INVISIBLE);
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setVisibility(View.INVISIBLE);
        buttonFinish = findViewById(R.id.buttonFinish);
        buttonFinish.setVisibility(View.INVISIBLE);
    }
    private void fetchJob(){
        Response.Listener<String> responseListener = response -> {
            if (response.isEmpty()) {
                Toast.makeText(SelesaiJobActivty.this, "No job has been applied!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelesaiJobActivty.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                cardView.setVisibility(View.VISIBLE);
                textViewTitle.setVisibility(View.VISIBLE);
                staticTextViewJobseekerName.setVisibility(View.VISIBLE);
                textViewJobseekerName.setVisibility(View.VISIBLE);
                staticTextViewInvoiceDate.setVisibility(View.VISIBLE);
                textViewInvoiceDate.setVisibility(View.VISIBLE);
                staticTextViewPaymentType.setVisibility(View.VISIBLE);
                textViewPaymentType.setVisibility(View.VISIBLE);
                staticTextViewInvoiceStatus.setVisibility(View.VISIBLE);
                textViewInvoiceStatus.setVisibility(View.VISIBLE);
                staticTextViewReferralCode.setVisibility(View.VISIBLE);
                textViewReferralCode.setVisibility(View.VISIBLE);
                staticTextViewJobName.setVisibility(View.VISIBLE);
                textViewJobName.setVisibility(View.VISIBLE);
                textViewFee.setVisibility(View.VISIBLE);
                cardView2.setVisibility(View.VISIBLE);
                staticTextViewTotalFee.setVisibility(View.VISIBLE);
                textViewTotalFee.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                buttonFinish.setVisibility(View.VISIBLE);
            }
            try {
                JSONArray jsonResponse = new JSONArray(response);
                for (int i=0; i<jsonResponse.length(); i++) {
                    JSONObject invoice = jsonResponse.getJSONObject(i);
                    invoiceStatus = invoice.getString("invoiceStatus");
                    Log.e("Status of invoice", invoiceStatus);
                    invoiceId = invoice.getInt("id");
                    invoiceDate = invoice.getString("date");
                    paymentType = invoice.getString("paymentType");
                    totalFee = invoice.getInt ("totalFee");
                    referralCode = "";
                    try {
                        bonus = invoice.getJSONObject("bonus");
                        referralCode = bonus.getString("referralCode");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonJobSeeker = invoice.getJSONObject("jobseeker");
                    jobseekerName = jsonJobSeeker.getString("name");
                    textViewJobseekerName.setText(jobseekerName);
                    textViewTitle.setText(String.valueOf(invoiceId));
                    textViewInvoiceDate.setText(invoiceDate.substring(0, 10));
                    textViewPaymentType.setText(paymentType);
                    textViewInvoiceStatus.setText(invoiceStatus);
                    textViewReferralCode.setText(referralCode);
                    JSONArray jsonJobs = invoice.getJSONArray("jobs");
                    for (int j=0; j<jsonJobs.length(); j++) {
                        JSONObject jsonJob = jsonJobs.getJSONObject(j);
                        jobName = jsonJob.getString("name");
                        textViewJobName.setText(jobName);
                        jobFee = jsonJob.getInt("fee");
                        textViewFee.setText(String.valueOf(jobFee));
                    }
                    textViewTotalFee.setText(String.valueOf(totalFee));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        JobFetchRequest fetchRequest = new JobFetchRequest(String.valueOf(jobseekerId), responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelesaiJobActivty.this);
        queue.add(fetchRequest);
    }
}