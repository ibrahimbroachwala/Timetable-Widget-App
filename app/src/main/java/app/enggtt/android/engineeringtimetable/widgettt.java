package app.enggtt.android.engineeringtimetable;

        import android.app.PendingIntent;
        import android.appwidget.AppWidgetManager;
        import android.appwidget.AppWidgetProvider;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.opengl.Visibility;
        import android.os.Build;
        import android.preference.PreferenceManager;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.widget.RemoteViews;
        import android.widget.TextView;

        import com.google.android.gms.ads.InterstitialAd;

        import java.util.Calendar;





/**
 * Implementation of App Widget functionality.
 */
public class widgettt extends AppWidgetProvider {


    public static final int[] times = {R.id.time1, R.id.time2, R.id.time3, R.id.time4, R.id.time5, R.id.time6, R.id.time7, R.id.time8 };
    //public static final int[] types = {R.id.type1, R.id.type2, R.id.type3, R.id.type4, R.id.type5, R.id.type6, R.id.type7, R.id.type8 };
    public static final int[] teachers = {R.id.teacher1, R.id.teacher2, R.id.teacher3, R.id.teacher4, R.id.teacher5, R.id.teacher6, R.id.teacher7, R.id.teacher8 };
    public static final int[] subjects = {R.id.lecture1, R.id.lecture2, R.id.lecture3, R.id.lecture4, R.id.lecture5, R.id.lecture6, R.id.lecture7, R.id.lecture8 };
    public static final int[] rooms = {R.id.room1, R.id.room2, R.id.room3, R.id.room4, R.id.room5, R.id.room6, R.id.room7, R.id.room8 };


