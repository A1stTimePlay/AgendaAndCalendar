package bss.intern.planb.View.AddAgenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import bss.intern.planb.Database.AgendaDatabase;
import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Presenter.AddAgenda.Presenter;
import bss.intern.planb.R;
import bss.intern.planb.Util.DateTime;

public class View extends AppCompatActivity implements IView {

    Presenter presenter;

    EditText etName;
    EditText etNote;
    EditText etLocation;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvStartTime;
    TextView tvEndTime;
    Button btnConfirm;
    ImageView btnCancel;
    final DateTime startDateTime = new DateTime(Calendar.getInstance());
    final DateTime endDateTime = new DateTime(Calendar.getInstance());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);

        AgendaDatabase db = AgendaDatabase.getINSTANCE(getApplication());
        presenter = new Presenter(this, db.agendaEventDao());

        etName = findViewById(R.id.etName);
        etNote = findViewById(R.id.etNote);
        etLocation = findViewById(R.id.etLocation);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);
        android.view.View line1 = findViewById(R.id.line1);
        android.view.View line2 = findViewById(R.id.line2);
        android.view.View line3 = findViewById(R.id.line3);
        android.view.View line4 = findViewById(R.id.line4);


        // lần đầu tiên DatePickerDialog hiện lên sẽ show ngày mặc định là ngày hiện tại
        tvStartDate = findViewById(R.id.tvStartDate);
        tvStartDate.setText(startDateTime.dateToString());
        tvStartDate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(View.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startDateTime.setmDay(i2);
                        startDateTime.setmMonth(i1);
                        startDateTime.setmYear(i);

                        tvStartDate.setText(startDateTime.dateToString());
                    }
                }, startDateTime.getmYear(), startDateTime.getmMonth(), startDateTime.getmDay());
                datePickerDialog.show();
            }
        });

        tvEndDate = findViewById(R.id.tvEndDate);
        tvEndDate.setText(endDateTime.dateToString());
        tvEndDate.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(View.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        endDateTime.setmDay(i2);
                        endDateTime.setmMonth(i1);
                        endDateTime.setmYear(i);
                        tvEndDate.setText(endDateTime.dateToString());
                    }
                }, endDateTime.getmYear(), endDateTime.getmMonth(), endDateTime.getmDay());
                datePickerDialog.show();
            }
        });

        // lần đầu tiên TimePickerDialog hiện lên sẽ show thời gian mặc định là thời gian hiện tại
        tvStartTime = findViewById(R.id.tvStartTime);
        tvStartTime.setText(startDateTime.timeToString());
        tvStartTime.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(View.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        startDateTime.setmHour(i);
                        startDateTime.setmMinute(i1);
                        tvStartTime.setText(startDateTime.timeToString());
                    }
                }, startDateTime.getmHour(), startDateTime.getmMinute(), true);
                timePickerDialog.show();
            }
        });

        tvEndTime = findViewById(R.id.tvEndTime);
        tvEndTime.setText(endDateTime.timeToString());
        tvEndTime.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(View.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        endDateTime.setmHour(i);
                        endDateTime.setmMinute(i1);
                        tvEndTime.setText(endDateTime.timeToString());
                    }
                }, endDateTime.getmHour(), endDateTime.getmMinute(), true);
                timePickerDialog.show();
            }
        });

        btnConfirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String name = etName.getText().toString();
                String note = etNote.getText().toString();
                String location = etLocation.getText().toString();
                int startDay = startDateTime.getmDay();
                int startMonth = startDateTime.getmMonth();
                int startYear = startDateTime.getmYear();
                int startHour = startDateTime.getmHour();
                int startMinute = startDateTime.getmMinute();
                int endDay = endDateTime.getmDay();
                int endMonth = endDateTime.getmMonth();
                int endYear = endDateTime.getmYear();
                int endHour = endDateTime.getmHour();
                int endMinute = endDateTime.getmMinute();
                if (name.length()!=0 && note.length()!=0 && location.length()!=0) {
                    AgendaEvent newAgendaEvent = new AgendaEvent(name, note, location, startDay, startMonth, startYear, startHour, startMinute, endDay, endMonth, endYear, endHour, endMinute);
                    presenter.createAgenda(newAgendaEvent);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("AgendaEvent", newAgendaEvent);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(View.this, "Please fill in all required field", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }


    @Override
    public void successful() {
        Toast toast = Toast.makeText(this, "hello world", Toast.LENGTH_SHORT);
        toast.show();
    }
}
