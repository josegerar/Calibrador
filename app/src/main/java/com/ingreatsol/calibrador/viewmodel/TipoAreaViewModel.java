package com.ingreatsol.calibrador.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.ingreatsol.calibrador.entity.TipoArea;
import com.ingreatsol.calibrador.repository.TipoAreaRepository;

import java.util.List;

public class TipoAreaViewModel extends AndroidViewModel {

    private TipoAreaRepository mRepository;

    private final LiveData<List<TipoArea>> mAllTipoAreas;

    public TipoAreaViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TipoAreaRepository(application);
        mAllTipoAreas = mRepository.getObservable();
    }

    public LiveData<List<TipoArea>> getObservable() { return mAllTipoAreas; }

    public void insert(TipoArea tipoArea) { mRepository.insert(tipoArea); }
}
