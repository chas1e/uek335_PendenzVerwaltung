package com.uek335.do_too.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.uek335.do_too.R;
import com.uek335.do_too.model.Task;
import com.uek335.do_too.model.TaskViewModel;
import com.uek335.do_too.util.Util;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private final List<Task> tasksList;
    private final OnTodoClickListener todoClickListener;


    public RecyclerViewAdapter(List<Task> tasksList, OnTodoClickListener onTodoClickListener) {
        this.tasksList = tasksList;
        todoClickListener = onTodoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Task task = tasksList.get(position);
        String formatted = Util.formatDate(task.getDueDate());
        holder.task.setText(task.getTask());
        holder.todoChip.setText(formatted);
        holder.priority.setText(task.getPriority().toString());
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public AppCompatTextView task;
        public AppCompatTextView priority;
        public Chip todoChip;

        OnTodoClickListener onTodoClickListener;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        radioButton = itemView.findViewById(R.id.todo_radio_button);
        task = itemView.findViewById(R.id.todo_row_todo);
        priority = itemView.findViewById(R.id.todo_row_priority);
        todoChip = itemView.findViewById(R.id.todo_row_chip);
        this.onTodoClickListener = todoClickListener;
        this.radioButton.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            Task currTask =  tasksList.get(getAdapterPosition());;
            int id = view.getId();
            if(id == R.id.todo_row_layout){
                onTodoClickListener.onTodoClick(currTask);
            }else if (id == R.id.todo_radio_button){
                onTodoClickListener.onTodoRadioButtonClick(currTask);
            }
        }
    }
}
