package com.example.android.blooddrop.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class WidgetUpdateService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String ACTION_WIDGET_DONORS_UPDATE = "com.example.android.blooddrop.action.update_widget";
    public static final String EXTRA_WIDGET_DONORS = "com.example.android.blooddrop.extra.widget_donors";

    public WidgetUpdateService() {
        super("com.example.android.blooddrop.widget.WidgetUpdateService");
    }

    public static void startWidgetUpdate(Context context, String DonorsList) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra(EXTRA_WIDGET_DONORS, DonorsList);
        intent.setAction(ACTION_WIDGET_DONORS_UPDATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WIDGET_DONORS_UPDATE.equals(action)) {
                String DonorsList = intent.getStringExtra(EXTRA_WIDGET_DONORS);
                handleActionUpdateWidgetRecipe(DonorsList);
            }
        }
    }

    private void handleActionUpdateWidgetRecipe(String donorsList) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NewAppWidget.class));
        NewAppWidget.updateUsersAppWidgets(this, appWidgetManager, donorsList, appWidgetIds);
    }
}
