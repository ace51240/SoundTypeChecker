package com.example.soundtypechecker;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener, AudioManagerListener.Action, AudioAttributesListener.Action {

    private Context context;
    private Spinner spinnerAudioManager, spinnerAudioAttributes;
    private AudioTypeManager audioTypeManager;
    private SoundPool soundPool;
    private int soundId;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity();

        audioTypeManager = new AudioTypeManager(context);
        soundPool = new SoundPool(15, AudioManager.STREAM_VOICE_CALL, 0);
        soundId = soundPool.load(context, R.raw.se_saa01, 1);

        Button soundButton = (Button) view.findViewById(R.id.sound_button);
        soundButton.setOnClickListener(this);

        RadioButton radioButtonAudioManager = (RadioButton) view.findViewById(R.id.radio_audio_manager);
        radioButtonAudioManager.setOnClickListener(this);
        RadioButton radioButtonAudioAttributes = (RadioButton) view.findViewById(R.id.radio_audio_attributes);
        radioButtonAudioAttributes.setOnClickListener(this);

        spinnerAudioManager = (Spinner) view.findViewById(R.id.spinner_audiomanager);
        String[] audioNameArray = audioTypeManager.getAudioManagerNames();

        ArrayAdapter<String> adapterAudioManager = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, audioNameArray);
        adapterAudioManager.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioManager.setAdapter(adapterAudioManager);
        spinnerAudioManager.setOnItemSelectedListener(new AudioManagerListener(this));

        spinnerAudioAttributes = (Spinner) view.findViewById(R.id.spinner_audioattributes);
        String[] audioAttributesArray = audioTypeManager.getAudioAttributesNames();

        ArrayAdapter<String> adapterAudioAttributes = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, audioAttributesArray);
        adapterAudioAttributes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioAttributes.setAdapter(adapterAudioAttributes);
        spinnerAudioAttributes.setOnItemSelectedListener(new AudioAttributesListener(this));

        spinnerAudioAttributes.setEnabled(false);

        LinearLayout audioNamesArea = (LinearLayout) view.findViewById(R.id.sound_type_name_area);
        for (int i = 0; i < audioNameArray.length; i++) {
            TextView textView = new TextView(context);
            textView.setText(audioNameArray[i]);
            audioNamesArea.addView(textView);
        }

        LinearLayout audioBarArea = (LinearLayout) view.findViewById(R.id.volume_bar_area);

        int[] soundTypeArray = audioTypeManager.getAudioManagerTypes();
        for (int i = 0; i < soundTypeArray.length; i++) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = audioManager.getStreamMaxVolume(i);
            int curVolume = audioManager.getStreamVolume(i);
            SeekBar volControl = new SeekBar(context);
            audioBarArea.addView(volControl);
            volControl.setMax(maxVolume);
            volControl.setProgress(curVolume);
            final int audioType = i;
            volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                    audioManager.setStreamVolume(audioType, arg1, 0);
                }
            });
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sound_button:
                soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case R.id.radio_audio_manager:
                if (((RadioButton) v).isChecked()) {
                    setAudioManager(spinnerAudioManager.getSelectedItemPosition());
                    spinnerAudioManager.setEnabled(true);
                    spinnerAudioAttributes.setEnabled(false);
                }
                break;
            case R.id.radio_audio_attributes:
                if (((RadioButton) v).isChecked()) {
                    setAudioAttributes(spinnerAudioManager.getSelectedItemPosition());
                    spinnerAudioManager.setEnabled(false);
                    spinnerAudioAttributes.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void setAudioManager(int position) {
        String type = (String) spinnerAudioManager.getItemAtPosition(position);
        int stream = audioTypeManager.getAudioManagerType(type);
        soundPool = new SoundPool(1, stream, 0);
        soundId = soundPool.load(context, R.raw.se_saa01, 1);
    }

    @Override
    public void setAudioAttributes(int position) {
        String type = (String) spinnerAudioAttributes.getItemAtPosition(position);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(audioTypeManager.getAudioAttributeType(type))
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(1)
                .build();
        soundId = soundPool.load(context, R.raw.se_saa01, 1);
    }
}