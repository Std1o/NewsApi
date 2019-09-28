package com.stdio.newsapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

    TextView tvResult;
    ArrayList<NewsSpinnerModel> newsModel = new ArrayList<>();
    AppCompatSpinner spNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        getData();
        initSpinner();
    }

    private void initSpinner() {
        spNews = findViewById(R.id.sp_news);
        newsModel.add(new NewsSpinnerModel(true, "Cold Drinks"));
        newsModel.add(new NewsSpinnerModel(false, "Coke"));
        newsModel.add(new NewsSpinnerModel(false, "Sprite"));
        newsModel.add(new NewsSpinnerModel(false, "Pepsi"));
        newsModel.add(new NewsSpinnerModel(true, "Fruits Juice"));
        newsModel.add(new NewsSpinnerModel(false, "Orange Juice"));
        newsModel.add(new NewsSpinnerModel(false, "Strawberry Juice"));
        newsModel.add(new NewsSpinnerModel(false, "Lemon Juice"));

        ArrayAdapter<NewsSpinnerModel> spinnerAdapter = new ArrayAdapter<NewsSpinnerModel>(this, android.R.layout.simple_spinner_dropdown_item, newsModel) {

            @Override
            public boolean isEnabled(int position) {
                return !newsModel.get(position).isHeader();
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
                    tvName.setPadding(50, 0,0,20);
                    tvName.setTypeface(null, Typeface.NORMAL);
                }
                else {
                    tvName.setPadding(0, 0,0,0);
                    tvName.setTypeface(null, Typeface.BOLD);
                }
                tvName.setText(model.getName());
                return v;
            }
        };

        spNews.setAdapter(spinnerAdapter);
        spNews.setSelection(1);//Header should not be selected
    }

    public void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://newsapi.org/v2/everything?domains=google.com&apiKey=6486799f62994b81929ce7efa8f9a174&language=ru";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject obj;

                        try {
                            obj = new JSONObject(response);
                            JSONObject articles = obj.getJSONArray("articles").getJSONObject(0);
                            tvResult.setText(articles.getString("title"));
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
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
