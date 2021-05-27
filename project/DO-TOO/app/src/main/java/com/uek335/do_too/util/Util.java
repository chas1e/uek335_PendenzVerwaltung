package com.uek335.do_too.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern(("dd/MM/yyyy"));
        return simpleDateFormat.format(date);
    }
}
