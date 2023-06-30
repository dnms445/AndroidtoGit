package com.example.parking_test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemUpdateActivity extends AppCompatActivity {
    private ItemDao itemDao;
    private List<Item> itemList;

    private TextView mTimeTextView;

    private Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_update);

        AppDatabase itemDb = Room.databaseBuilder(this, AppDatabase.class, "Item-db")
                .allowMainThreadQueries()
                .build();
        itemDao = itemDb.itemDao();


        Button buttonItemSave = findViewById(R.id.buttonItemSave);
        Button buttonItemOut = findViewById(R.id.buttonItemOut);
        Button buttonItemComplete = findViewById(R.id.buttonItemComplete);
        EditText editTextsItemCode = findViewById(R.id.editTextsItemCode);
        EditText editTextsItemName = findViewById(R.id.editTextsItemName);
        EditText editTextNowDate = findViewById(R.id.editTextNowDate);
        EditText editTextItemTime = findViewById(R.id.editTextItemTime);
        //test code
        mTimeTextView = findViewById(R.id.timer);


        Intent intent = getIntent();
        String sItemCode = intent.getExtras().getString("itemId");
        if (sItemCode.equals("")) {
            if (itemDao.countCode() > 0)
                editTextsItemCode.setText(Integer.toString(Integer.parseInt(itemDao.newCode()) + 1));
        } else {
            //업데이드 후 보여주는 데이터
            editTextsItemCode.setText(sItemCode);
            itemList = itemDao.selectCode(editTextsItemCode.getText().toString());
            editTextsItemName.setText(itemList.get(0).getItemName());
            //update에 표기됨
            editTextNowDate.setText(itemList.get(0).getItemDate());
        }

        buttonItemSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 메세지 출력
                if (editTextsItemCode.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "코드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (editTextsItemName.getText().length() == 0) {
                    Toast.makeText(ItemUpdateActivity.this, "차량번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //변경해야하는부분;

                if (itemDao.countCode(editTextsItemCode.getText().toString()) > 0) {
                    AlertDialog.Builder saveAD = new AlertDialog.Builder(ItemUpdateActivity.this);
                    saveAD.setIcon(R.mipmap.ic_launcher);
                    saveAD.setTitle("확인");
                    saveAD.setMessage("해당 내용을 업데이트 하시겠습니까?");
                    saveAD.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            itemDao.updateCode(editTextsItemCode.getText().toString()
                                    , editTextsItemName.getText().toString()
                                    //변경해야하는부분 date부분과 Time부분
                                    , editTextItemTime.getText().toString()
                                    , getTime()
                            );
                            editTextItemTime.setText(getTime());
                            Toast.makeText(ItemUpdateActivity.this, "업데이트 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    saveAD.setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    saveAD.show();
                }

                else {
                    Item item = new Item(editTextsItemCode.getText().toString()
                            , editTextsItemName.getText().toString()
                            //변경해야하는부분
                            , editTextItemTime.getText().toString()
                            , getTime()
                    );
                    editTextItemTime.setText(getTime());
                    itemDao.insert(item);

                    Toast.makeText(ItemUpdateActivity.this, "입차 되었습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //출차 버튼 코드
        buttonItemComplete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(item != null)
                {
                    item = new Item(editTextsItemCode.getText().toString()
                            , editTextsItemName.getText().toString()
                            , editTextNowDate.getText().toString()
                            , getTime());
                }
                itemDao.update(item);
            }
            });

        buttonItemOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if () {

                }*/
                // else {
                itemDao.deleteCode(editTextsItemCode.getText().toString());
                //test code
                Toast.makeText(ItemUpdateActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                //  }
            }
        });
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        ;
        Date date = new Date(now);
        SimpleDateFormat nowTime = new SimpleDateFormat("hh:mm");

        String getTime = nowTime.format(date);

        return getTime;
    }

    private String outTime() {
        long now = System.currentTimeMillis();
        ;
        Date date = new Date(now);
        SimpleDateFormat nowTime = new SimpleDateFormat("hh:mm");

        String outTime = nowTime.format(date);

        return outTime;
    }
}
