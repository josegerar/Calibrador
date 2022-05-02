package com.ingreatsol.calibrador.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Lote extends MasterTable{
    @PrimaryKey
    @NonNull
    public UUID uuid;

    public String nombre;

    public Lote() {
        this.uuid = UUID.randomUUID();
    }

    public Lote(String nombre) {
        this.nombre = nombre;
    }
}
