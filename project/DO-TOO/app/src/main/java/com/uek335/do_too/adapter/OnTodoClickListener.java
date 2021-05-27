package com.uek335.do_too.adapter;

import com.uek335.do_too.model.Task;

//Todod click listener, hilft uns Klicks auf dem task-block und auf dem fertig-knopf zu handhaben
public interface OnTodoClickListener {
    void onTodoClick( Task task);
    void onTodoRadioButtonClick(Task task);
}
