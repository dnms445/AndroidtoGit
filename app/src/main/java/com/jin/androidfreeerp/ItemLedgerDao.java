package com.jin.androidfreeerp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemLedgerDao {
    @Query("SELECT * FROM ItemLedger order by ItemLedgerCode")
    List<ItemLedger> getAll();

    @Insert
    void insert(ItemLedger itemLedger);

    @Update
    void update(ItemLedger itemLedger);

    @Delete
    void delete(ItemLedger itemLedger);

    @Query("SELECT * FROM ItemLedger WHERE ItemLedgerCode = :selectCode")
    List<ItemLedger> selectCode(String selectCode);

    @Query("DELETE FROM ItemLedger WHERE ItemLedgerCode = :delCode")
    void deleteCode(String delCode);

    @Query("SELECT COUNT(ItemLedgerCode) FROM ItemLedger WHERE ItemLedgerCode = :countCode")
    int countCode(String countCode);

    @Query("SELECT ItemLedgerCode FROM ItemLedger order by ItemLedgerCode desc LIMIT 1")
    String newCode();

    @Query("UPDATE ItemLedger SET CustomerCode = :CustomerCode, ItemCode = :ItemCode, Quantity = :Quantity, Price = :Price, ItemLedgerMemo = :ItemLedgerMemo WHERE ItemLedgerCode = :ItemLedgerCode")
    void updateCode(String ItemLedgerCode, String CustomerCode, String ItemCode, int Quantity, int Price, String ItemLedgerMemo);

}
