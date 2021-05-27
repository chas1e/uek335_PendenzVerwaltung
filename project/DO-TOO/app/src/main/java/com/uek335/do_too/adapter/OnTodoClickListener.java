package com.uek335.do_too.adapter;

import com.uek335.do_too.model.Task;

public interface OnTodoClickListener {
    void onTodoClick( Task task);
    void onTodoRadioButtonClick(Task task);
}
