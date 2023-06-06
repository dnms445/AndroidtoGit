package com.jin.androidfreeerp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ItemLedger.class}, version = 1)
public abstract class ItemLedgerAppDatabase extends RoomDatabase {
    public abstract ItemLedgerDao itemLedgerDao();
}
