package com.example.soundtypechecker;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AudioManagerListener.Action, AudioAttributesListener.Action {

    private Context context;
    private SoundPool soundPool;
    private Spinner spinnerAudioManager, spinnerAudioAttributes;
    private AudioTypeManager audioTypeManager;

    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        audioTypeManager = new AudioTypeManager(this);

        soundPool = new SoundPool(15, AudioManager.STREAM_VOICE_CALL, 0);
        soundId = soundPool.load(context, R.raw.se_saa01, 1);

        Button soundButton = (Button) findViewById(R.id.sound_button);
        soundButton.setOnClickListener(this);

        spinnerAudioManager = (Spinner) findViewById(R.id.spinner_audiomanager);
        String[] audioNameArray = audioTypeManager.getAudioManagerNames();

        ArrayAdapter<String> adapterAudioManager = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, audioNameArray);
        adapterAudioManager.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioManager.setAdapter(adapterAudioManager);
        spinnerAudioManager.setOnItemSelectedListener(new AudioManagerListener(this));

        spinnerAudioAttributes = (Spinner) findViewById(R.id.spinner_audioattributes);
        String[] audioAttributesArray = audioTypeManager.getAudioAttributesNames();

        ArrayAdapter<String> adapterAudioAttributes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, audioAttributesArray);
        adapterAudioAttributes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAudioAttributes.setAdapter(adapterAudioAttributes);
        spinnerAudioAttributes.setOnItemSelectedListener(new AudioAttributesListener(this));

        spinnerAudioAttributes.setEnabled(false);

        LinearLayout audioNamesArea = (LinearLayout) findViewById(R.id.sound_type_name_area);
        for (int i = 0; i < audioNameArray.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(audioNameArray[i]);
            audioNamesArea.addView(textView);
        }

        LinearLayout audioBarArea = (LinearLayout) findViewById(R.id.volume_bar_area);

        int[] soundTypeArray = audioTypeManager.getAudioManagerTypes();
        for (int i = 0; i < soundTypeArray.length; i++) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = audioManager.getStreamMaxVolume(i);
            int curVolume = audioManager.getStreamVolume(i);
            SeekBar volControl = new SeekBar(this);
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