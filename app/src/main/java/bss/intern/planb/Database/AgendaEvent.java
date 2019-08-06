package bss.intern.planb.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import bss.intern.planb.Util.DateTime;

@Entity (tableName = "AgendaEvent")
public class AgendaEvent implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Name;
    private String Note;
    private String Location;
    private int startDay;
    private int startMonth;
    private int startYear;
    private int startHour;
    private int startMinute;
    private int endDay;
    private int endMonth;
    private int endYear;
    private int endHour;
    private int endMinute;
    private int color;
    private boolean allDay;

    public AgendaEvent(String Name, String Note, String Location, int startDay, int startMonth, int startYear, int startHour, int startMinute, int endDay, int endMonth, int endYear, int endHour, int endMinute, int color, boolean allDay) {
        this.Name = Name;
        this.Note = Note;
        this.Location = Location;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endDay = endDay;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.color = color;
        this.allDay = allDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
}