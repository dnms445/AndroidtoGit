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
    /*private TextView mTimeTextView;
    private Button buttonItemStop;
    private Button buttonItemStart;
    private Button buttonItemReset;
    private Thread timeThread = null;
    private boolean isRunning = true;*/

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
        /*long now = System.currentTimeMillis();;
        Date date = new Date(now);
        SimpleDateFormat nowTime = new SimpleDateFormat("hh:mm");

        String getTime = nowTime.format(date);*/

        Intent intent = getIntent();
        String sItemCode = intent.getExtras().getString("itemId");
        if(sItemCode.equals("")){
            if (itemDao.countCode() > 0)
                editTextsItemCode.setText(Integer.toString(Integer.parseInt(itemDao.newCode()) + 1));
        }else {
            //업데이드 후 보여주는 데이터
            //timeThread = new Thread(new timeThread());
            editTextsItemCode.setText(sItemCode);
            itemList = itemDao.selectCode(editTextsItemCode.getText().toString());
            editTextsItemName.setText(itemList.get(0).getItemName());
            //update에 표기됨
            editTextNowDate.setText(itemList.get(0).getItemDate());
            //변경한 부분
            if (!itemList.isEmpty()) {
                Item firstItem = itemList.get(0);
                String itemTime = firstItem.getItemTime(); // getItemTime() 메서드로 시간 값을 가져옵니다.
                editTextItemTime.setText(itemTime);
            }
        }

        //test code
        /*mTimeTextView = findViewById(R.id.timer);
        buttonItemStop = findViewById(R.id.buttonItemStop);
        buttonItemStart = findViewById(R.id.buttonItemStart);
        buttonItemReset = findViewById(R.id.buttonItemReset);*/

        buttonItemSave.setOnClickListener(new View.OnClickListener(){
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
                                    , editTextNowDate.getText().toString()
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
                } else {
                    Item item = new Item(editTextsItemCode.getText().toString()
                            , editTextsItemName.getText().toString()
                            //변경해야하는부분
                            , editTextNowDate.getText().toString()
                            , getTime()
                    );
                    editTextItemTime.setText(getTime());
                    itemDao.insert(item);

                    Toast.makeText(ItemUpdateActivity.this, "입차되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //출차 버튼 코드
        buttonItemComplete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String itemCode = editTextsItemCode.getText().toString();
                String itemName = editTextsItemName.getText().toString();
                String nowDate = editTextNowDate.getText().toString();
                String itemTime = outTime();

                // 기존 데이터 조회
                Item existingItem = itemDao.getItemByCode(itemCode);

                // 데이터 수정
                if (existingItem != null) {
                    existingItem.setItemName(itemName);
                    existingItem.setNowDate(nowDate);
                    existingItem.setItemTime(itemTime);

                    // 수정된 데이터 업데이트
                    itemDao.update(existingItem);
                }
                editTextNowDate.setText(outTime());


                Toast.makeText(ItemUpdateActivity.this, "출차 되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        buttonItemOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                itemDao.deleteCode(editTextsItemCode.getText().toString());

                Toast.makeText(ItemUpdateActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                //  }
            }
        });

        //test code
       /* buttonItemStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                timeThread.interrupt();
            }
        });
        buttonItemReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                    timeThread.interrupt();
            }
        });
        buttonItemStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //test code
                timeThread = new Thread(new timeThread());
                timeThread.start();
            }
        });*/
    }
    //test code
    /*@SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //100이 1초 100*60 은 1분 100*60*10은 10분 100*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", hour, min, sec);
            if (result.equals("00:01:15")) {
                Toast.makeText(ItemUpdateActivity.this, "1분 15초가 지났습니다.", Toast.LENGTH_SHORT).show();
            }
            mTimeTextView.setText(result);
        }
    };
    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTimeTextView.setText("");
                                mTimeTextView.setText("00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }*/
    private String getTime(){
        long now = System.currentTimeMillis();;
        Date date = new Date(now);
        SimpleDateFormat nowTime = new SimpleDateFormat("hh:mm");

        String getTime = nowTime.format(date);

        return getTime;
    }
    private String outTime(){
        long now = System.currentTimeMillis();;
        Date date = new Date(now);
        SimpleDateFormat nowTime = new SimpleDateFormat("hh:mm");

        String outTime = nowTime.format(date);

        return outTime;
    }
}
