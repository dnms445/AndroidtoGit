package com.jin.androidfreeerp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

public class ItemLedgerUpdateActivity extends AppCompatActivity {

    private ItemLedgerDao itemLedgerDao;
    private List<ItemLedger> itemLedgerList;
    private ItemDao itemDao;
    private List<Item> itemList;
    private String radioGroupItemLedgerState;
    private EditText editTextsItemLedgerCode, editTextsItemLedgerName, editTextLedgeriQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_ledger_update);

        ItemLedgerAppDatabase itemLedgerDb = Room.databaseBuilder(this, ItemLedgerAppDatabase.class, "ItemLedger-db")
                .allowMainThreadQueries()
                .build();
        itemLedgerDao = itemLedgerDb.itemLedgerDao();

        AppDatabase itemDb = Room.databaseBuilder(this, AppDatabase.class, "Item-db")
                .allowMainThreadQueries()
                .build();
        itemDao = itemDb.itemDao();

        Button buttonItemSearch = findViewById(R.id.buttonItemSearch);
        Button buttonItemLedgerSave = findViewById(R.id.buttonItemLedgerSave);
        editTextsItemLedgerCode = findViewById(R.id.editTextsItemLedgerCode);
        editTextsItemLedgerName = findViewById(R.id.editTextsItemLedgerName);
        editTextLedgeriQuantity = findViewById(R.id.editTextLedgeriQuantity);
        RadioButton radioButtonReleased = findViewById(R.id.radioButtonReleased);
        RadioButton radioButtonStored = findViewById(R.id.radioButtonStored);
        RadioGroup radioGroupItemLedger = findViewById(R.id.radioGroupItemLedger);

        buttonItemSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ItemLedgerUpdateActivity.this, ItemSearchActivity.class);
                intent.putExtra("itemId", "");
                startActivityForResult(intent, 1);
            }
        });

        radioGroupItemLedgerState = "Released";
        radioGroupItemLedger.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButtonReleased){
                    radioGroupItemLedgerState = "Released";
                } else if(checkedId == R.id.radioButtonStored){
                    radioGroupItemLedgerState = "Stored";
                }
            }
        });

        buttonItemLedgerSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (editTextsItemLedgerCode.getText().length() == 0) {
                    Toast.makeText(ItemLedgerUpdateActivity.this, "품목을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextLedgeriQuantity.getText().length() == 0) {
                    Toast.makeText(ItemLedgerUpdateActivity.this, "수량을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                };
                ItemLedger itemLedger = new ItemLedger(""
                        , ""
                        , editTextsItemLedgerCode.getText().toString()
                        , Integer.parseInt(editTextLedgeriQuantity.getText().toString())
                        , 0
                        , ""
                        );

                itemLedgerDao.insert(itemLedger);
                if (radioGroupItemLedgerState == "Stored") {
                    itemDao.updateStock(editTextsItemLedgerCode.getText().toString()
                            , Integer.parseInt(editTextLedgeriQuantity.getText().toString())
                    );
                } else {
                    itemDao.updateStock(editTextsItemLedgerCode.getText().toString()
                            , -(Integer.parseInt(editTextLedgeriQuantity.getText().toString()))
                    );
                }

                Toast.makeText(ItemLedgerUpdateActivity.this, "신규 저장되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1)
            {
                editTextsItemLedgerCode.setText(intent.getStringExtra("itemIdReturnCode"));
                editTextsItemLedgerName.setText(intent.getStringExtra("itemIdReturnName"));
            }
        }
    }
}