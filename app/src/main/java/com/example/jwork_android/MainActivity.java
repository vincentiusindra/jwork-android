package com.example.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private ArrayList<Job> jobIdList = new ArrayList<>();
    private HashMap<Recruiter, ArrayList<Job>> childMapping  = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableLV);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshList();
            }
        });
    }

    protected void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                            int jobPrice = job.getInt("price");
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}