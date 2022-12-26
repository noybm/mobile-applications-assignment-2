package com.example.Ex2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Ex2.databinding.FragmentRecordItemBinding;

import java.util.List;


public class MyRecordRecyclerViewAdapter extends RecyclerView.Adapter<MyRecordRecyclerViewAdapter.RecordViewHolder> {

    private final List<Record> records;
    private final CallBack_list onItemClicked;

    public MyRecordRecyclerViewAdapter(List<Record> items, CallBack_list onItemClicked) {
        records = items;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RecordViewHolder(FragmentRecordItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final RecordViewHolder holder, int position) {
        Log.d("RecordRecyclerViewAdapter", "onBindViewHolder: " + position);
        Record record = records.get(position);

        holder.playerName.setText(record.getName());
        holder.playerScore.setText(""+record.getScore());
        holder.itemView.setOnClickListener(v -> onItemClicked.func(record));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class RecordViewHolder extends RecyclerView.ViewHolder {
        public final TextView playerName;
        public final TextView playerScore;

        public RecordViewHolder(FragmentRecordItemBinding binding) {
            super(binding.getRoot());
            playerName = binding.playerLBLName;
            playerScore = binding.playerLBLScore;
        }

        @Override
        public String toString() {
            return "RecordViewHolder{" +
                    "playerName=" + playerName +
                    ", playerScore=" + playerScore +
                    '}';
        }
    }
}