    static String action1 = "my.package.PREV";
    static String action2 = "my.package.NEXT";
    final String[] Days_weekends = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};
    final String[] Days_weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    int dayhandler;
    int ID;




    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        final Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spedit = sp.edit();
        Boolean bool = sp.getBoolean("weekend_toggle",true);
        ID=appWidgetId;


        if(bool)
            dayhandler=day;
        else {
            if (day == 1 || day == 7)
                day = 2;
            dayhandler = day;
        }

        spedit.putInt("Day",dayhandler);
        spedit.apply();
        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.timetable);
        String temp = sp.getString("widget_columns","Four");
        String tempcolor = sp.getString("widget_theme","Blue");
        switch (temp){
            case "Two":
                switch(tempcolor){
                    case "Blue": views = new RemoteViews(context.getPackageName(), R.layout.timetable_columns_2);
                        break;
                    case "White":views = new RemoteViews(context.getPackageName(), R.layout.white_tt_2);
                        break;
                    case "Black": views = new RemoteViews(context.getPackageName(), R.layout.black_tt_2);
                        break;
                    case "Red": views = new RemoteViews(context.getPackageName(), R.layout.red_tt_2);
                        break;
                    case "Yellow": views = new RemoteViews(context.getPackageName(), R.layout.yellow_tt_2);
                        break;
                    case "Green": views = new RemoteViews(context.getPackageName(), R.layout.green_tt_2);
                        break;
                }
                break;
            case "Three":  switch(tempcolor){
                case "Blue": views = new RemoteViews(context.getPackageName(), R.layout.timetable_columns_3);
                    break;
                case "White":views = new RemoteViews(context.getPackageName(), R.layout.white_tt_3);
                    break;
                case "Black": views = new RemoteViews(context.getPackageName(), R.layout.black_tt_3);
                    break;
                case "Red": views = new RemoteViews(context.getPackageName(), R.layout.red_tt_3);
                    break;
                case "Yellow": views = new RemoteViews(context.getPackageName(), R.layout.yellow_tt_3);
                    break;
                case "Green": views = new RemoteViews(context.getPackageName(), R.layout.green_tt_3);
                    break;
            }
                break;
            case "Four":  switch(tempcolor){
                case "Blue": views = new RemoteViews(context.getPackageName(), R.layout.timetable);
                    break;
                case "White":views = new RemoteViews(context.getPackageName(), R.layout.white_tt_4);
                    break;
                case "Black": views = new RemoteViews(context.getPackageName(), R.layout.black_tt_4);
                    break;
                case "Red": views = new RemoteViews(context.getPackageName(), R.layout.red_tt_4);
                    break;
                case "Yellow": views = new RemoteViews(context.getPackageName(), R.layout.yellow_tt_4);
                    break;
                case "Green": views = new RemoteViews(context.getPackageName(), R.layout.green_tt_4);
                    break;
            }
                break;
        }

        ttcases(dayhandler,views,context,appWidgetId);

        Intent configintent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,configintent,0);
        views.setOnClickPendingIntent(R.id.bconfig,pendingIntent);


        if(bool)
        views.setTextViewText(R.id.appwidget_text, Days_weekends[dayhandler-1]);
        else
        views.setTextViewText(R.id.appwidget_text,Days_weekdays[dayhandler-2]);
        views.setTextViewText(R.id.title,sp.getString("title",""));
        views.setTextViewText(R.id.wcolumn1,sp.getString("key_column1","Subject"));
        views.setTextViewText(R.id.wcolumn2,sp.getString("key_column2","Teacher"));
        views.setTextViewText(R.id.wcolumn3,sp.getString("key_column3","Room"));


        views.setOnClickPendingIntent(R.id.bprev, getRefreshPendingIntent(context, appWidgetId,action1));
        views.setOnClickPendingIntent(R.id.bnext, getRefreshPendingIntent(context, appWidgetId,action2));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public PendingIntent getRefreshPendingIntent(Context context, int appWidgetId, String action){
        //Intent intent = new Intent("xml.ACTION_UPDATE_WIDGET");
        Intent intent = new Intent(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        //final String action = intent.getAction();
        SharedPreferences sp4 = PreferenceManager.getDefaultSharedPreferences(context);
        RemoteViews views2 = new RemoteViews(context.getPackageName(), R.layout.timetable);
        String temp = sp4.getString("widget_columns","Four");
        String tempcolor = sp4.getString("widget_theme","Blue");
        switch (temp){
            case "Two":
                switch(tempcolor){
                    case "Blue": views2 = new RemoteViews(context.getPackageName(), R.layout.timetable_columns_2);
                        break;
                    case "White":views2 = new RemoteViews(context.getPackageName(), R.layout.white_tt_2);
                        break;
                    case "Black": views2 = new RemoteViews(context.getPackageName(), R.layout.black_tt_2);
                        break;
                    case "Red": views2 = new RemoteViews(context.getPackageName(), R.layout.red_tt_2);
                        break;
                    case "Yellow": views2 = new RemoteViews(context.getPackageName(), R.layout.yellow_tt_2);
                        break;
                    case "Green": views2 = new RemoteViews(context.getPackageName(), R.layout.green_tt_2);
                        break;
                }
                break;
            case "Three":  switch(tempcolor){
                case "Blue": views2 = new RemoteViews(context.getPackageName(), R.layout.timetable_columns_3);
                    break;
                case "Black": views2 = new RemoteViews(context.getPackageName(), R.layout.black_tt_3);
                    break;
                case "White":views2 = new RemoteViews(context.getPackageName(), R.layout.white_tt_3);
                    break;
                case "Red": views2 = new RemoteViews(context.getPackageName(), R.layout.red_tt_3);
                    break;
                case "Yellow": views2 = new RemoteViews(context.getPackageName(), R.layout.yellow_tt_3);
                    break;
                case "Green": views2 = new RemoteViews(context.getPackageName(), R.layout.green_tt_3);
                    break;
            }
                break;
            case "Four":  switch(tempcolor){
                case "Blue": views2 = new RemoteViews(context.getPackageName(), R.layout.timetable);
                    break;
                case "Black": views2 = new RemoteViews(context.getPackageName(), R.layout.black_tt_4);
                    break;
                case "White":views2 = new RemoteViews(context.getPackageName(), R.layout.white_tt_4);
                    break;
                case "Red": views2 = new RemoteViews(context.getPackageName(), R.layout.red_tt_4);
                    break;
                case "Yellow": views2 = new RemoteViews(context.getPackageName(), R.layout.yellow_tt_4);
                    break;
                case "Green": views2 = new RemoteViews(context.getPackageName(), R.layout.green_tt_4);
                    break;
            }
                break;
        }

        //handle other appwidget actions

        if (intent.getAction().equals("my.package.PREV")) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Boolean bool = sp.getBoolean("weekend_toggle",true);
            dayhandler = sp.getInt("Day",1);
            if(bool) {
                if (dayhandler == 1)
                    dayhandler = 7;
                else
                    dayhandler -= 1;
            }
            else {
                if (dayhandler == 2)
                    dayhandler = 6;
                else
                    dayhandler -= 1;
            }


            ttcases(dayhandler,views2,context,ID);



            if(bool)
                views2.setTextViewText(R.id.appwidget_text, Days_weekends[dayhandler-1]);
            else
                views2.setTextViewText(R.id.appwidget_text,Days_weekdays[dayhandler-2]);
            views2.setTextViewText(R.id.title,sp.getString("title",""));
            views2.setTextViewText(R.id.wcolumn1,sp.getString("key_column1"+ID,"Subject"));
            views2.setTextViewText(R.id.wcolumn2,sp.getString("key_column2"+ID,"Teacher"));
            views2.setTextViewText(R.id.wcolumn3,sp.getString("key_column3"+ID,"Room"));

            SharedPreferences.Editor spedit = sp.edit();
            spedit.putInt("Day",dayhandler);
            spedit.apply();


            //some code here that will update your widget
        }else if(intent.getAction().equals("my.package.NEXT")){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Boolean bool = sp.getBoolean("weekend_toggle",true);
            dayhandler = sp.getInt("Day",1);
            if(bool) {
                if (dayhandler == 7)
                    dayhandler = 1;
                else
                    dayhandler += 1;
            }
            else {

                if (dayhandler == 6)
                    dayhandler = 2;
                else
                    dayhandler += 1;
            }

            ttcases(dayhandler,views2,context,ID);

            if(bool)
                views2.setTextViewText(R.id.appwidget_text, Days_weekends[dayhandler-1]);
            else
                views2.setTextViewText(R.id.appwidget_text, Days_weekdays[dayhandler-2]);
            views2.setTextViewText(R.id.title,sp.getString("title",""));
            views2.setTextViewText(R.id.wcolumn1,sp.getString("key_column1"+ID,"Subject"));
            views2.setTextViewText(R.id.wcolumn2,sp.getString("key_column2"+ID,"Teacher"));
            views2.setTextViewText(R.id.wcolumn3,sp.getString("key_column3"+ID,"Room"));

            SharedPreferences.Editor spedit = sp.edit();
            spedit.putInt("Day",dayhandler);
            spedit.apply();
        }
        ComponentName componentName = new ComponentName(context, widgettt.class);
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, views2);



        super.onReceive(context, intent);

        //handle other appwidget actions
    }

    void ttcases(int dayhandler, RemoteViews views2,Context context,int widgetID){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);


        switch(dayhandler){
            case 1:for(int k = 0 ; k < 8;k++) {

                views2.setTextViewText(times[k], sp.getString("key"+(k+48)+"_starttime"+widgetID, "") + "-" + sp.getString("key"+(k+48)+"_endtime"+widgetID, ""));
                views2.setTextViewText(subjects[k], sp.getString("key"+(k+48)+"_subject"+widgetID, ""));
                views2.setTextViewText(rooms[k], sp.getString("key"+(k+48)+"_room"+widgetID, ""));
                views2.setTextViewText(teachers[k], sp.getString("key"+(k+48)+"_teacher"+widgetID, ""));
            }


                break;

            case 2:
                for(int k = 0 ; k < 8;k++) {

                    views2.setTextViewText(times[k], sp.getString("key"+k+"_starttime"+widgetID, "") + "-" + sp.getString("key"+k+"_endtime"+widgetID, ""));
                    views2.setTextViewText(subjects[k], sp.getString("key"+(k)+"_subject"+widgetID, ""));
                    views2.setTextViewText(rooms[k], sp.getString("key"+k+"_room"+widgetID, ""));
                    views2.setTextViewText(teachers[k], sp.getString("key"+k+"_teacher"+widgetID, ""));
                }

                break;
            case 3:
                for(int k = 0 ; k < 8;k++) {

                    views2.setTextViewText(times[k], sp.getString("key"+(k+8)+"_starttime"+widgetID, "") + "-" + sp.getString("key"+(k+8)+"_endtime"+widgetID, ""));
                    views2.setTextViewText(subjects[k], sp.getString("key"+(k+8)+"_subject"+widgetID, ""));
                    views2.setTextViewText(rooms[k], sp.getString("key"+(k+8)+"_room"+widgetID, ""));
                    views2.setTextViewText(teachers[k], sp.getString("key"+(k+8)+"_teacher"+widgetID, ""));
                }


                break;
            case 4:
                for(int k = 0 ; k < 8;k++) {

                    views2.setTextViewText(times[k], sp.getString("key"+(k+16)+"_starttime"+widgetID, "") + "-" + sp.getString("key"+(k+16)+"_endtime"+widgetID, ""));
                    views2.setTextViewText(subjects[k], sp.getString("key"+(k+16)+"_subject"+widgetID, ""));
                    views2.setTextViewText(rooms[k], sp.getString("key"+(k+16)+"_room"+widgetID, ""));
                    views2.setTextViewText(teachers[k], sp.getString("key"+(k+16)+"_teacher"+widgetID, ""));
                }


                break;
            case 5:
                for(int k = 0 ; k < 8;k++) {

                    views2.setTextViewText(times[k], sp.getString("key"+(k+24)+"_starttime"+widgetID, "") + "-" + sp.getString("key"+(k+24)+"_endtime"+widgetID, ""));
                    views2.setTextViewText(subjects[k], sp.getString("key"+(k+24)+"_subject"+widgetID, ""));
                    views2.setTextViewText(rooms[k], sp.getString("key"+(k+24)+"_room"+widgetID, ""));
                    views2.setTextViewText(teachers[k], sp.getString("key"+(k+24)+"_teacher"+widgetID, ""));
                }

                break;
            case 6:
                for(int k = 0 ; k < 8;k++) {

                    views2.setTextViewText(times[k], sp.getString("key"+(k+32)+"_starttime"+widgetID, "") + "-" + sp.getString("key"+(k+32)+"_endtime"+widgetID, ""));
                    views2.setTextViewText(subjects[k], sp.getString("key"+(k+32)+"_subject"+widgetID, ""));
                    views2.setTextViewText(rooms[k], sp.getString("key"+(k+32)+"_room"+widgetID, ""));
                    views2.setTextViewText(teachers[k], sp.getString("key"+(k+32)+"_teacher"+widgetID, ""));

                }


                break;
            case 7:
                for(int k = 0 ; k < 8;k++) {

                    views2.setTextViewText(times[k], sp.getString("key"+(k+40)+"_starttime"+widgetID, "") + "-" + sp.getString("key"+(k+40)+"_endtime"+widgetID, ""));
                    views2.setTextViewText(subjects[k], sp.getString("key"+(k+40)+"_subject"+widgetID, ""));
                    views2.setTextViewText(rooms[k], sp.getString("key"+(k+40)+"_room"+widgetID, ""));
                    views2.setTextViewText(teachers[k], sp.getString("key"+(k+40)+"_teacher"+widgetID, ""));
                }
                break;


        }


        /*switch(dayhandler){
            case 2:
                views2.setTextViewText(R.id.time1,"8 am \nto \n10 am");
                views2.setTextViewText(R.id.lecture1,"DBMS \n(ZP)");
                views2.setTextViewText(R.id.type1,"Lecture");

                views2.setTextViewText(R.id.time2,"10 am \nto \n11 am");
                views2.setTextViewText(R.id.lecture2,"COA \n(AB)");
                views2.setTextViewText(R.id.type2,"Lecture");


                views2.setTextViewText(R.id.time3,"11 am \nto \n1 pm");
                views2.setTextViewText(R.id.lecture3,"TCS \n(FS)");
                views2.setTextViewText(R.id.type3,"Lecture");

                views2.setTextViewText(R.id.time4,"2 pm \nto \n4 pm");
                views2.setTextViewText(R.id.lecture4,"COA (A) (L4) \nCG (B) (L3) \nDBMS (C) (L2)");
                views2.setTextViewText(R.id.type4,"Practical");

                views2.setTextViewText(R.id.time5,"4 pm \nto \n5pm");
                views2.setTextViewText(R.id.lecture5,"Maths \n(A)");
                views2.setTextViewText(R.id.type5,"Tutorial");
                break;
            case 3:
                views2.setTextViewText(R.id.time1,"9 am \nto \n11am");
                views2.setTextViewText(R.id.lecture1,"DBMS \n(ZP)");
                views2.setTextViewText(R.id.type1,"Lecture");

                views2.setTextViewText(R.id.time2,"11 am \nto \n1 pm");
                views2.setTextViewText(R.id.lecture2,"AOA \n(WD)");
                views2.setTextViewText(R.id.type2,"Lecture");

                views2.setTextViewText(R.id.time4,"4 pm \nto \n5 pm");
                views2.setTextViewText(R.id.lecture4,"CG \n(LI)");
                views2.setTextViewText(R.id.type4,"Lecture");

                views2.setTextViewText(R.id.time3,"2 pm \nto \n4 pm");
                views2.setTextViewText(R.id.lecture3,"AOA (B) (CC2) \nCG (A) (L3) \nCOA (C) (CPLAB)");
                views2.setTextViewText(R.id.type3,"Practical");

                views2.setTextViewText(R.id.time5,"");
                views2.setTextViewText(R.id.lecture5,"");
                views2.setTextViewText(R.id.type5,"");
                break;
            case 4:
                views2.setTextViewText(R.id.time1,"9 am \nto \n11am");
                views2.setTextViewText(R.id.lecture1,"CG \n(LI)");
                views2.setTextViewText(R.id.type1,"Lecture");

                views2.setTextViewText(R.id.time3,"2 pm \nto \n3 pm");
                views2.setTextViewText(R.id.lecture3,"COA \n(AB)");
                views2.setTextViewText(R.id.type3,"Lecture");

                views2.setTextViewText(R.id.time4,"3 pm \nto \n5 pm");
                views2.setTextViewText(R.id.lecture4,"TCS \n(FS)");
                views2.setTextViewText(R.id.type4,"Lecture");

                views2.setTextViewText(R.id.time2,"11 am \nto \n1 pm");
                views2.setTextViewText(R.id.lecture2,"COA (B) (CPLAB) \nCG (C) (L3) \nDBMS (A) (L2)");
                views2.setTextViewText(R.id.type2,"Practical");

                views2.setTextViewText(R.id.time5,"");
                views2.setTextViewText(R.id.lecture5,"");
                views2.setTextViewText(R.id.type5,"");
                break;
            case 5:
                views2.setTextViewText(R.id.time1,"9 am \nto \n11am");
                views2.setTextViewText(R.id.lecture1,"Maths \n(MZ)");
                views2.setTextViewText(R.id.type1,"Lecture");


                views2.setTextViewText(R.id.time4,"4 pm \nto \n5 pm");
                views2.setTextViewText(R.id.lecture4,"Maths \n(B)");
                views2.setTextViewText(R.id.type4,"Tutorial");

                views2.setTextViewText(R.id.time2,"11 am \nto \n1 pm");
                views2.setTextViewText(R.id.lecture2,"AOA \n(ST)");
                views2.setTextViewText(R.id.type2,"Lecture");

                views2.setTextViewText(R.id.time3,"2 pm \nto \n4 pm");
                views2.setTextViewText(R.id.lecture3,"AOA (A) (CC1) \nAOA (C) (L1) \nDBMS (B) (L2)");
                views2.setTextViewText(R.id.type3,"Practical");

                views2.setTextViewText(R.id.time5,"");
                views2.setTextViewText(R.id.lecture5,"");
                views2.setTextViewText(R.id.type5,"");
                break;
            case 6:
                views2.setTextViewText(R.id.time1,"10 am \nto \n11am");
                views2.setTextViewText(R.id.lecture1,"CG \n(LI)");
                views2.setTextViewText(R.id.type1,"Lecture");

                views2.setTextViewText(R.id.time4,"4 pm \nto \n5 pm");
                views2.setTextViewText(R.id.lecture4,"Maths \n(C)");
                views2.setTextViewText(R.id.type4,"Tutorial");

                views2.setTextViewText(R.id.time2,"11 am \nto \n1 pm");
                views2.setTextViewText(R.id.lecture2,"Maths \n(MZ)");
                views2.setTextViewText(R.id.type2,"Lecture");

                views2.setTextViewText(R.id.time3,"2 pm \nto \n4 pm");
                views2.setTextViewText(R.id.lecture3,"COA \n(FS)");
                views2.setTextViewText(R.id.type3,"Lecture");

                 views2.setTextViewText(R.id.time5,"");
                views2.setTextViewText(R.id.lecture5,"");
                views2.setTextViewText(R.id.type5,"");
                break;

        }*/

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            onEnabled(context);


            updateAppWidget(context, appWidgetManager, appWidgetId);
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



