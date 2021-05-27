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
import android.widget.PopupMenu;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.uek335.do_too.model.Priority;
import com.uek335.do_too.model.SharedViewModel;
import com.uek335.do_too.model.Task;
import com.uek335.do_too.model.TaskViewModel;
import com.uek335.do_too.util.Util;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;
import java.util.Calendar;
import java.util.Date;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton enterDescription;
    private ImageButton priorityButton;
    private EditText description;
    private CalendarView calendarView;
    private ImageButton saveButton;
    private Group calendarGroup;
    private Date dueDate;
    private Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority;

    //Empty Constructor der notwendig ist
    public BottomSheetFragment(){
    }

    //Sobald der Bottom Dialog aufgerufen wird, werden alle Buttons und Textfelder usw hier bereitgestellt und verknüpft
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
        enterDescription = view.findViewById(R.id.enter_description);
        description = view.findViewById(R.id.description);


        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);

        return view;
    }
    //Vor allem wichtig für den Edit. Befüllt daten von Task in die Felder des Dialogs
    @Override
    public void onResume(){
        super.onResume();
        if(sharedViewModel.getSelectedItem().getValue() != null){
            isEdit = sharedViewModel.isEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
            dueDate = task.getDueDate();
            description.setText(task.getTaskDescription());
            priority = task.getPriority();

        }
    }

    // Pop up Menü für die Priorität
    private void showMenu(View view, @MenuRes int menuRes){
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(menuItem -> {
            priority = Priority.valueOf(menuItem.getTitle().toString());
            return true;
        });

    }

    //Alles was mit abspeichern und schreiben von einer Pendenz zu tun hat. Der ganze Dialog mit allen Funktionen und Features
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        priorityButton.setOnClickListener(view12 -> {
            showMenu(view12, R.menu.priority_menu);
        });
        calendarButton.setOnClickListener(view12 -> calendarGroup.setVisibility(
                calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        calendarGroup.setVisibility(View.GONE);
        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
            Util.hideSoftKeyboard(view);
        });
        enterDescription.setOnClickListener(view13 -> description.setVisibility(description.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        description.setVisibility(View.GONE);
        saveButton.setOnClickListener(view1 -> {
            if(priority == null){
                priority = Priority.Normal;
            }
            String task = enterTodo.getText().toString().trim();
            String description_text = description.getText().toString().trim();
            if(!TextUtils.isEmpty(task)){
                Task myTask = new Task(task, description_text, priority, dueDate, Calendar.getInstance().getTime(), false);
                if (isEdit){
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();
                    updateTask.setTask(task);
                    updateTask.setDateCreated((Calendar.getInstance().getTime()));
                    updateTask.setDueDate(dueDate);
                    updateTask.setPriority(priority);
                    updateTask.setTaskDescription(description_text);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setEdit(false);
                    this.dueDate = null;
                    this.enterTodo.setText("");
                    this.description = null;
                    this.priority = null;
                    this.dismiss();
                }else{
                    TaskViewModel.insert(myTask);
                    this.dueDate = null;
                    this.enterTodo.setText("");
                    this.description = null;
                    this.priority = null;
                    this.dismiss();
                }
            }else {
                //Error Message für Titel der Pendenz
                enterTodo.setError(getResources().getString(R.string.error));
            }
        });


    }

    //Handhabt unsere Klicke im Dialog
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