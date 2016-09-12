package chengxinlun.github.io.shakingclock;


import chengxinlun.github.io.shakingclock.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class ShakingClockSetting extends Activity
{
    private ShakingClockService service;
    private Intent intent;
    private boolean isServiceRunning = false;
    private int threshold = 25;
    private int interval = 2;
    private SeekBar slideThreshold;
    private SeekBar slideInterval;


@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaking_clock_setting);
        slideThreshold = (SeekBar) findViewById(R.id.slideThreshold);
        slideThreshold.setProgress(threshold - 15);
        slideThreshold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                threshold = progress + 15;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        slideInterval = (SeekBar) findViewById(R.id.slideInterval);
        slideInterval.setProgress(interval - 1);
        slideInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                interval = progress + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        intent = new Intent(this, ShakingClockService.class);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    public void startService(View v)
    {
        Button buttonControl = (Button) findViewById(R.id.buttonControl);
        if (!isServiceRunning)
        {
            intent.putExtra("threshold", threshold);
            intent.putExtra("interval", interval);
            startService(intent);
            isServiceRunning = true;
            buttonControl.setText(R.string.buttonControlStopService);
            slideThreshold.setEnabled(false);
            slideInterval.setEnabled(false);
        }
        else
        {
            stopService(intent);
            isServiceRunning = false;
            buttonControl.setText(R.string.buttonControlStartService);
            slideThreshold.setEnabled(true);
            slideInterval.setEnabled(true);
        }
    }
}
