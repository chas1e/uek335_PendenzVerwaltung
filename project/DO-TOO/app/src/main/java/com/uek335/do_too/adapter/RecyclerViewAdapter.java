package com.uek335.do_too.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.uek335.do_too.R;
import com.uek335.do_too.model.Priority;
import com.uek335.do_too.model.Task;
import com.uek335.do_too.util.Util;

import java.util.List;

//View Adapter für die Recycle View. Ermöglicht uns die Task-Blöcke schön und richtig darzustellen
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private final List<Task> tasksList;
    private final OnTodoClickListener todoClickListener;
    private TextView NoTasks;

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
//füllt Daten in den Block
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task_obj = tasksList.get(position);

        if(task_obj.getDueDate() != null){
            String formatted = Util.formatDate(task_obj.getDueDate());
            holder.todoChip.setText(formatted);
        }else{
            holder.todoChip.setText(R.string.nodate);
        }
//Je nach Priorität eine andere Farbe
        holder.task.setText(task_obj.getTask());
        holder.priority.setText(task_obj.getPriority().toString());
        if(task_obj.getPriority() == Priority.Hoch){
            holder.priority.setTextColor(Color.parseColor("#E53935"));
        }else if(task_obj.getPriority() == Priority.Niedrig){
            holder.priority.setTextColor(Color.parseColor("#43A047"));
        }
        //Streichen falls die Pendenz abgeschlossen wurde
        if(task_obj.isDone){
            holder.task.setPaintFlags(holder.task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    //Falls keine Tasks vorhanden sind wird ein Info Block dargestellt
    public void checkPendencies(Activity activity) {
        NoTasks = activity.findViewById(R.id.NoTasks);
        if(!tasksList.isEmpty()) {
            NoTasks.setVisibility(View.GONE);
        } else {
            NoTasks.setVisibility(View.VISIBLE);
        }
    }
    //View Holder Klasse um Komponenten des Task-Block mit dem Backend zu verlinken
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
        //Handhabt alle Klicks auf die Task-Blöcke
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
