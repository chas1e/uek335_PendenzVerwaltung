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

public class BottomSheetFragment extends BottomSheetDialogFragment {
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
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);


        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton.setOnClickListener(view1 -> {
            String task = enterTodo.getText().toString().trim();
            if(!TextUtils.isEmpty(task)){
                Task myTask = new Task(task, "", Priority.Normal, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(myTask);
                this.dismiss();
                enterTodo.setText(null);
            }
        });
    }
}