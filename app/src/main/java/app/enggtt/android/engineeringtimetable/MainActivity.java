package app.enggtt.android.engineeringtimetable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;

/**
 * Created by Ibrahimkb on 07-02-2016.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if(!sp.getBoolean("weekend_toggle",true)) {
            for (int l = 40; l < 48; l++) {
                button[l].setEnabled(false);
                button[l].setBackgroundColor(Color.parseColor("#ffcecece"));
            }

            for (int l = 48; l < 56; l++) {
                button[l].setEnabled(false);
                button[l].setBackgroundColor(Color.parseColor("#ffcecece"));
            }
        }else{
            for (int l = 40; l < 48; l++) {
                button[l].setEnabled(true);
                button[l].setBackgroundColor(Color.parseColor("#ffffffff"));
            }

            for (int l = 48; l < 56; l++) {
                button[l].setEnabled(true);
                button[l].setBackgroundColor(Color.parseColor("#ffffffff"));
            }

        }
    }

    static final int DIALOG_ID=0;
    static final int DIALOG_ID2=1;
    int hour_x;
    int minute_x;


    TextView start_time,end_time,column1,column2,column3;


    public static final int[] times = {R.id.time1, R.id.time2, R.id.time3, R.id.time4, R.id.time5, R.id.time6, R.id.time7, R.id.time8 };
    //public static final int[] types = {R.id.type1, R.id.type2, R.id.type3, R.id.type4, R.id.type5, R.id.type6, R.id.type7, R.id.type8 };
    public static final int[] teachers = {R.id.teacher1, R.id.teacher2, R.id.teacher3, R.id.teacher4, R.id.teacher5, R.id.teacher6, R.id.teacher7, R.id.teacher8 };
    public static final int[] subjects = {R.id.lecture1, R.id.lecture2, R.id.lecture3, R.id.lecture4, R.id.lecture5, R.id.lecture6, R.id.lecture7, R.id.lecture8 };
    public static final int[] rooms = {R.id.room1, R.id.room2, R.id.room3, R.id.room4, R.id.room5, R.id.room6, R.id.room7, R.id.room8 };


    private MainActivity context;
    private int widgetID;
    static String action1 = "my.package.PREV";
    static String action2 = "my.package.NEXT";
    final String[] Days_weekends = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"};
    final String[] Days_weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    int dayhandler;
    int temporary=3;

    TextView title;


    String starttimecopy="",endtimecopy="",subjectcopy="",teachercopy="",roomcopy="";

    String[] starttimearray = new String[56];
    String[] endtimearray = new String[56];
    String[] teacherarray = new String[56];
    String[] subjectarray = new String[56];
    String[] roomarray = new String[56];
    //String[] typearray = new String[48];

    public static final int[] Ids = {R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9
            , R.id.b10,R.id.b11, R.id.b12, R.id.b13, R.id.b14, R.id.b15, R.id.b16, R.id.b17, R.id.b18, R.id.b19
            , R.id.b20,R.id.b21, R.id.b22, R.id.b23, R.id.b24, R.id.b25, R.id.b26, R.id.b27, R.id.b28, R.id.b29
            , R.id.b30,R.id.b31, R.id.b32, R.id.b33, R.id.b34, R.id.b35, R.id.b36, R.id.b37, R.id.b38, R.id.b39
            , R.id.b40,R.id.b41, R.id.b42, R.id.b43, R.id.b44, R.id.b45, R.id.b46, R.id.b47, R.id.b48,
            R.id.b49,R.id.b50, R.id.b51, R.id.b52, R.id.b53, R.id.b54, R.id.b55, R.id.b56};
    Button[] button = new Button[56];

    AdView mAdView;
    AdRequest adRequest;
    FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        setContentView(R.layout.activity_main);
        setResult(RESULT_CANCELED);
        context = this;
        title = (TextView) findViewById(R.id.titleconfig);

        final SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor spedit2 = sp2.edit();


        temporary=3;

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9084920470877631~2010767904");
        mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(adRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("6EBA7182CFCE89CAC7DCBDC243045A97")
                .build();
        mAdView.loadAd(adRequest);


        title.setText(sp2.getString("title","Set Title"));

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater l = getLayoutInflater();
                final View addtitle = l.inflate(R.layout.title, null);

                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText ettitle = (EditText) addtitle.findViewById(R.id.ettitle);

                                spedit2.putString("title",ettitle.getText().toString());
                                spedit2.apply();

                                title.setText(ettitle.getText().toString());

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setTitle("Set Title").create();
                alertDialog.setView(addtitle);
                alertDialog.show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        getData();
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);


        Button addwidget = (Button) findViewById(R.id.addwid);
        addwidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirebaseAnalytics.logEvent("tt_save",null);
                addwidget(appWidgetManager);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id)
        {
            case R.id.about:
                LayoutInflater l = getLayoutInflater();
                final View alayout = l.inflate(R.layout.about, null);

                AlertDialog about = new AlertDialog.Builder(this)
                        .setTitle("About")
                        .setView(alayout)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                            }
                        })
                        .create();
                about.show();

                break;

            case R.id.settings:
                Intent j = new Intent("app.enggtt.android.engineeringtimetable.PREFERENCES");
                startActivity(j);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public PendingIntent getRefreshPendingIntent(Context context, int appWidgetId, String action){
        //Intent intent = new Intent("xml.ACTION_UPDATE_WIDGET");
        Intent intent = new Intent(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    void ttcases(int dayhandler, RemoteViews views2){

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
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        if(id == DIALOG_ID){
            return new TimePickerDialog(this,timelistener,hour_x,minute_x,false);
        }else if(id == DIALOG_ID2){
            return new TimePickerDialog(this,timelistener2,hour_x,minute_x,false);
        }
        return null;
    }

    protected TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            hour_x = hourOfDay;
            minute_x = minute;

            if(hour_x>12)
                hour_x -= 12;

            if(minute_x<9) {
                start_time.setText(hour_x + ":0" + minute_x);
                end_time.setText((hour_x + 1) + ":0" + minute_x);
            }
            else if(minute_x==9) {
                start_time.setText(hour_x + ":0" + minute_x);
                end_time.setText((hour_x + 1) + ":" + minute_x);
            }

            else {
                start_time.setText(hour_x + ":" + minute_x);
                end_time.setText((hour_x+1)+":"+minute_x);
            }


        }
    };
    protected TimePickerDialog.OnTimeSetListener timelistener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            hour_x = hourOfDay;
            minute_x = minute;
            if(hour_x>12)
                hour_x -= 12;
            if(minute<=9)
                end_time.setText(hour_x+":0"+minute_x);
            else
                end_time.setText(hour_x+":"+minute_x);


        }
    };

    public void getData(){



        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor spedit = sp.edit();


        for(int k = 0;k<=Ids.length-1;k++) {
            button[k] = (Button) findViewById(Ids[k]);
            button[k].setId(k);

            if(sp.getString("key"+k+"_starttime"+widgetID,"").equals("")){
                button[k].setText("");
            }else {
                button[k].setText(sp.getString("key" + k + "_starttime"+widgetID, "") + "-" + sp.getString("key" + k + "_endtime"+widgetID, "")+"\n"+sp.getString("key"+k+"_subject"+widgetID,""));
            }
        }
        if(!sp.getBoolean("weekend_toggle",true)) {
            for (int l = 40; l < 48; l++) {
                button[l].setEnabled(false);
                button[l].setBackgroundColor(Color.parseColor("#ffcecece"));
            }

            for (int l = 48; l < 56; l++) {
                button[l].setEnabled(false);
                button[l].setBackgroundColor(Color.parseColor("#ffcecece"));
            }
        }else{
            for (int l = 40; l < 48; l++) {
                button[l].setEnabled(true);
                button[l].setBackgroundColor(Color.parseColor("#ffffffff"));
            }

            for (int l = 48; l < 56; l++) {
                button[l].setEnabled(true);
                button[l].setBackgroundColor(Color.parseColor("#ffffffff"));
            }

        }


        Button breset = (Button) findViewById(R.id.reset);
        breset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(spedit);
            }
        });








        for(int k = 0;k<=Ids.length-1;k++){


            button[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int i = v.getId();
                    LayoutInflater m = getLayoutInflater();
                    final View addnew = m.inflate(R.layout.addnew, null);
                    temporary=3;

                    start_time = (TextView) addnew.findViewById(R.id.start_time);
                    end_time = (TextView) addnew.findViewById(R.id.end_time);
                    column1 = (TextView) addnew.findViewById(R.id.column1_head);
                    column2 = (TextView) addnew.findViewById(R.id.column2_head);
                    column3 = (TextView) addnew.findViewById(R.id.column3_head);


                    final EditText teacher = (EditText) addnew.findViewById(R.id.etteacher);
                    final EditText subject = (EditText) addnew.findViewById(R.id.etsubject);
                    final EditText room = (EditText) addnew.findViewById(R.id.etroom);

                    Button copy = (Button) addnew.findViewById(R.id.copy);
                    Button paste = (Button) addnew.findViewById(R.id.paste);
                    Button delete = (Button) addnew.findViewById(R.id.delete);


                    String temp = sp.getString("widget_columns","four");
                    switch(temp){
                        case "Two":
                            subject.setEnabled(true);
                            column1.setBackgroundResource(R.drawable.buttonbg);
                            column1.setClickable(true);
                            teacher.setEnabled(false);
                            column2.setBackgroundColor(Color.parseColor("#ffcecece"));
                            column2.setClickable(false);
                            room.setEnabled(false);
                            column3.setBackgroundColor(Color.parseColor("#ffcecece"));
                            column3.setClickable(false);
                            break;
                        case "Three":
                            subject.setEnabled(true);
                            column1.setBackgroundResource(R.drawable.buttonbg);
                            column1.setClickable(true);
                            teacher.setEnabled(true);
                            column2.setBackgroundResource(R.drawable.buttonbg);
                            column2.setClickable(true);
                            room.setEnabled(false);
                            column3.setBackgroundColor(Color.parseColor("#ffcecece"));
                            column3.setClickable(false);
                            break;
                        case "Four":
                            subject.setEnabled(true);
                            column1.setBackgroundResource(R.drawable.buttonbg);
                            column1.setClickable(true);
                            teacher.setEnabled(true);
                            column2.setBackgroundResource(R.drawable.buttonbg);
                            column2.setClickable(true);
                            room.setEnabled(true);
                            column3.setBackgroundResource(R.drawable.buttonbg);
                            column2.setClickable(true);
                            break;
                    }


                    column1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater col = getLayoutInflater();
                            View editcolumn = col.inflate(R.layout.columnedit, null);
                            final EditText etcolumn = (EditText) editcolumn.findViewById(R.id.etcolumn);

                            AlertDialog adcol = new AlertDialog.Builder(context)
                                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String tempcolhead = etcolumn.getText().toString();
                                            column1.setText(tempcolhead);
                                            spedit.putString("key_column1"+widgetID,tempcolhead);
                                            spedit.apply();

                                        }
                                    })
                                    .setTitle("Set Column 1 Head")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create();
                            adcol.setView(editcolumn);
                            adcol.show();


                        }
                    });

                    column2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater col = getLayoutInflater();
                            View editcolumn = col.inflate(R.layout.columnedit, null);
                            final EditText etcolumn = (EditText) editcolumn.findViewById(R.id.etcolumn);
                            AlertDialog adcol = new AlertDialog.Builder(context)
                                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String tempcolhead = etcolumn.getText().toString();
                                            column2.setText(tempcolhead);
                                            spedit.putString("key_column2"+widgetID,tempcolhead);
                                            spedit.apply();

                                        }
                                    })
                                    .setTitle("Set Column 2 Head")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create();
                            adcol.setView(editcolumn);
                            adcol.show();
                        }
                    });

                    column3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LayoutInflater col = getLayoutInflater();
                            View editcolumn = col.inflate(R.layout.columnedit, null);
                            final EditText etcolumn = (EditText) editcolumn.findViewById(R.id.etcolumn);
                            AlertDialog adcol = new AlertDialog.Builder(context)
                                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String tempcolhead = etcolumn.getText().toString();
                                            column3.setText(tempcolhead);
                                            spedit.putString("key_column3"+widgetID,tempcolhead);
                                            spedit.apply();

                                        }
                                    })
                                    .setTitle("Set Column 3 Head")
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create();
                            adcol.setView(editcolumn);
                            adcol.show();
                        }
                    });




                    start_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            showDialog(DIALOG_ID);

                        }
                    });

                    end_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(DIALOG_ID2);

                        }
                    });

                    copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            teachercopy = teacher.getText().toString();
                            roomcopy = room.getText().toString();
                            starttimecopy = start_time.getText().toString();
                            endtimecopy = end_time.getText().toString();
                            subjectcopy = subject.getText().toString();

                            Toast.makeText(context, "Records Copied", Toast.LENGTH_SHORT).show();

                        }
                    });

                    paste.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            start_time.setText(starttimecopy);
                            end_time.setText(endtimecopy);
                            room.setText(roomcopy);
                            teacher.setText(teachercopy);
                            subject.setText(subjectcopy);

                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            start_time.setText("Start Time");
                            end_time.setText("End Time");
                            room.setText("");
                            teacher.setText("");
                            subject.setText("");
                            temporary=3;


                        }
                    });


                    column1.setText(sp.getString("key_column1"+widgetID,"Subject"));
                    column2.setText(sp.getString("key_column2"+widgetID,"Teacher"));
                    column3.setText(sp.getString("key_column3"+widgetID,"Room"));

                    if(sp.getString("key"+i+"_starttime"+widgetID, "").equals(""))
                        start_time.setText("Start Time");
                    else
                        start_time.setText(sp.getString("key"+i+"_starttime"+widgetID, "Start Time"));
                    if(sp.getString("key"+i+"_endtime"+widgetID, "").equals(""))
                        end_time.setText("End Time");
                    else
                        end_time.setText(sp.getString("key"+i+"_endtime"+widgetID, "End Time"));
                    room.setText(sp.getString("key"+i+"_room"+widgetID,""));
                    teacher.setText(sp.getString("key"+i+"_teacher"+widgetID,""));
                    subject.setText(sp.getString("key"+i+"_subject"+widgetID,""));

                    int number = 0;
                    if(0<=i && i<8) {
                        dayhandler = 1;
                        number = i;
                    }
                    else if(8<=i && i<16) {
                        dayhandler = 2;
                        number = i -8;
                    }

                    else if(16<=i && i<24) {
                        dayhandler = 3;
                        number = i-16;
                    }
                    else if(24<=i && i<32) {
                        dayhandler = 4;
                        number = i - 24;
                    }
                    else if(32<=i && i<40) {
                        dayhandler = 5;
                        number = i - 32;
                    }
                    else if(40<=i && i<48){
                        dayhandler = 6;
                        number = i - 40;
                    }
                    else if(48<=i && i<56){
                        dayhandler = 0;
                        number = i - 48;
                    }




                    AlertDialog ad = new AlertDialog.Builder(context)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setTitle(Days_weekends[dayhandler] + " Period "+ (number+1) )
                            .create();
                    ad.setView(addnew);


                    ad.setButton(AlertDialog.BUTTON_POSITIVE, "SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            starttimearray[i] = start_time.getText().toString();
                            endtimearray[i] = end_time.getText().toString();
                            teacherarray[i] = teacher.getText().toString();
                            subjectarray[i] = subject.getText().toString();
                            roomarray[i] = room.getText().toString();

                            if(starttimearray[i].equals("Start Time") && endtimearray[i].equals("End Time")){
                                spedit.putString("key"+i+"_starttime"+widgetID, "");
                                spedit.putString("key"+i+"_endtime"+widgetID, "");
                            }else {

                                spedit.putString("key" + i + "_starttime"+widgetID, starttimearray[i]);
                                spedit.putString("key" + i + "_endtime"+widgetID, endtimearray[i]);
                            }
                            spedit.putString("key"+i+"_room"+widgetID, roomarray[i]);
                            spedit.putString("key"+i+"_teacher"+widgetID, teacherarray[i]);
                            spedit.putString("key"+i+"_subject"+widgetID, subjectarray[i]);
                            spedit.apply();

                            if(starttimearray[i].equals("Start Time") && endtimearray[i].equals("End Time"))
                                button[i].setText("\n"+subjectarray[i]);
                            else
                                button[i].setText(starttimearray[i] + "-" + endtimearray[i] +"\n"+subjectarray[i]);

                        }
                    });
                    ad.show();
                }
            });
        }







    }


    public void addwidget(AppWidgetManager appWidgetManager){
        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor spedit = sp.edit();
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

        views.setOnClickPendingIntent(R.id.bprev, getRefreshPendingIntent(context, widgetID,action1));
        views.setOnClickPendingIntent(R.id.bnext, getRefreshPendingIntent(context, widgetID,action2));

        Intent configintent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,configintent,0);
        views.setOnClickPendingIntent(R.id.bconfig,pendingIntent);

        final Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        Boolean bool = sp.getBoolean("weekend_toggle",true);
        if(bool)
            dayhandler=day;
        else {
            if (day == 1 || day == 7)
                day = 2;
            dayhandler = day;
        }


        spedit.putInt("Day",dayhandler);
        spedit.apply();

        ttcases(dayhandler,views);
        if(bool)
            views.setTextViewText(R.id.appwidget_text, Days_weekends[dayhandler-1]);
        else
            views.setTextViewText(R.id.appwidget_text,Days_weekdays[dayhandler-1]);
        views.setTextViewText(R.id.title,sp.getString("title",""));
        views.setTextViewText(R.id.wcolumn1,sp.getString("key_column1"+widgetID,"Subject"));
        views.setTextViewText(R.id.wcolumn2,sp.getString("key_column2"+widgetID,"Teacher"));
        views.setTextViewText(R.id.wcolumn3,sp.getString("key_column3"+widgetID,"Room"));


        // appWidgetManager.updateAppWidget(widgetID,views);
        appWidgetManager.updateAppWidget(widgetID,views);

        Intent result = new Intent();
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID);
        setResult(RESULT_OK,result);
        finish();
    }



    public void reset(final SharedPreferences.Editor spedit){
        AlertDialog alertreset = new AlertDialog.Builder(context)
                .setTitle("Reset Timetable?")
                .setMessage("This will remove all records from your timetable.")
                .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int k = 0;k<=Ids.length-1;k++) {
                            button[k].setText("");
                            spedit.putString("key"+k+"_starttime"+widgetID,"");
                            spedit.putString("key"+k+"_endtime"+widgetID,"");
                            spedit.putString("key"+k+"_room"+widgetID,"");
                            spedit.putString("key"+k+"_teacher"+widgetID,"");
                            spedit.putString("key"+k+"_subject"+widgetID,"");
                            spedit.putString("key"+k+"_type"+widgetID,"");
                            spedit.apply();
                        }
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertreset.show();


    }

}
