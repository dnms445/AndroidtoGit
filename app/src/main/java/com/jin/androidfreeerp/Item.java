package com.jin.androidfreeerp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ItemCode;
    private String ItemName;
    private int PurchasePrice;
    private int SellingPrice;
    private String ItemMemo;
    private int Stock;
    private int StartStock;


    public Item(String ItemCode, String ItemName, int PurchasePrice, int SellingPrice, String ItemMemo, int Stock, int StartStock) {
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.PurchasePrice = PurchasePrice;
        this.SellingPrice = SellingPrice;
        this.ItemMemo = ItemMemo;
        this.Stock = Stock;
        this.StartStock = StartStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String sItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String sItemName) {
        this.ItemName = ItemName;
    }

    public int getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(int iPurchasePrice) {
        this.PurchasePrice = PurchasePrice;
    }

    public int getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(int iSellingPrice) {
        this.SellingPrice = SellingPrice;
    }

    public String getItemMemo() {
        return ItemMemo;
    }

    public void setItemMemo(String sItemMemo) {
        this.ItemMemo = ItemMemo;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int iStock) {
        this.Stock = Stock;
    }

    public int getStartStock() {
        return StartStock;
    }

    public void setStartStock(int iStartStock) {
        this.StartStock = StartStock;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ItemCode='" + ItemCode + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", PurchasePrice=" + PurchasePrice +
                ", SellingPrice=" + SellingPrice +
                ", ItemMemo='" + ItemMemo + '\'' +
                ", Stock=" + Stock +
                ", StartStock=" + StartStock +
                '}';
    }
}
