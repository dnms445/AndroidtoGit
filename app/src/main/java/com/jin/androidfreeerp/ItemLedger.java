package com.jin.androidfreeerp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class  ItemLedger {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ItemLedgerCode;
    private String CustomerCode;
    private String ItemCode;
    private int Quantity;
    private int Price;
    private String ItemLedgerMemo;

    public ItemLedger(String ItemLedgerCode, String CustomerCode, String ItemCode, int Quantity, int Price, String ItemLedgerMemo) {
        this.ItemLedgerCode = ItemLedgerCode;
        this.CustomerCode = CustomerCode;
        this.ItemCode = ItemCode;
        this.Quantity = Quantity;
        this.Price = Price;
        this.ItemLedgerMemo = ItemLedgerMemo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemLedgerCode() {
        return ItemLedgerCode;
    }

    public void setItemLedgerCode(String itemLedgerCode) {
        ItemLedgerCode = itemLedgerCode;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getItemLedgerMemo() {
        return ItemLedgerMemo;
    }

    public void setItemLedgerMemo(String itemLedgerMemo) {
        ItemLedgerMemo = itemLedgerMemo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ItemLedgerCode='" + ItemLedgerCode + '\'' +
                ", CustomerCode='" + CustomerCode + '\'' +
                ", ItemCode=" + ItemCode +
                ", Quantity=" + Quantity +
                ", Price='" + Price + '\'' +
                ", ItemLedgerMemo=" + ItemLedgerMemo +
                '}';
    }
}
