package com.example.tmdbaudio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        //setando a nova lista para o adapter do recyclerView
        viewModel.getAlbuns("Aerosmith");
        viewModel.albunsLiveData.observe(this, (List<Album> albuns) -> {
            adapter.setResult(albuns);
        });

        //mudando a visibilidade da barra de progresso de acordo com o retorno do isLoading
        viewModel.isLoading.observe(this, (Boolean loading) -> {
            if (loading){
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);
        adapter = new RecyclerViewAdapater(albuns);
        viewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
    }
}
