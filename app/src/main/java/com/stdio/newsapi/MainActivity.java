package com.stdio.newsapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NewsSpinnerModel> newsModel = new ArrayList<>();
    public static ArrayList<DataModel> dataList;
    private RecyclerView rv;
    AppCompatSpinner spNews;
    ArrayList<SourcesModel> sources = new ArrayList<>();
    String apiKey = "f500d8d177eb4f9981c46c3731f2de70";
    int i = 0;
    ArrayAdapter<NewsSpinnerModel> spinnerAdapter;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        getSources();
        getData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    private void getSources() {
        sources.add(new SourcesModel("Cointelegraph Bitcoin & Ethereum Blockchain", "cointelegraph.com"));
        sources.add(new SourcesModel("The Wall Street Journal", "wsj.com"));
        sources.add(new SourcesModel("Lifehacker", "lifehacker.com"));
        sources.add(new SourcesModel("TechCrunch", "techcrunch.com"));
    }

    private void initSpinner() {
        spNews = findViewById(R.id.sp_news);

        spinnerAdapter = new ArrayAdapter<NewsSpinnerModel>(this, android.R.layout.simple_spinner_dropdown_item, newsModel) {

            @Override
            public boolean isEnabled(int position) {
                return newsModel.get(position).isHeader();
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row2, null);
                }

                TextView tvName = v.findViewById(R.id.tvName);
                NewsSpinnerModel model = newsModel.get(position);
                tvName.setText(model.getName());
                return v;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    Context mContext = this.getContext();
                    LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }

                TextView tvName = v.findViewById(R.id.tvName);
                NewsSpinnerModel model = newsModel.get(position);
                if (!model.isHeader) {
                    tvName.setPadding(50, 0,0,5);
                    tvName.setTypeface(null, Typeface.NORMAL);
                    tvName.setTextSize(10);
                }
                else {
                    tvName.setPadding(0, 10,0,10);
                    tvName.setTypeface(null, Typeface.BOLD);
                    tvName.setTextSize(15);
                }
                tvName.setText(model.getName());
                return v;
            }
        };

        spNews.setAdapter(spinnerAdapter);
    }

    private void newsSpinnerSetOnItemSelectedListener() {
        spNews.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                dataList = new ArrayList<>();
                int count = selectedItemPosition + 1;
                System.out.println("size is " + newsModel.size());
                while (count != newsModel.size() && !spinnerAdapter.getItem(count).isHeader) {
                    dataList.add(new DataModel(spinnerAdapter.getItem(count).name, spinnerAdapter.getItem(count).description, spinnerAdapter.getItem(count).url));
                    RVAdapter adapter = new RVAdapter(dataList, mContext);
                    rv.setAdapter(adapter);
                    count++;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getData() {
        requestData();
    }

    private void requestData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println("index " + i);
        String url ="https://newsapi.org/v2/everything?domains=" + sources.get(i).domain + "&apiKey=" + apiKey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;
                        newsModel.add(new NewsSpinnerModel(true, sources.get(i).getName(), "", ""));
                        if (i == sources.size() - 1) {
                            try {
                                obj = new JSONObject(response);
                                for (int j = 0; j < 5; j++) {
                                    JSONObject articles = obj.getJSONArray("articles").getJSONObject(j);
                                    newsModel.add(new NewsSpinnerModel(false, articles.getString("title"), articles.getString("description"), articles.getString("url")));
                                }
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                            }
                            initSpinner();
                            newsSpinnerSetOnItemSelectedListener();
                        }
                        else {
                            try {
                                obj = new JSONObject(response);
                                for (int j = 0; j < 5; j++) {
                                    JSONObject articles = obj.getJSONArray("articles").getJSONObject(j);
                                    newsModel.add(new NewsSpinnerModel(false, articles.getString("title"), articles.getString("description"), articles.getString("url")));
                                }
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                            }
                            if (i == 0) {
                                i++;
                                requestData();
                            }
                            else if (i + 1 < sources.size()) {
                                i++;
                                requestData();
                            }
                            else if (i == sources.size() - 1) {
                                requestData();
                                i++;
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("response " + error.getMessage());
            }
        });
        queue.add(stringRequest);
    }
}
