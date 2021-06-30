package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
/*
Kelas ApplyJobActivity merupakan kelas dimana seorang jobseeker melamar sebuah pekerjaan dan dapat memilih cara pembayaran
 */
public class ApplyJobActivity extends AppCompatActivity {
    private int jobseekerID;
    private int jobID;
    private String jobName;
    private String jobCategory;
    private double jobFee;
    private int bonus;
    private String selectedPayment;
    ApplyJobRequest applyJobRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerID = extras.getInt("jobseekerId");
            jobID = extras.getInt("jobID");
            jobName = extras.getString("jobName");
            jobCategory = extras.getString("jobCategory");
            jobFee = extras.getInt("jobFee");
        }
        TextView textViewPesanan = findViewById(R.id.pesanan);
        TextView job_name = findViewById(R.id.job_name);
        TextView job_category = findViewById(R.id.job_category);
        TextView job_fee = findViewById(R.id.job_fee);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton ewallet = findViewById(R.id.ewallet);
        RadioButton bank = findViewById(R.id.bank);
        TextView textCode = findViewById(R.id.textCode);
        EditText referral_code = findViewById(R.id.referral_code);
        TextView total_fee  = findViewById(R.id.total_fee);
        Button btnApply = findViewById(R.id.btnApply);
        Button hitung = findViewById(R.id.hitung);
        btnApply.setVisibility(View.INVISIBLE);
        btnApply.setEnabled(false);
        textCode.setVisibility(View.INVISIBLE);
        referral_code.setVisibility(View.INVISIBLE);
        job_name.setText(jobName);
        job_category.setText(jobCategory);
        job_fee.setText(String.valueOf(jobFee));
        total_fee.setText(String.valueOf(0));
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton checkedRadioButton = findViewById(checkedId);
            String paymentMethod = checkedRadioButton.getText().toString().trim();
            switch (paymentMethod) {
                case "E-wallet":
                    textCode.setVisibility(View.VISIBLE);
                    referral_code.setVisibility(View.VISIBLE);
                    break;
                case "Bank":
                    textCode.setVisibility(View.INVISIBLE);
                    referral_code.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        });

        hitung.setOnClickListener(v -> {
            String referralCode = referral_code.getText().toString().trim();
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
            String paymentMethod = checkedRadioButton.getText().toString().trim();
            switch (paymentMethod) {
                case "Bank":
                    total_fee.setText(String.valueOf(jobFee));
                    Toast.makeText(ApplyJobActivity.this, "Total price has been updated!", Toast.LENGTH_LONG).show();
                    break;
                case "E-wallet":
                    if (!referralCode.isEmpty()) {
                        Response.Listener<String> responseListener = response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject != null) {
                                    int extraFee = jsonObject.getInt("extraFee");
                                    int minTotalFee = jsonObject.getInt("minTotalFee");
                                    boolean status = jsonObject.getBoolean("active");

                                    if (status && (jobFee > minTotalFee || jobFee > extraFee)) {
                                        total_fee.setText(String.valueOf(jobFee + extraFee));
                                        Toast.makeText(ApplyJobActivity.this, "Total price has been updated!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        total_fee.setText(String.valueOf(jobFee));
                                        Toast.makeText(ApplyJobActivity.this, "Referral code not found or not active.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        };

                        BonusRequest bonusRequest = new BonusRequest(referralCode, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);
                    }
                    else {
                        total_fee.setText(String.valueOf(jobFee));
                    }
                    break;
                default:
                    break;
            }
            hitung.setVisibility(View.INVISIBLE);
            hitung.setEnabled(false);
            btnApply.setVisibility(View.VISIBLE);
            btnApply.setEnabled(true);
        });

        btnApply.setOnClickListener(v -> {
            String referralCode = referral_code.getText().toString().trim();
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);
            String paymentMethod = checkedRadioButton.getText().toString().trim();
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        Toast.makeText(ApplyJobActivity.this, "Invoice created", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(ApplyJobActivity.this, "Failed to create invoice", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApplyJobActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            };
            switch (paymentMethod) {
                case "E-wallet":
                    applyJobRequest = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), referralCode, responseListener);
                    break;
                case "Bank":
                    applyJobRequest = new ApplyJobRequest(String.valueOf(jobID), String.valueOf(jobseekerID), responseListener);
                    break;
                default:
                    break;
            }
            RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this); 
            queue.add(applyJobRequest);
        });
    }
}