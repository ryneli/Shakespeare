package com.zhenqiangli.shakespeare.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.zhenqiangli.shakespeare.R;
import com.zhenqiangli.shakespeare.main.MainActivity;

/**
 * Created by zhenqiangli on 8/7/17.
 */

public class DailyVerseService extends IntentService {

  private static final String TAG = "DailyVerseService";

  public DailyVerseService() {
    super(TAG);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    Log.d(TAG, "onHandleIntent: " + intent);
    // sendNotification();
    // disableMainActivity();
  }

  private void sendNotification() {
    int notificationId = 001;
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.shakespeare_icon)
            .setContentTitle("Shakespeare")
            .setContentText("Subtitle");
    NotificationManager notificationManager = (NotificationManager) getSystemService(
        NOTIFICATION_SERVICE);
    notificationManager.notify(notificationId, builder.build());
  }

  private void disableMainActivity() {
    PackageManager pm = getPackageManager();
    pm.setComponentEnabledSetting(
        new ComponentName(this, MainActivity.class),
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);
  }
}
