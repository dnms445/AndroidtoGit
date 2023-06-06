package com.jin.androidfreeerp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jin.androidfreeerp.databinding.ActivityItemBinding;
import com.jin.androidfreeerp.databinding.ActivityItemSearchBinding;

import java.util.List;

public class ItemSearchActivity extends AppCompatActivity {

    private ActivityItemSearchBinding binding;
    private List<Item> itemList;
    private ItemDao itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_item_search);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_item_search);

        AppDatabase itemDb = Room.databaseBuilder(this, AppDatabase.class, "Item-db")
                .allowMainThreadQueries()
                .build();
        itemDao = itemDb.itemDao();
        //itemList.clear();
        itemList = itemDao.getAll();

        binding.recyclerViewItem.setAdapter(new RecyclerviewAdapter());
        binding.recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(ItemSearchActivity.this, "아이템수 :"+itemList.size(), Toast.LENGTH_SHORT).show();
    }

    public class RecyclerviewAdapter extends RecyclerView.Adapter<ItemSearchActivity.RecyclerviewAdapter.ViewHolder>{

        @NonNull
        @Override
        public ItemSearchActivity.RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
            return new ItemSearchActivity.RecyclerviewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemSearchActivity.RecyclerviewAdapter.ViewHolder holder, int position) {
            String reTextViewsItemCode;
            String reTextViewsItemName;
            reTextViewsItemCode = itemList.get(position).getItemCode();
            reTextViewsItemName = itemList.get(position).getItemName();

            holder.reTextViewsItemCode.setText((CharSequence) itemList.get(position).getItemCode());
            holder.reTextViewsItemName.setText((CharSequence) itemList.get(position).getItemName());
            holder.reTextViewStock.setText((CharSequence) Integer.toString(itemList.get(position).getStock()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openVideoActivity(reTextViewsItemCode, reTextViewsItemName );
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView reTextViewsItemCode;
            private TextView reTextViewsItemName;
            private TextView reTextViewStock;
            public ViewHolder(View view){
                super(view);
                reTextViewsItemCode = view.findViewById(R.id.reTextViewsItemCode);
                reTextViewsItemName = view.findViewById(R.id.reTextViewsItemName);
                reTextViewStock = view.findViewById(R.id.reTextViewStock);
            }
        }

        public void openVideoActivity(String itemCode, String itemName) {
            Intent intent = getIntent();
            intent.putExtra("itemIdReturnCode", itemCode);
            intent.putExtra("itemIdReturnName", itemName);
            setResult(RESULT_OK,intent);
            finish();
        }

    }

}