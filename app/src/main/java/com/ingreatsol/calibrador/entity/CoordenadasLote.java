package com.ingreatsol.calibrador.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CoordenadasLote {
    @Embedded
    public Lote lote;

    @Relation(
            parentColumn = "uuid",
            entityColumn = "uuidLote"
    )
    public List<Coordenadas> Coordenadas;
}
