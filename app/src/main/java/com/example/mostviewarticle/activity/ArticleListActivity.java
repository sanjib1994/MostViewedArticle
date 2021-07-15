package com.example.mostviewarticle.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mostviewarticle.R;
import com.example.mostviewarticle.adapter.ArticleListAdapter;
import com.example.mostviewarticle.api.APIManager;
import com.example.mostviewarticle.api.ApiConfig;
import com.example.mostviewarticle.api.Apicallback;
import com.example.mostviewarticle.model.ArticleData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleListActivity extends AppCompatActivity {

    ArrayList<ArticleData> articleList;

    RecyclerView recyclerView;
    KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        initViews();

        callApi();
    }

    private void initViews(){

        recyclerView = findViewById(R.id.recycleview);

        kProgressHUD =  KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Fetching Data....")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

    }

    private void callApi(){
        try {
            if (!kProgressHUD.isShowing()) {
                kProgressHUD.show();
            }

            new APIManager().getApiCall(ApiConfig.YOUR_SERVER_URL,
                    new Apicallback() {
                        @Override
                        public void success(int code, String value) {

                            if (code == 200) {

                                parse_stops_response(value, new ApiParsing() {
                                    @Override
                                    public void onSuccess(Object object) {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (kProgressHUD.isShowing()) {
                                                    kProgressHUD.dismiss();
                                                }

                                                showList();

                                            }
                                        });


                                    }

                                    @Override
                                    public void onFailure() {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (kProgressHUD.isShowing()) {
                                                    kProgressHUD.dismiss();
                                                    Toast.makeText(ArticleListActivity.this,
                                                            "Something went wrong..Please try after sometime.",Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });
                                    }
                                });

                            }
                        }

                        @Override
                        public void failure(String value) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (kProgressHUD.isShowing()) {
                                        kProgressHUD.dismiss();
                                        Toast.makeText(ArticleListActivity.this,
                                                "Something went wrong..Please try after sometime.",Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parse_stops_response(String response,ApiParsing apiParsing) {
        try {
            articleList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for(int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                ArticleData articleData = new ArticleData();
                articleData.setTitle(jsonObject1.getString("title"));
                articleData.setByline(jsonObject1.getString("byline"));
                articleData.setPublished_date(jsonObject1.getString("published_date"));
                articleData.setDetail(jsonObject1.getString("abstract"));

                JSONArray jsonArray1 = jsonObject1.getJSONArray("media");
                for(int j=0;j<jsonArray1.length();j++){
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("media-metadata");
                    ArrayList<String> media = new ArrayList<>();
                    for(int k=0;k<jsonArray2.length();k++){
                        JSONObject jsonObject3 = jsonArray2.getJSONObject(k);
                        media.add(jsonObject3.getString("url"));

                    }
                    articleData.setMedia(media);
                }
                articleList.add(articleData);
            }

            apiParsing.onSuccess(articleList);

        } catch (Exception e) {
            apiParsing.onFailure();
            e.printStackTrace();
        }

    }

    private void showList(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        ArticleListAdapter adapter = new ArticleListAdapter(ArticleListActivity.this, articleList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public interface ApiParsing{
        void onSuccess(Object object);
        void onFailure();
    }
}

