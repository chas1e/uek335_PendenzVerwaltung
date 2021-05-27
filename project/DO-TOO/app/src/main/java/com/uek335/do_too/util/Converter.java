package com.uek335.do_too.util;

import androidx.room.TypeConverter;

import com.uek335.do_too.model.Priority;

import java.util.Date;

//Hilft uns verschiedene Werte wie z.B. Daten oder Prioritaeten in der Datenbank zu Speichern
public class Converter {

    @TypeConverter
    public static Date fromTimeStamp(Long value){
       return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority){
        return priority == null ? null : Priority.valueOf(priority);
    }

}
