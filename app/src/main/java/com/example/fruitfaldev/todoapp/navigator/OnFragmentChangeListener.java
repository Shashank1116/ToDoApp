package com.example.fruitfaldev.todoapp.navigator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

public interface OnFragmentChangeListener {
    void onFragmentAdd(Fragment fragment, String TAG);
    void onFragmentReplace(Fragment fragment, String TAG);
    void onFragmentRemove(Fragment fragment, String TAG);
}