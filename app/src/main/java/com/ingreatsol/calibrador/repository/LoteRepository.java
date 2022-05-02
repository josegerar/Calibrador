package com.ingreatsol.calibrador.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ingreatsol.calibrador.AppDatabase;
import com.ingreatsol.calibrador.dao.LoteDao;
import com.ingreatsol.calibrador.entity.Lote;

import java.util.List;

public class LoteRepository {
    private final LoteDao mLoteDao;
    private final LiveData<List<Lote>> mAllLote;

    public LoteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mLoteDao = db.loteDao();
        mAllLote = mLoteDao.getObservable();
    }

    public LiveData<List<Lote>> getObservable() {
        return mAllLote;
    }

    public void insert(Lote lote) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mLoteDao.insert(lote);
        });
    }
}
