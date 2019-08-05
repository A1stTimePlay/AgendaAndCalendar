package bss.intern.planb.View.Home;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import bss.intern.planb.Database.AgendaDatabase;
import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Presenter.Home.Presenter;
import bss.intern.planb.R;
import bss.intern.planb.WeekView.DateTimeInterpreter;
import bss.intern.planb.WeekView.MonthLoader;
import bss.intern.planb.WeekView.WeekView;
import bss.intern.planb.WeekView.WeekViewEvent;

public class View extends AppCompatActivity implements IView {

    private DrawerLayout drawerLayout;
    private WeekView weekView;
    private FloatingActionButton fabEvent, fabTodo, fabGoal, fabMeeting;
    private TextView tvFabEvent, tvFabTodo, tvFabGoal, tvFabMeeting;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private List<AgendaEvent> mEventModels = new ArrayList<>();
    private Presenter presenter;
    private android.view.View shadowView;
    boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AgendaDatabase db = AgendaDatabase.getINSTANCE(this);
        presenter = new Presenter(this, db.agendaEventDao());

        initComponent();
        configureNavigationDrawer();
        configureToolbar();
        configureWeekView();

        presenter.loadAll();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                presenter.loadAll();
            }
            if (requestCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }

    private void initComponent() {
        tvFabEvent = findViewById(R.id.tvFabEvent);
        tvFabTodo = findViewById(R.id.tvFabTodo);
        tvFabGoal = findViewById(R.id.tvFabGoal);
        tvFabMeeting = findViewById(R.id.tvFabMeeting);
        fabEvent = findViewById(R.id.fabEvent);
        fabTodo = findViewById(R.id.fabTodo);
        fabGoal = findViewById(R.id.fabGoal);
        fabMeeting = findViewById(R.id.fabMeeting);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        shadowView = findViewById(R.id.shadowView);

        fabEvent.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fabEvent.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (isOpen) {
                    int color = ContextCompat.getColor(View.this, R.color.event_color_01);
                    fabMenuClose();
                    Intent intent = new Intent(View.this, bss.intern.planb.View.AddAgenda.View.class);
                    intent.putExtra("color", color);
                    startActivityForResult(intent, 1);
                } else {
                    fabMenuOpen();
                }
            }
        });

        fabTodo.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                int color = ContextCompat.getColor(View.this, R.color.event_color_02);
                fabMenuClose();
                Intent intent = new Intent(View.this, bss.intern.planb.View.AddAgenda.View.class);
                intent.putExtra("color", color);
                startActivityForResult(intent, 1);
            }
        });

        fabGoal.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                int color = ContextCompat.getColor(View.this, R.color.event_color_03);
                fabMenuClose();
                Intent intent = new Intent(View.this, bss.intern.planb.View.AddAgenda.View.class);
                intent.putExtra("color", color);
                startActivityForResult(intent, 1);
            }
        });

        fabMeeting.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                int color = ContextCompat.getColor(View.this, R.color.event_color_04);
                fabMenuClose();
                Intent intent = new Intent(View.this, bss.intern.planb.View.AddAgenda.View.class);
                intent.putExtra("color", color);
                startActivityForResult(intent, 1);
            }
        });


        shadowView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (isOpen) {
                    fabMenuClose();
                } else {
                    fabMenuOpen();
                }
            }
        });
    }

    private void fabMenuOpen() {
        shadowView.setVisibility(android.view.View.VISIBLE);
        fabTodo.startAnimation(fab_open);
        fabTodo.setClickable(true);
        fabTodo.show();

        fabGoal.startAnimation(fab_open);
        fabGoal.setClickable(true);
        fabGoal.show();

        fabMeeting.startAnimation(fab_open);
        fabMeeting.setClickable(true);
        fabMeeting.show();

        fabEvent.setImageResource(R.drawable.ic_event);
        fabEvent.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(View.this, R.color.event_color_01)));
        fabEvent.startAnimation(fab_clock);

        tvFabEvent.setVisibility(android.view.View.VISIBLE);
        tvFabTodo.setVisibility(android.view.View.VISIBLE);
        tvFabGoal.setVisibility(android.view.View.VISIBLE);
        tvFabMeeting.setVisibility(android.view.View.VISIBLE);

        isOpen = true;
    }

    private void fabMenuClose() {
        shadowView.setVisibility(android.view.View.GONE);
        fabTodo.startAnimation(fab_close);
        fabTodo.setClickable(false);
        fabTodo.hide();

        fabGoal.startAnimation(fab_close);
        fabGoal.setClickable(false);
        fabGoal.hide();

        fabMeeting.startAnimation(fab_close);
        fabMeeting.setClickable(false);
        fabMeeting.hide();

        fabEvent.setImageResource(R.drawable.plus);
        fabEvent.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fabEvent.startAnimation(fab_anticlock);

        tvFabEvent.setVisibility(android.view.View.INVISIBLE);
        tvFabTodo.setVisibility(android.view.View.INVISIBLE);
        tvFabGoal.setVisibility(android.view.View.INVISIBLE);
        tvFabMeeting.setVisibility(android.view.View.INVISIBLE);

        isOpen = false;
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

    }

    private void configureWeekView() {
        weekView = findViewById(R.id.weekView);

        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" d", Locale.getDefault());
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });

        weekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                setTitle(newFirstVisibleDay.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
            }
        });

        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return getWeekViewEventsFromEventModels(mEventModels, newYear, newMonth);
            }
        });

        weekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {
                Toast toast = Toast.makeText(View.this, "Hello world", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    @Override
    public void populateList(List<AgendaEvent> agendaEventList) {
        mEventModels.clear();
        for (AgendaEvent agendaEvent : agendaEventList) {
            mEventModels.add(agendaEvent);
        }
        weekView.notifyDatasetChanged();
    }

    private List<WeekViewEvent> getWeekViewEventsFromEventModels(List<AgendaEvent> eventModels, int year, int month) {
        List<WeekViewEvent> result = new ArrayList<>();
        for (AgendaEvent agendaEvent : eventModels) {
            if (agendaEvent.getStartMonth() == month && agendaEvent.getStartYear() == year) {
                long id = agendaEvent.getId();
                String name = agendaEvent.getName();
                String location = agendaEvent.getLocation();

                Calendar startDate = Calendar.getInstance();
                startDate.set(Calendar.DAY_OF_MONTH, agendaEvent.getStartDay());
                startDate.set(Calendar.MONTH, agendaEvent.getStartMonth());
                startDate.set(Calendar.YEAR, agendaEvent.getStartYear());
                startDate.set(Calendar.HOUR_OF_DAY, agendaEvent.getStartHour());
                startDate.set(Calendar.MINUTE, agendaEvent.getStartMonth());

                Calendar endDate = Calendar.getInstance();
                endDate.set(Calendar.DAY_OF_MONTH, agendaEvent.getEndDay());
                endDate.set(Calendar.MONTH, agendaEvent.getEndMonth());
                endDate.set(Calendar.YEAR, agendaEvent.getEndYear());
                endDate.set(Calendar.HOUR_OF_DAY, agendaEvent.getEndHour());
                endDate.set(Calendar.MINUTE, agendaEvent.getEndMinute());

                int color = agendaEvent.getColor();

                WeekViewEvent temp = new WeekViewEvent(id, name, location, startDate, endDate, false, color);
                temp.setColor(color);
                result.add(temp);
            }
        }
        return result;
    }
}
