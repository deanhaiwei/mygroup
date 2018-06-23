package com.example.day13;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mobject=new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerviewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        adapter = new MyAdapter(this,mobject);
        recyclerView.setAdapter(adapter);
        loadobjectAsync();
    }
    public void loadobjectAsync (){
        new loadAsyncTask(recyclerView).execute();
    }
    static class loadAsyncTask extends AsyncTask<String,Void,List<String>>{
        private WeakReference<RecyclerView> reference;
        loadAsyncTask(RecyclerView recyclerView){
            reference = new WeakReference<>(recyclerView);
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> object= new ArrayList<>();
            String item = "A/B/C/D/E/F/G/H/I/J/K/L/M/N/O/P/Q/R/S/T/U/V/W/X/Y/Z";
            object.addAll(Arrays.asList(item.split("/")));

            return object;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            RecyclerView mrecyclerView = reference.get();
            if (mrecyclerView==null)return;
            MyAdapter adapter= (MyAdapter) mrecyclerView.getAdapter();
        adapter.AddAll(strings);
        adapter.notifyDataSetChanged();

        }
    }
}
