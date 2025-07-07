package com.korlus.korluslive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    Button goLiveBtn;
    TextInputEditText liveIdInput,nameInput;

    String liveID,name,userID;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("name_pref",MODE_PRIVATE);

        goLiveBtn = findViewById(R.id.go_live_button);
        liveIdInput = findViewById(R.id.live_id_input);
        nameInput = findViewById(R.id.name_input);

        nameInput.setText(sharedPreferences.getString("name",""));

        liveIdInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                liveID = liveIdInput.getText().toString();
                if(liveID.length() == 0){
                    goLiveBtn.setText("Yeni Yayına Başla");
                }else {
                    goLiveBtn.setText("Yayına Katıl");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        goLiveBtn.setOnClickListener(view -> {
            name = nameInput.getText().toString();
            if(name.isEmpty()){
                nameInput.setError("Adınızı Yazmanız Gerekiyor");
                nameInput.requestFocus();
                return;
            }

            liveID = liveIdInput.getText().toString();

            if (liveID.length() > 0 && liveID.length() != 5){
                liveIdInput.setError("Bu ID doğru değil.");
                liveIdInput.requestFocus();
                return;
            }
            startMeeting();

        });

    }

    void startMeeting(){

        sharedPreferences.edit().putString("name",name).apply();

        Log.i("LOG","Yayın Başladı");

        boolean isHost = true;
        if(liveID.length()==5){
            isHost = false;
        }else {
            liveID = generateLiveID();
            Log.e("id",liveID);
        }
        userID = UUID.randomUUID().toString();


        Intent intent = new Intent(MainActivity.this,LiveActivity.class);
        intent.putExtra("user_id",userID);
        intent.putExtra("name",name);
        intent.putExtra("live_id",liveID);
        intent.putExtra("host",isHost);
        startActivity(intent);
    }

    String generateLiveID(){
        StringBuilder id = new StringBuilder();
        while(id.length()!=5){
            int random = new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}