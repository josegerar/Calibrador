package com.ingreatsol.calibrador.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ingreatsol.calibrador.entity.CoordenadasLote;
import com.ingreatsol.calibrador.entity.Lote;
import com.ingreatsol.calibrador.entity.TipoArea;

import java.util.List;

@Dao
public interface LoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Lote... lotes);

    @Update
    public void update(Lote... lotes);

    @Delete
    public void delete(Lote... lotes);

    @Query("DELETE FROM Lote")
    public void deleteAll();

    @Query("SELECT * FROM Lote ORDER BY nombre ASC")
    public List<Lote> get();

    @Query("SELECT * FROM Lote ORDER BY nombre ASC")
    public LiveData<List<Lote>> getObservable();

    @Transaction
    @Query("SELECT * FROM Lote")
    public List<CoordenadasLote> getCoordenadasLote();
}
