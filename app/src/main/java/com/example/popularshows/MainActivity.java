package com.example.popularshows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

    RecyclerView recyclerView;
    ArrayList<dataclass> list;
    Adapter adapter;
    LinearLayoutManager  manager;
    boolean isscrolling = false;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycelerview);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        page = 1;

        fetchshows(page);
        pagination();

        adapter = new Adapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    private void pagination() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isscrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentintems = manager.getChildCount();
                int scrolleditems = manager.findFirstVisibleItemPosition();
                int totalitems = manager.getItemCount();
                if(isscrolling && currentintems+scrolleditems == totalitems){
                    page++;
                  //  Toast.makeText(getApplicationContext(),page,Toast.LENGTH_SHORT).show();
                    fetchshows(page);
                    isscrolling = false;
                }
            }
        });
    }

    public void fetchshows(int page) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=bf3a2f6566e0b444cb16cbf46e1e5602&language=en-US&page="+page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i=0 ; i< jsonArray.length() ; i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String name = jsonObject1.getString("name");
                                String overview = jsonObject1.getString("overview");
                                String vote_average = jsonObject1.getString("vote_average");
                                String vote_count = jsonObject1.getString("vote_count");
                                String image = jsonObject1.getString("poster_path");

                                list.add(new dataclass(name,overview,vote_average,vote_count,image));
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

}