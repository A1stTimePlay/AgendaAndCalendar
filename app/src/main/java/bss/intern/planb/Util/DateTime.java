package bss.intern.planb.Util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class DateTime implements Serializable {
    int mDay;
    int mMonth;
    int mYear;
    int mHour;
    int mMinute;

    NumberFormat formatter = new DecimalFormat("00");

    public DateTime(){

    }

    public DateTime(Calendar calendar) {
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.mMonth = calendar.get(Calendar.MONTH) + 1;
        this.mYear = calendar.get(Calendar.YEAR);
        this.mHour = calendar.get(Calendar.HOUR);
        this.mMinute = calendar.get(Calendar.MINUTE);
    }

    public void set(Calendar calendar){
        this.mDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.mMonth = calendar.get(Calendar.MONTH) + 1;
        this.mYear = calendar.get(Calendar.YEAR);
        this.mHour = calendar.get(Calendar.HOUR);
        this.mMinute = calendar.get(Calendar.MINUTE);
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
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
        String day = formatter.format(mDay);
        String month = formatter.format(mMonth);
        String year = formatter.format(mYear);
        return day + "/" + month + "/" + year;
    }
}
