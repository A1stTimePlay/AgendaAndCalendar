package bss.intern.planb.Util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import bss.intern.planb.Database.AgendaEvent;

public class DateTime implements Serializable {
    int mDay;
    int mMonth;
    int mYear;
    int mHour;
    int mMinute;
    Calendar calendar;

    NumberFormat formatter = new DecimalFormat("00");

    public DateTime(Calendar calendar) {
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.mMonth = calendar.get(Calendar.MONTH);
        this.mYear = calendar.get(Calendar.YEAR);
        this.mHour = calendar.get(Calendar.HOUR_OF_DAY);
        this.mMinute = calendar.get(Calendar.MINUTE);
        this.calendar = calendar;
    }

    public DateTime(int mDay, int mMonth, int mYear, int mHour, int mMinute) {
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
        this.mHour = mHour;
        this.mMinute = mMinute;
        calendar = calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.HOUR, mHour);
        calendar.set(Calendar.MINUTE, mMinute);
    }

    public void set(Calendar calendar) {
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.mMonth = calendar.get(Calendar.MONTH);
        this.mYear = calendar.get(Calendar.YEAR);
        this.mHour = calendar.get(Calendar.HOUR_OF_DAY);
        this.mMinute = calendar.get(Calendar.MINUTE);
        this.calendar = calendar;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
        calendar.set(Calendar.MONTH, mMonth);
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
        calendar.set(Calendar.YEAR, mYear);
    }

    public int getmHour() {
        return mHour;
    }

    public void setmHour(int mHour) {
        this.mHour = mHour;
    }

    public int getmMinute() {
        return mMinute;
    }

    public void setmMinute(int mMinute) {
        this.mMinute = mMinute;
    }

    public String timeToString() {

        String hour = formatter.format(mHour);
        String minute = formatter.format(mMinute);
        return hour + ":" + minute;
    }

    public String dateToString() {
        String dayWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        String day = Integer.toString(mDay);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        String year = formatter.format(mYear);
        return dayWeek + ", " + month + " " + day + ", " + year;
    }
}
