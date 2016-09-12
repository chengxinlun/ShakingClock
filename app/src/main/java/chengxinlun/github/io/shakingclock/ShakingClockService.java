package chengxinlun.github.io.shakingclock;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Cheng Xinlun on 2016/9/11.
 */
public class ShakingClockService extends Service implements SensorEventListener, TextToSpeech.OnInitListener
{
    private SensorManager sm;
    private Sensor mAcce;
    private TextToSpeech tts;
    private int threshold = 25;
    private int interval = 2;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcce = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tts = new TextToSpeech(this, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        if (intent.getExtras() != null)
        {
            threshold = intent.getIntExtra("threshold", 25);
            interval = intent.getIntExtra("interval", 2);
        }
        sm.registerListener(this, mAcce, 100000 * interval);
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        sm.unregisterListener(this);
        tts.shutdown();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            double acce = Math.sqrt(event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2]);
            if (acce >= (double)threshold)
                sayTime();
        }
    }

    @Override
    public void onInit(int status)
    {
        if(status == TextToSpeech.SUCCESS)
        {
            int result=tts.setLanguage(new Locale(Locale.getDefault().getLanguage()));
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("Error/TTS", "This Language is not supported");
            }
            else
            {
                Log.d("Debug/TTS", "Complete");
            }
        }
        else
            Log.e("Error/TTS", "Initialization Failed!");
    }

    private void sayTime()
    {
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
        Calendar now = Calendar.getInstance();
        Date clocalTime = now.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm:ss");
        String localTime = date.format(clocalTime);
        String id = date.hashCode() + "";
        tts.speak(localTime, TextToSpeech.QUEUE_FLUSH, null, id);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_SHOW_UI);
    }
}
