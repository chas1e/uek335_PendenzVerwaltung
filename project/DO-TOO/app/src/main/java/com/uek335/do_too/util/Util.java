package com.uek335.do_too.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.uek335.do_too.model.Priority;
import com.uek335.do_too.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
//Util ist eine Support-Klasse welche uns tolle Methoden zur verfuegung stellt
public class Util {
    //Um das Datum im richtigen Format darzustellen
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern(("dd/MM/yyyy"));
        return simpleDateFormat.format(date);
    }
    //Um das Keyboard bei Knopfdruecken verschwinden zu lassen
    public static void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
