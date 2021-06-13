package com.example.soundtypechecker;

import android.view.View;
import android.widget.AdapterView;

public class AudioManagerListener implements AdapterView.OnItemSelectedListener {

    Action action = null;

    public AudioManagerListener(Action action) {
        this.action = action;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        action.setAudioManager(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface Action {
        void setAudioManager(int position);
    }
}
