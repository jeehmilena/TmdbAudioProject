package com.example.tmdbaudio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.tmdbaudio.R;
import com.example.tmdbaudio.adapter.RecyclerViewAdapater;
import com.example.tmdbaudio.model.Album;
import com.example.tmdbaudio.viewmodel.AlbumViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Album> albuns = new ArrayList<>();
    private AlbumViewModel viewModel;
    private ProgressBar progressBar;
    private RecyclerViewAdapater adapter;
    private SearchView searchView;
    private String bandName = "Aerosmith";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        viewModel.getAlbuns(bandName);
        viewModel.getAlbumLiveData().observe(this, (List<Album> albuns) -> {
            adapter.setUpdate(albuns);
        });

        viewModel.isLoading.observe(this, (Boolean loading) -> {
            if (loading){
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                bandName = text;
                adapter.clear();
                viewModel.getAlbuns(bandName);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);
        searchView = findViewById(R.id.searchView);
        adapter = new RecyclerViewAdapater(albuns);
        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        recyclerView.setAdapter(adapter);
    }
}
