package com.example.soundtypechecker;

import android.view.View;
import android.widget.AdapterView;

public class AudioAttributesListener implements AdapterView.OnItemSelectedListener {

    Action action = null;

    public AudioAttributesListener(Action action) {
        this.action = action;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        action.setAudioAttributes(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface Action {
        void setAudioAttributes(int position);
    }
}
