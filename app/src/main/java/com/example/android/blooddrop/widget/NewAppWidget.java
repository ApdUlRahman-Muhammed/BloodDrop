package com.example.android.blooddrop.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.blooddrop.Patient;
import com.example.android.blooddrop.R;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager, String usersList, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // If widget clicked, open the app
        Intent intent = new Intent(context, Patient.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.newAppWidgetDonors, pendingIntent);
        views.setTextViewText(R.id.appwidget_text, usersList);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateUsersAppWidgets(Context context,
                                             AppWidgetManager appWidgetManager, String usersList, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, usersList, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, context.getString(R.string.appwidget_text), appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


