package com.zhenqiangli.shakespeare.util;

import android.util.Log;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by zhenqiangli on 8/4/17.
 */

public class TimeUtil {

  private static final String TAG = "TimeUtil";

  public static long now() {
    long res = GregorianCalendar.getInstance().getTimeInMillis() / 1000;
    Log.d(TAG, "now: " + res);
    return res;
  }

  public static String getDate(long time) {
    Log.d(TAG, "getDate: " + time);
    Calendar cal = GregorianCalendar.getInstance();
    cal.setTimeInMillis(time * 1000);
    return String.format(
        "%s/%s/%s",
        cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.DAY_OF_MONTH),
        cal.get(Calendar.YEAR));
  }
}
