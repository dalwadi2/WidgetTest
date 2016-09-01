package in.vaksys.widgettest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Harsh on 31-08-2016.
 */
public class ExampleAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "ExampleAppWidgetProvider";

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, "TimeWidgetRemoved id(s):" + appWidgetIds, Toast.LENGTH_SHORT).show();
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled():last widget instance removed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 3 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 3, 1000, pi);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Log.e(TAG, "onUpdate: called");
        ComponentName thisWidget = new ComponentName(context,
                ExampleAppWidgetProvider.class);

        for (int widgetId : appWidgetManager.getAppWidgetIds(thisWidget)) {

            //Get the remote views
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget1);
            // Set the text with the current time.
            remoteViews.setTextViewText(R.id.tvTime, Utility.getCurrentTime("hh:mm:ss a"));
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager, int appWidgetId,
                                          Bundle newOptions) {
        //Do some operation here, once you see that the widget has change its size or position.
        Toast.makeText(context, "onAppWidgetOptionsChanged() called", Toast.LENGTH_SHORT).show();
    }
}