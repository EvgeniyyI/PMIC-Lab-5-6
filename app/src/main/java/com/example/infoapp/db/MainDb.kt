package com.example.infoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infoapp.utils.ListItem

@Database(
    entities = [ListItem::class],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val dao: Dao;
}