package com.uek335.do_too.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//Eine Klasse die uns Hilft Tasks zwischen dem Dialog und der Main Acticty zu transportieren. Notweniding fuer update
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedItem = new MutableLiveData<>();
    private boolean isEdit;

    public void selectItem(Task task){
        selectedItem.setValue(task);
    }
    public LiveData<Task> getSelectedItem(){return selectedItem;}

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
