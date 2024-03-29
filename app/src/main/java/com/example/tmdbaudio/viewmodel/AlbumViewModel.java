package com.example.tmdbaudio.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdbaudio.model.Album;
import com.example.tmdbaudio.model.Artista;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.tmdbaudio.network.RetrofitService.getApiService;

public class AlbumViewModel extends AndroidViewModel {
    public MutableLiveData<List<Album>> albunsLiveData = new MutableLiveData<>();
    MutableLiveData<Throwable> albunsLiveDataError = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public AlbumViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Album>> getAlbumLiveData() {
        return albunsLiveData;
    }

    public void getAlbuns(String artista) {

        disposable.add(
                getApiService().getAllAlbunArtist(artista)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe((Disposable disposable) -> {
                            isLoading.setValue(true);
                        })
                        .doOnTerminate(() -> {
                            isLoading.setValue(false);
                        })
                        .subscribe((Artista artistaResponse) ->
                                {
                                    albunsLiveData.setValue(artistaResponse.getAlbum());
                                }
                                , throwable -> {
                                    Log.i("LOG", "Error: " + throwable.getMessage());
                                })

        );


    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
