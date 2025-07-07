package com.korlus.korluslive;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

public class LiveActivity extends AppCompatActivity {

    String userID, name, liveID;
    boolean isHost;

    TextView liveIdText;1


    04955
        setContentView(R.layout.activity_live);

        liveIdText = findViewById(R.id.live_id_textview);
        shareBtn = findViewById(R.id.share_btn);

        userID = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        liveID = getIntent().getStringExtra("live_id");
        isHost = getIntent().getBooleanExtra("host",false);

        liveIdText.setText(liveID);

        addFragment();99999999999999999999999999999

        shareBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"Canlı yayına katılın \n Live ID: " + liveID);
            startActivity(Intent.createChooser(intent,"Paylaş"));
        });

    }

    void addFragment(){
        ZegoUIKitPrebuiltLiveStreamingConfig config;
        if (isHost) {
            config = ZegoUIKitPrebuiltLiveStreamingConfig.host();
        } else {
            config = ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }
        ZegoUIKitPrebuiltLiveStreamingFragment fragment = ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                AppConstants.appId, AppConstants.appSign, userID, name, liveID, config
        );
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                .commitNow();
    }
}