package com.example.soundtypechecker;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;

import androidx.annotation.StringRes;

public class AudioTypeManager {

    Context context;

    AudioTypeManager(Context context) {
        this.context = context;
    }

    private enum SoundType {
        STREAM_VOICE_CALL(AudioManager.STREAM_VOICE_CALL, R.string.voice_call),
        STREAM_SYSTEM(AudioManager.STREAM_SYSTEM, R.string.system),
        STREAM_RING(AudioManager.STREAM_RING, R.string.ring),
        STREAM_MUSIC(AudioManager.STREAM_MUSIC, R.string.music),
        STREAM_ALARM(AudioManager.STREAM_ALARM, R.string.alarm),
        STREAM_NOTIFICATION(AudioManager.STREAM_NOTIFICATION, R.string.notification),
        STREAM_DTMF(AudioManager.STREAM_DTMF, R.string.dtmf);

        int type;
        @StringRes
        int name;

        SoundType(int type, @StringRes int name) {
            this.type = type;
            this.name = name;
        }
    }

    private enum AudioAttributesType {

        USAGE_MEDIA(AudioAttributes.USAGE_MEDIA, R.string.media),
        USAGE_VOICE_COMMUNICATION(AudioAttributes.USAGE_VOICE_COMMUNICATION, R.string.voice_communication),
        USAGE_VOICE_COMMUNICATION_SIGNALLING(AudioAttributes.USAGE_VOICE_COMMUNICATION_SIGNALLING, R.string.voice_communication_signalling),
        USAGE_ALARM(AudioAttributes.USAGE_ALARM, R.string.alarm),
        USAGE_NOTIFICATION(AudioAttributes.USAGE_NOTIFICATION, R.string.notification),
        USAGE_NOTIFICATION_RINGTONE(AudioAttributes.USAGE_NOTIFICATION_RINGTONE, R.string.notification_ringtone),
        USAGE_NOTIFICATION_COMMUNICATION_REQUEST(AudioAttributes.USAGE_NOTIFICATION_COMMUNICATION_REQUEST, R.string.notification_communication_request),
        USAGE_NOTIFICATION_COMMUNICATION_INSTANT(AudioAttributes.USAGE_NOTIFICATION_COMMUNICATION_INSTANT, R.string.notification_communication_instant),
        USAGE_NOTIFICATION_COMMUNICATION_DELAYED(AudioAttributes.USAGE_NOTIFICATION_COMMUNICATION_DELAYED, R.string.notification_communication_delayed),
        USAGE_NOTIFICATION_EVENT(AudioAttributes.USAGE_NOTIFICATION_EVENT, R.string.notification_event),
        USAGE_ASSISTANCE_ACCESSIBILITY(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY, R.string.assistance_accessibility),
        USAGE_ASSISTANCE_NAVIGATION_GUIDANCE(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE, R.string.assistance_navigation_guidance),
        USAGE_ASSISTANCE_SONIFICATION(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION, R.string.assistance_sonification),
        USAGE_GAME(AudioAttributes.USAGE_GAME, R.string.game),
        USAGE_ASSISTANT(AudioAttributes.USAGE_ASSISTANT, R.string.assistant);

        int type;
        @StringRes
        int name;

        AudioAttributesType(int type, @StringRes int name) {
            this.type = type;
            this.name = name;
        }
    }

    public int getAudioManagerType(String s) {
        for (SoundType soundType : SoundType.values()) {
            if (context.getString(soundType.name).equals(s)) {
                return soundType.type;
            }
        }
        return 0;
    }

    public int getAudioAttributeType(String s) {
        for (AudioAttributesType soundType : AudioAttributesType.values()) {
            if (context.getString(soundType.name).equals(s)) {
                return soundType.type;
            }
        }
        return 0;
    }

    public int[] getAudioManagerTypes() {
        int[] soundTypeArray = new int[SoundType.values().length];
        int i = 0;
        for (SoundType soundType : SoundType.values()) {
            soundTypeArray[i++] = soundType.type;
        }
        return soundTypeArray;
    }

    public String[] getAudioManagerNames() {
        String[] soundsTypeArray = new String[SoundType.values().length];
        int i = 0;
        for (SoundType soundType : SoundType.values()) {
            soundsTypeArray[i++] = context.getString(soundType.name);
        }
        return soundsTypeArray;
    }

    public String[] getAudioAttributesNames() {
        String[] audioAttributesArray = new String[AudioAttributesType.values().length];
        int i = 0;
        for (AudioAttributesType audioAttributesType : AudioAttributesType.values()) {
            audioAttributesArray[i++] = context.getString(audioAttributesType.name);
        }
        return audioAttributesArray;
    }
}
