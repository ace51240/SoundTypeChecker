package com.example.soundtypechecker;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context context;
    private SoundPool soundPool;
    private Spinner spinnerAudioManager, spinnerAudioAttributes;
    private AudioTypeManager audioTypeManager;
    private AudioManager audioManager;

    private int soundId;

    private boolean isAudioManager = true;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        audioTypeManager = new AudioTypeManager(this);

        soundPool = new SoundPool(15, AudioManager.STREAM_VOICE_CALL, 0);
        soundId = soundPool.load(context, R.raw.se_saa01, 1);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        spinnerAudioManager = (Spinner) findViewById(R.id.spinner_audiomanager);
        String[] soundsTypeArray = audioTypeManager.getAudioManagerNames();

        ArrayAdapter<String> adapterAudioManager = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, soundsTypeArray);
        adapterAudioManager.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioManager.setAdapter(adapterAudioManager);
        spinnerAudioManager.setOnItemSelectedListener(this);

        spinnerAudioAttributes = (Spinner) findViewById(R.id.spinner_audioattributes);
        String[] audioAttributesArray = audioTypeManager.getAudioAttributesNames();

        ArrayAdapter<String> adapterAudioAttributes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, audioAttributesArray);
        adapterAudioAttributes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioAttributes.setAdapter(adapterAudioAttributes);
        spinnerAudioAttributes.setOnItemSelectedListener(this);

        spinnerAudioAttributes.setEnabled(false);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
                break;
            case R.id.radio_audiomanager:
                if (((RadioButton) v).isChecked()) {
                    isAudioManager = true;
                    setupSoundpool(spinnerAudioManager.getSelectedItemPosition());
                    spinnerAudioManager.setEnabled(true);
                    spinnerAudioAttributes.setEnabled(false);
                }
                break;
            case R.id.radio_audioattributes:
                if (((RadioButton) v).isChecked()) {
                    isAudioManager = false;
                    setupSoundpool(spinnerAudioManager.getSelectedItemPosition());
                    spinnerAudioManager.setEnabled(false);
                    spinnerAudioAttributes.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setupSoundpool(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setupSoundpool(int position) {
        if (isAudioManager) {
            String type = (String) spinnerAudioManager.getItemAtPosition(position);
            int stream = audioTypeManager.getAudioManagerType(type);
            soundPool = new SoundPool(1, stream, 0);
            soundId = soundPool.load(context, R.raw.se_saa01, 1);
            int volume = audioManager.getStreamVolume(stream);
            int volumeMax = audioManager.getStreamMaxVolume(stream);

            setVolumeProgress(volume, volumeMax);
        } else {
            String type = (String) spinnerAudioAttributes.getItemAtPosition(position);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(audioTypeManager.getAudioAttributeType(type))
                    .build();
            SoundPool soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(1)
                    .build();
            soundId = soundPool.load(context, R.raw.se_saa01, 1);
        }
    }

    public void setVolumeProgress(int volume, int volumeMax) {
        progressBar.setMax(volumeMax);
        progressBar.setProgress(volume);
    }
}