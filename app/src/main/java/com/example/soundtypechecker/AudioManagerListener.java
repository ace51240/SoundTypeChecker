package com.example.soundtypechecker;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

public class AudioManagerListener implements AdapterView.OnItemSelectedListener {

    Action action = null;

    public AudioManagerListener(Context context) {
        action = (Action) context;
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
