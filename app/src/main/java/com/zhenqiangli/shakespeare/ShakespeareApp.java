package com.zhenqiangli.shakespeare;

import android.app.Application;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by zhenqiangli on 8/8/17.
 */

public class ShakespeareApp extends Application {

  private Executor backgroundExecutor = Executors.newSingleThreadExecutor();

  @Override
  public void onCreate() {
    super.onCreate();
  }

  public Executor getBackgroundExecutor() {
    return backgroundExecutor;
  }
}
