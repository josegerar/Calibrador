package com.ingreatsol.calibrador.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Coordenadas {
    @PrimaryKey
    @NonNull
    public UUID uuid;

    public double x;

    public double y;

    public UUID uuidLote;

    public Coordenadas() {
        this.uuid = UUID.randomUUID();
    }

    public Coordenadas(double x, double y, UUID uuidLote) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.uuidLote = uuidLote;
    }
}
