package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static int jobseekerId;
    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping  = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            jobseekerId = extras.getInt("jobseekerId");
        }
        refreshList();
        expandableListView = findViewById(R.id.expandableLV);
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
            Job selectedJob = Objects.requireNonNull(childMapping.get(listRecruiter.get(groupPosition))).get(childPosition);
            intent.putExtra("jobID", selectedJob.getId());
            intent.putExtra("jobName", selectedJob.getName());
            intent.putExtra("jobCategory", selectedJob.getCategory());
            intent.putExtra("jobFee", selectedJob.getFee());
            intent.putExtra("jobseekerId", jobseekerId);
            startActivity(intent);
            return true;
        });
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = response -> {
            try {
                JSONArray jsonResponse = new JSONArray(response);
                if (jsonResponse != null) {
                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject job = jsonResponse.getJSONObject(i);
                        JSONObject recruiter = job.getJSONObject("recruiter");
                        JSONObject location = recruiter.getJSONObject("location");
                        String city = location.getString("city");
                        String province = location.getString("province");
                        String description = location.getString("description");
                        Location location1 = new Location(city, province, description);
                        int recruiterId = recruiter.getInt("id");
                        String recruiterName = recruiter.getString("name");
                        String recruiterEmail = recruiter.getString("email");
                        String recruiterPhoneNumber = recruiter.getString("phoneNumber");
                        Recruiter recruiter1 = new Recruiter(recruiterId, recruiterName, recruiterEmail, recruiterPhoneNumber, location1);
                        if (listRecruiter.size() > 0) {
                            boolean status = true;
                            for (Recruiter recruiterr : listRecruiter)
                                if (recruiterr.getId() == recruiter1.getId())
                                    status = false;
                            if (status) {
                                listRecruiter.add(recruiter1);
                            }
                        } else {
                            listRecruiter.add(recruiter1);
                        }
                        int jobId = job.getInt("id");
                        int jobPrice = job.getInt("fee");
                        String jobName = job.getString("name");
                        String jobCategory = job.getString("category");
                        Job job1 = new Job(jobId, jobName, recruiter1, jobPrice, jobCategory);
                        jobIdList.add(job1);
                        for (Recruiter sel : listRecruiter) {
                            ArrayList<Job> temp = new ArrayList<>();
                            for (Job jobs : jobIdList) {
                                if (jobs.getRecruiter().getName().equals(sel.getName()) || jobs.getRecruiter().getEmail().equals(sel.getEmail()) || jobs.getRecruiter().getPhoneNumber().equals(sel.getPhoneNumber())) {
                                    temp.add(jobs);
                                }
                            }
                            childMapping.put(sel, temp);
                        }
                    }
                    listAdapter = new MainListAdapter(MainActivity.this, listRecruiter, childMapping);
                    expandableListView.setAdapter(listAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);

        Button btnAppliedJob = findViewById(R.id.buttonApplyJob);
        btnAppliedJob.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelesaiJobActivty.class);
            intent.putExtra("jobseekerId", jobseekerId);
            startActivity(intent);
        });
    }
}