package com.ingreatsol.calibrador.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ingreatsol.calibrador.AppDatabase;
import com.ingreatsol.calibrador.dao.TipoAreaDao;
import com.ingreatsol.calibrador.entity.TipoArea;

import java.util.List;

public class TipoAreaRepository {
    private final TipoAreaDao mTipoAreaDao;
    private final LiveData<List<TipoArea>> mAllTipoAreas;

    public TipoAreaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTipoAreaDao = db.tipoAreaDao();
        mAllTipoAreas = mTipoAreaDao.getObservable();
    }

    public LiveData<List<TipoArea>> getObservable() {
        return mAllTipoAreas;
    }

    public void insert(TipoArea tipoArea) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTipoAreaDao.insert(tipoArea);
        });
    }
}
