package com.example.lutemonapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CreateLutemonActivity extends AppCompatActivity {

    private EditText editName;
    private RadioGroup radioGroupColors;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lutemon);

        editName = findViewById(R.id.editName);
        radioGroupColors = findViewById(R.id.radioGroupColors);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            String name = editName.getText().toString();
            int checkedId = radioGroupColors.getCheckedRadioButtonId();

            if (name.isEmpty() || checkedId == -1) {
                Toast.makeText(this, "Please enter name and select color", Toast.LENGTH_SHORT).show();
                return;
            }

            Lutemon lutemon = null;

            if (checkedId == R.id.radioWhite) {
                lutemon = new WhiteLutemon(name);
            } else if (checkedId == R.id.radioGreen) {
                lutemon = new GreenLutemon(name);
            } else if (checkedId == R.id.radioPink) {
                lutemon = new PinkLutemon(name);
            } else if (checkedId == R.id.radioOrange) {
                lutemon = new OrangeLutemon(name);
            } else if (checkedId == R.id.radioBlack) {
                lutemon = new BlackLutemon(name);
            }

            if (lutemon != null) {
                Storage.getInstance().addLutemonToHome(lutemon); // ✅ 内部已包含 incrementCreated()
                Toast.makeText(this, "Lutemon created!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}


