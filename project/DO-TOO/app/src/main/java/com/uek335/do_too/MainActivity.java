package com.uek335.do_too;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.uek335.do_too.adapter.OnTodoClickListener;
import com.uek335.do_too.adapter.RecyclerViewAdapter;
import com.uek335.do_too.model.SharedViewModel;
import com.uek335.do_too.model.Task;
import com.uek335.do_too.model.TaskViewModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Jonathan und David
 * Dies ist die main activity unseres Pendenz Verwaltung Systems
 */
public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    /*
    Hier werden wichtige felder und variablen erstellt
     */
    private TaskViewModel taskViewModel;
    public static final String TAG = "ITEM";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    /*
    Dies ist die Methode, welche aufgerufen wird, wenn die Applikation startet. Das ist im Manifest festgelegt. Buttons, toolbar und alles andere wird hier "gefunden" und bereitgestellt.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);

        sharedViewModel= new ViewModelProvider(this).get(SharedViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
        recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.checkPendencies(this);
        });

        //Dies ist der floating action button, er ist unten rechts zu finden. Er erlaubt uns, neue Pendenzen zu erfassen.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            showBottomSheetDialog();
        });
    }

    //Das Fragment, auch als Bottom Sheet Dialog wird hier aufgerufen. So kann man mit dem FAB ein Dialogfeld öffen, welche uns erlaubt, neue Pendenzen zu kreeiren.
    private void showBottomSheetDialog(){
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    //Ist zuständig für den Klich auf die Pendenz um den Editor zu öffnen
    @Override
    public void onTodoClick(Task task) {
        sharedViewModel.selectItem(task);
        sharedViewModel.setEdit(true);
        showBottomSheetDialog();
    }

    //Ist zuständig für den Klick auf den Radio Button. Beendet eine Pendenz, streicht diese durch und macht ein update in der Datenbank
    @Override
    public void onTodoRadioButtonClick(Task task) {
        Task update = task;

        if(task.isDone){
            update.setDone(false);
            TaskViewModel.update(update);
        }else{
            update.setDone(true);
            TaskViewModel.update(update);
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }


}