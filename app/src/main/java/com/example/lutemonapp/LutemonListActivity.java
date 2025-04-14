package com.example.lutemonapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LutemonListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LutemonAdapter adapter;
    private TextView txtListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lutemon_list);

        recyclerView = findViewById(R.id.recyclerLutemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtListTitle = findViewById(R.id.txtListTitle); // Optional 显示当前区域标题

        // 获取传来的区域名参数（默认 "home"）
        String area = getIntent().getStringExtra("area");
        if (area == null) {
            area = "home";
        }

        // 设置页面标题
        txtListTitle.setText("Viewing Lutemons in " + capitalize(area));

        // 加载对应区域 Lutemon 列表
        List<Lutemon> lutemons = Storage.getInstance().getLutemonsByArea(area);
        adapter = new LutemonAdapter(lutemons);
        recyclerView.setAdapter(adapter);
    }

    // 首字母大写辅助函数
    private String capitalize(String input) {
        if (input == null || input.length() == 0) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}

