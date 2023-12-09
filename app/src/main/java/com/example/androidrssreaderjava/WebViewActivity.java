package com.example.androidrssreaderjava;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidrssreaderjava.databinding.ActivityMainBinding;
import com.example.androidrssreaderjava.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        navigateUrl();

    }

    private void navigateUrl() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String url = extras.getString("url");
            if(!TextUtils.isEmpty(url)){
                binding.webView.loadUrl(url);
            }

        }
    }

    private void initBinding() {
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}