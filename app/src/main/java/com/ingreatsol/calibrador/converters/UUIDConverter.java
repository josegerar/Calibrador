package com.ingreatsol.calibrador.converters;

import androidx.room.TypeConverter;

import java.util.UUID;

public class UUIDConverter {
    @TypeConverter
    public static String fromUUID(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

    @TypeConverter
    public static UUID uuidFromString(String string) {
        return string.isEmpty() ? null : UUID.fromString(string);
    }
}
