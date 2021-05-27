package com.uek335.do_too.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.uek335.do_too.model.Priority;
import com.uek335.do_too.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern(("dd/MM/yyyy"));
        return simpleDateFormat.format(date);
    }
    public static void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static int priorityColor(Task task){
        int color;
        if(task.getPriority() == Priority.Hoch){
            color = Color.argb(255, 57, 53, 1);
        }else if(task.getPriority() == Priority.Niedrig){
            color = Color.argb(255, 160, 71, 1);
        }else{
            color = Color.argb(255, 50, 56, 1);
        }
        return color;
    }
}
