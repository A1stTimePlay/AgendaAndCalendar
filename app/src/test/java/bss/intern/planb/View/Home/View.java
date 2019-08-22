package bss.intern.planb.View.Home;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

    public static int FLAG_CREATE_NEW = 0;
    public static int FLAG_EDIT = 1;

    private DrawerLayout drawerLayout;
    private WeekView weekView;
    private FloatingActionButton fabEvent, fabTodo, fabGoal, fabMeeting, fabAccept, fabDecline;
    private TextView tvFabEvent, tvFabTodo, tvFabGoal, tvFabMeeting;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private List<AgendaEvent> mEventModels = new ArrayList<>();
    private Presenter presenter;
    private android.view.View shadowView;
    boolean isOpen;
    private WeekViewEvent currentlySelected;
    private WeekViewEvent currentlyMoving = new WeekViewEvent();

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

    private final class DragTapListener implements android.view.View.OnLongClickListener {
        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        public boolean onLongClick(android.view.View v) {
            ClipData data = ClipData.newPlainText("", "");
            android.view.View.DragShadowBuilder shadowBuilder = new android.view.View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            return true;
        }
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
        fabAccept = findViewById(R.id.fabAccept);
        fabDecline = findViewById(R.id.fabDecline);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        shadowView = findViewById(R.id.shadowView);

        TextView draggableView = (TextView) findViewById(R.id.draggable_view);
        draggableView.setOnLongClickListener(new DragTapListener());
        draggableView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Toast.makeText(View.this, "Drag and drop to quick create new event", Toast.LENGTH_SHORT).show();
            }
        });

        fabEvent.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fabEvent.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (isOpen) {
                    OpenCreateEventActivityDefault(ContextCompat.getColor(View.this, R.color.event_color_01));
                } else {
                    fabMenuOpen();
                }
            }
        });

        fabTodo.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                OpenCreateEventActivityDefault(ContextCompat.getColor(View.this, R.color.event_color_02));
            }
        });

        fabGoal.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                OpenCreateEventActivityDefault(ContextCompat.getColor(View.this, R.color.event_color_03));
            }
        });

        fabMeeting.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                OpenCreateEventActivityDefault(ContextCompat.getColor(View.this, R.color.event_color_04));
            }
        });

        fabDecline.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                fabConfirmClose();

            }
        });

        fabAccept.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (currentlySelected != null) {
                    presenter.moveEvent(currentlyMoving, currentlySelected);
                }
                fabConfirmClose();
                presenter.loadAll();
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

    private void fabConfirmOpen() {
        fabAccept.startAnimation(fab_open);
        fabDecline.startAnimation(fab_open);
        fabAccept.setVisibility(android.view.View.VISIBLE);
        fabDecline.setVisibility(android.view.View.VISIBLE);
        fabEvent.startAnimation(fab_close);
        fabEvent.setVisibility(android.view.View.GONE);
    }

    private void fabConfirmClose() {
        fabDecline.startAnimation(fab_close);
        fabAccept.startAnimation(fab_close);
        fabEvent.startAnimation(fab_open);
        fabDecline.setVisibility(android.view.View.GONE);
        fabAccept.setVisibility(android.view.View.GONE);
        fabEvent.setVisibility(android.view.View.VISIBLE);
    }

    private void fabMenuOpen() {
        shadowView.setVisibility(android.view.View.VISIBLE);

        fabTodo.startAnimation(fab_open);
        fabTodo.setClickable(true);
        fabTodo.setVisibility(android.view.View.VISIBLE);

        fabGoal.startAnimation(fab_open);
        fabGoal.setClickable(true);
        fabGoal.setVisibility(android.view.View.VISIBLE);

        fabMeeting.startAnimation(fab_open);
        fabMeeting.setClickable(true);
        fabMeeting.setVisibility(android.view.View.VISIBLE);

        fabEvent.startAnimation(fab_clock);
        fabEvent.setImageResource(R.drawable.ic_event);
        fabEvent.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(View.this, R.color.event_color_01)));

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
        fabTodo.setVisibility(android.view.View.GONE);

        fabGoal.startAnimation(fab_close);
        fabGoal.setClickable(false);
        fabGoal.setVisibility(android.view.View.GONE);

        fabMeeting.startAnimation(fab_close);
        fabMeeting.setClickable(false);
        fabMeeting.setVisibility(android.view.View.GONE);

        fabEvent.setImageResource(R.drawable.plus);
        fabEvent.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        fabEvent.startAnimation(fab_anticlock);

        tvFabEvent.setVisibility(android.view.View.INVISIBLE);
        tvFabTodo.setVisibility(android.view.View.INVISIBLE);
        tvFabGoal.setVisibility(android.view.View.INVISIBLE);
        tvFabMeeting.setVisibility(android.view.View.INVISIBLE);

        isOpen = false;
    }

    private void OpenCreateEventActivityDefault(int color) {
        if (isOpen == true)
            fabMenuClose();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.HOUR, 1);
        AgendaEvent temp = new AgendaEvent(startDate, endDate, color);
        Intent intent = new Intent(View.this, bss.intern.planb.View.AddAndEditEvent.View.class);
        intent.putExtra("AgendaEvent", temp);
        intent.putExtra("FLAG", FLAG_CREATE_NEW);
        startActivityForResult(intent, 1);
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
                drawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_new_event)
                    OpenCreateEventActivityDefault(ContextCompat.getColor(View.this, R.color.event_color_01));
                if (menuItem.getItemId() == R.id.nav_map) {
                    Intent intent = new Intent(View.this, bss.intern.planb.View.ShowOnMap.View.class);
                    intent.putExtra("FLAG", View.FLAG_EDIT);
                    startActivity(intent);
                }
                return true;
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
                return weekday.toUpperCase();
            }

            @Override
            public String interpretDay(Calendar date) {
                SimpleDateFormat format = new SimpleDateFormat(" d", Locale.getDefault());
                return format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour, int minutes) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });

        weekView.setScrollListener(new WeekView.ScrollListener() {
            @Override
            public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
                setTitle(newFirstVisibleDay.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + ", "+newFirstVisibleDay.get(Calendar.YEAR));
            }
        });

        weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                return getWeekViewEventsFromEventModels(mEventModels, newYear, newMonth);
            }
        });

        weekView.setAddEventClickListener(new WeekView.AddEventClickListener() {
            @Override
            public void onAddEventClicked(Calendar startTime, Calendar endTime) {

                AgendaEvent temp = new AgendaEvent(startTime, endTime, ContextCompat.getColor(View.this, R.color.event_color_01));
                Intent intent = new Intent(View.this, bss.intern.planb.View.AddAndEditEvent.View.class);
                intent.putExtra("AgendaEvent", temp);
                intent.putExtra("FLAG", View.FLAG_CREATE_NEW);
                startActivityForResult(intent, 1);

            }
        });

        weekView.setEmptyViewClickListener(new WeekView.EmptyViewClickListener() {
            @Override
            public void onEmptyViewClicked(Calendar date) {
                currentlySelected = new WeekViewEvent();
                currentlySelected.setStartTime(date);
            }
        });

        weekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Intent intent = new Intent(View.this, bss.intern.planb.View.EventDetail.View.class);
                intent.putExtra("WeekViewEvent", event);
                startActivity(intent);
            }
        });

        weekView.setDropListener(new WeekView.DropListener() {
            @Override
            public void onDrop(android.view.View view, Calendar date) {
                Calendar endTime = (Calendar) date.clone();
                endTime.add(Calendar.HOUR, 1);
                AgendaEvent temp = new AgendaEvent(date, endTime, ContextCompat.getColor(View.this, R.color.event_color_03));
                temp.setName("Quick create event");
                temp.setNote("You have some work to do now");
                presenter.quickCreate(temp);
                presenter.loadAll();
            }
        });

        weekView.setEventLongPressListener(new WeekView.EventLongPressListener() {
            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
                fabConfirmOpen();
                currentlyMoving = event;
            }
        });

    }

    private List<WeekViewEvent> getWeekViewEventsFromEventModels(List<AgendaEvent> eventModels, int year, int month) {
        List<WeekViewEvent> result = new ArrayList<>();
        for (AgendaEvent agendaEvent : eventModels) {
            if (agendaEvent.getStartMonth() == month - 1 && agendaEvent.getStartYear() == year) {
                long id = agendaEvent.getId();
                String name = agendaEvent.getName();
                String location = agendaEvent.getLocation();

                Calendar startDate = Calendar.getInstance();
                startDate.set(Calendar.DAY_OF_MONTH, agendaEvent.getStartDay());
                startDate.set(Calendar.MONTH, agendaEvent.getStartMonth());
                startDate.set(Calendar.YEAR, agendaEvent.getStartYear());
                startDate.set(Calendar.HOUR_OF_DAY, agendaEvent.getStartHour());
                startDate.set(Calendar.MINUTE, agendaEvent.getStartMinute());

                Calendar endDate = Calendar.getInstance();
                endDate.set(Calendar.DAY_OF_MONTH, agendaEvent.getEndDay());
                endDate.set(Calendar.MONTH, agendaEvent.getEndMonth());
                endDate.set(Calendar.YEAR, agendaEvent.getEndYear());
                endDate.set(Calendar.HOUR_OF_DAY, agendaEvent.getEndHour());
                endDate.set(Calendar.MINUTE, agendaEvent.getEndMinute());

                int color = agendaEvent.getColor();
                boolean allDay = agendaEvent.isAllDay();

                WeekViewEvent temp = new WeekViewEvent(id, name, location, startDate, endDate, allDay);
                temp.setColor(color);
                result.add(temp);
            }
        }
        return result;
    }

    @Override
    public void populateList(List<AgendaEvent> agendaEventList) {
        mEventModels.clear();
        for (AgendaEvent agendaEvent : agendaEventList) {
            mEventModels.add(agendaEvent);
        }
        weekView.notifyDatasetChanged();
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
            case R.id.today:
                weekView.goToToday();

        }
        return true;
    }
}