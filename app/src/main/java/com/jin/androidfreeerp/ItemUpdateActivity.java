package com.jin.androidfreeerp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class ItemUpdateActivity extends AppCompatActivity {

    private ItemDao itemDao;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_update);

        AppDatabase itemDb = Room.databaseBuilder(this, AppDatabase.class, "Item-db")
                .allowMainThreadQueries()
                .build();
        itemDao = itemDb.itemDao();

        Button buttonItemSave = findViewById(R.id.buttonItemSave);
        Button buttonItemDel = findViewById(R.id.buttonItemDel);
        EditText editTextsItemCode = findViewById(R.id.editTextsItemCode);
        EditText editTextsItemName = findViewById(R.id.editTextsItemName);
        EditText editTextiPurchasePrice = findViewById(R.id.editTextiPurchasePrice);
        EditText editTextiSellingPrice = findViewById(R.id.editTextiSellingPrice);
        EditText editTextsItemMemo = findViewById(R.id.editTextsItemMemo);

        Intent intent = getIntent();
        String sItemCode = intent.getExtras().getString("itemId");
        if(sItemCode.equals("")){
            if (itemDao.countCode() > 0)
            {
                editTextsItemCode.setText(Integer.toString(Integer.parseInt(itemDao.newCode()) + 1));
            } else {

            }
        }else {
            editTextsItemCode.setText(sItemCode);
            itemList = itemDao.selectCode(editTextsItemCode.getText().toString());
            editTextsItemName.setText(itemList.get(0).getItemName());
            editTextiPurchasePrice.setText(Integer.toString(itemList.get(0).getPurchasePrice()));
            editTextiSellingPrice.setText(Integer.toString(itemList.get(0).getSellingPrice()));
            editTextsItemMemo.setText(itemList.get(0).getItemMemo());
        }

        buttonItemSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (editTextsItemCode.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "코드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextsItemName.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "명칭을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextiPurchasePrice.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "입고가를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextiSellingPrice.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "출고가를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextsItemMemo.getText().length() == 0) {
                    editTextsItemMemo.setText("");
                };

                if (itemDao.countCode(editTextsItemCode.getText().toString()) > 0)
                {
                    AlertDialog.Builder saveAD = new AlertDialog.Builder(ItemUpdateActivity.this);
                    saveAD.setIcon(R.mipmap.ic_launcher);
                    saveAD.setTitle("확인");
                    saveAD.setMessage("해당 품목을 업데이트 하시겠습니까?");
                    saveAD.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            itemDao.updateCode(editTextsItemCode.getText().toString()
                                    , editTextsItemName.getText().toString()
                                    , Integer.parseInt(editTextiPurchasePrice.getText().toString())
                                    , Integer.parseInt(editTextiSellingPrice.getText().toString())
                                    , editTextsItemMemo.getText().toString());
                            Toast.makeText(ItemUpdateActivity.this, "업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    saveAD.setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which){

                        }
                    });
                    saveAD.show();
                }
                else {
                    Item item = new Item(editTextsItemCode.getText().toString()
                            , editTextsItemName.getText().toString()
                            , Integer.parseInt(editTextiPurchasePrice.getText().toString())
                            , Integer.parseInt(editTextiSellingPrice.getText().toString())
                            , editTextsItemMemo.getText().toString()
                            , 0
                            , 0);

                    itemDao.insert(item);
                    Toast.makeText(ItemUpdateActivity.this, "신규 저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonItemDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editTextsItemCode.getText().toString();
                File datafile = new File(getFilesDir(), "data.txt");
                try {
                    // 파일에 데이터 저
                    FileWriter writer = new FileWriter(datafile);
                    writer.write(data);
                    writer.flush();
                    writer.close();
                    Toast.makeText(ItemUpdateActivity.this, "텍스트가 파일에 저장되었습니다", Toast.LENGTH_SHORT).show();

                    itemDao.deleteCode(editTextsItemCode.getText().toString());
                    Toast.makeText(ItemUpdateActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ItemUpdateActivity.this, "파일 저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            });
        }

    }