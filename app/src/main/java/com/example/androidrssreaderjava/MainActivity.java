package com.example.androidrssreaderjava;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidrssreaderjava.adapter.MyAdapter;
import com.example.androidrssreaderjava.databinding.ActivityMainBinding;
import com.example.androidrssreaderjava.interfaces.IRecyclerClickListener;
import com.example.androidrssreaderjava.viewmodel.MainViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements IRecyclerClickListener {

    ActivityMainBinding binding;
    MainViewModel viewModel;

    FloatingActionButton add;

    FloatingActionButton show;
    Spinner spinner;
    Dialog dialog;
    Dialog dialog1;

    Map<String, String> sites = new HashMap<>();
    Gson gson = new Gson();

    public  String retro_url= "https://rss.stopgame.ru/";
    public  String api_url= "rss_all.xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initViewModel();
        initViews();
        initObserve();

        add = findViewById(R.id.btnadd);
        if (sites == null) {
            sites.put("StopGames", "https://rss.stopgame.ru/rss_all.xml");
        }
        show = findViewById(R.id.dialog);
        dialog = new Dialog(MainActivity.this);

        dialog1 = new Dialog(MainActivity.this);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomDialog();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

    }



    private void addCustomDialog() {
        dialog.setContentView(R.layout.dialog_layout_1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        MaterialButton login = dialog.findViewById(R.id.loginBtt);
        EditText name, site;
        name = findViewById(R.id.name);
        site = findViewById(R.id.channel_url);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                sites.put(name.toString(),site.toString());
                String sitesString = gson.toJson(sites);
                SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                prefs.edit().putString("sites", sitesString).apply();
                dialog.cancel();
            }
        });

        dialog.show();

    }

    private void showCustomDialog() {
        dialog1.setContentView(R.layout.dialog_layout_2);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(true);
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String storedSitesString = prefs.getString("sites", null);

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> storedSites = gson.fromJson(storedSitesString, type);
        sites = storedSites;
        List<String> names = new ArrayList<>();
        names.addAll(sites.keySet());
        MaterialButton login = dialog1.findViewById(R.id.loginBtt);
        spinner = dialog1.findViewById(R.id.spinner);
        ArrayAdapter<String> channelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);


        channelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(channelAdapter);
        spinner.setSelection(0);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(),adapterView.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog1.cancel();
            }
        });

        dialog1.show();
    }

    private void initObserve() {
        binding.recyclerRss.showShimmer();
        viewModel.getRSS().observe(this, rss -> {
            MyAdapter adapter = new MyAdapter(rss.channel.items, this);
            binding.recyclerRss.setAdapter(adapter);
            binding.recyclerRss.hideShimmer();
        });

        viewModel.getError().observe(this, error -> Toast.makeText(MainActivity.this, error , Toast.LENGTH_SHORT).show());
    }

    private void initViews() {
        binding.recyclerRss.setHasFixedSize(true);
        binding.recyclerRss.setLayoutManager(new LinearLayoutManager(this));


        binding.btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerRss.showShimmer();
                viewModel.getRSS();
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void initBinding() {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onItemClick(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);

        startActivity(intent);
    }
}