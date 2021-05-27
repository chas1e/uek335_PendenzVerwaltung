package com.uek335.do_too;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.uek335.do_too.model.Priority;
import com.uek335.do_too.model.Task;
import com.uek335.do_too.model.TaskViewModel;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;


import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private ImageButton description;
    private Menu priorityPicker;
    private EditText selectedPriority;
    private int selectedButtonId;
    private CalendarView calendarView;
    private ImageButton saveButton;
    private Group calendarGroup;
    private Date dueDate;
    private Calendar calendar = Calendar.getInstance();


    public BottomSheetFragment(){

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        //priorityPicker = view.findViewById((R.id.priority_picker));

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarButton.setOnClickListener(view12 -> calendarGroup.setVisibility(
                calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        calendarGroup.setVisibility(View.GONE);
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
        });

        saveButton.setOnClickListener(view1 -> {
            String task = enterTodo.getText().toString().trim();
            if(!TextUtils.isEmpty(task) && dueDate != null){
                Task myTask = new Task(task, "", Priority.Normal, dueDate, Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(myTask);
                this.dismiss();
                enterTodo.setText(null);
                dueDate = null;
            }
        });


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        calendar = Calendar.getInstance();
        if (id == R.id.today_chip){
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
        }else if(id == R.id.tomorrow_chip){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
        }else if(id == R.id.next_week_chip){
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
        }
    }
}