package com.example.lutemonapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LutemonListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LutemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lutemon_list);

        recyclerView = findViewById(R.id.recyclerLutemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 初始加载 home 区域的 Lutemon
        List<Lutemon> homeLutemons = Storage.getInstance().getLutemonsByArea("home");
        adapter = new LutemonAdapter(homeLutemons);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 每次回到这个页面时刷新 home 区域数据
        List<Lutemon> updatedHomeLutemons = Storage.getInstance().getLutemonsByArea("home");
        adapter.updateData(updatedHomeLutemons);
    }
}



