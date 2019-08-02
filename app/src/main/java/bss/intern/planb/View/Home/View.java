package bss.intern.planb.View.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    private FloatingActionButton floatingActionButton;
    private List<AgendaEvent> mEventModels = new ArrayList<>();
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AgendaDatabase db = AgendaDatabase.getINSTANCE(this);
        presenter = new Presenter(this, db.agendaEventDao());

        configureNavigationDrawer();
        configureToolbar();
        configureWeekView();
        presenter.loadAll();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);
        floatingActionButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(View.this, bss.intern.planb.View.AddAgenda.View.class);
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                presenter.loadAll();
            }
            if (requestCode == Activity.RESULT_CANCELED){
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }
    private void configureNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                return false;
            }
        });
    }

    private void configureWeekView(){
        weekView = (WeekView) findViewById(R.id.weekView);

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
    }

    @Override
    public void populateList(List<AgendaEvent> agendaEventList) {
        mEventModels.clear();
        for (AgendaEvent agendaEvent:agendaEventList){
            mEventModels.add(agendaEvent);
        }
        weekView.notifyDatasetChanged();
    }

    private List<WeekViewEvent> getWeekViewEventsFromEventModels(List<AgendaEvent> eventModels, int year, int month) {
        List<WeekViewEvent> result = new ArrayList<>();
        for (AgendaEvent agendaEvent:eventModels){
            if (agendaEvent.getStartMonth() == month && agendaEvent.getStartYear() == year){
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

                WeekViewEvent temp = new WeekViewEvent(id, name, location, startDate, endDate);
                temp.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
                result.add(temp);
            }
        }
        return result;
    }
}
