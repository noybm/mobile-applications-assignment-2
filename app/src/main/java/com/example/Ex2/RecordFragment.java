package com.example.Ex2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecordFragment extends Fragment {

    RecyclerView recyclerView;
    CallBack_list onItemClicked;

    public RecordFragment() {
    }

    public static RecordFragment newInstance(CallBack_list onItemClicked) {
        RecordFragment fragment = new RecordFragment();
        fragment.setOnItemClickedCallback(onItemClicked);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.record_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(
                new MyRecordRecyclerViewAdapter(
                        RecordsListDB.getInstance().getRecords(),
                        onItemClicked));
    }

    public void setOnItemClickedCallback(CallBack_list itemClickedCallback) {
        this.onItemClicked = itemClickedCallback;
    }
}