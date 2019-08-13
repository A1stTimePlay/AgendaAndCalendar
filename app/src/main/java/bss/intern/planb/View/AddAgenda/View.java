package bss.intern.planb.View.AddAgenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import bss.intern.planb.Database.AgendaDatabase;
import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Presenter.AddAgenda.Presenter;
import bss.intern.planb.R;
import bss.intern.planb.Util.DateTime;

public class View extends AppCompatActivity implements IView {

    private static final String TAG = "Add agenda";
    Presenter presenter;

    private EditText etName;
    private EditText etNote;
    private EditText etLocation;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private Button btnConfirm;
    private ImageView btnCancel;
    private Switch swAllday;
    private Button btnLocation;

    private int FLAG;

    public static String API_KEY = "AIzaSyBBSZMlEWrUVAZRccZCSOxEgxszII_kWhU";
    public static int AUTOCOMPLETE_REQUEST_CODE = 2;

    int color;

    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_agenda);

        final Intent intent = getIntent();
        final AgendaEvent temp = (AgendaEvent) intent.getSerializableExtra("AgendaEvent");
        FLAG = intent.getIntExtra("FLAG", bss.intern.planb.View.Home.View.FLAG_CREATE_NEW);

        color = temp.getColor();
        Window window = getWindow();
        window.setStatusBarColor(color);


        AgendaDatabase db = AgendaDatabase.getINSTANCE(getApplication());
        presenter = new Presenter(this, db.agendaEventDao());

        Places.initialize(getApplicationContext(), API_KEY);
        PlacesClient placesClient = Places.createClient(this);

        etName = findViewById(R.id.etName);
        etNote = findViewById(R.id.etNote);
        etLocation = findViewById(R.id.etLocation);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancel);
        swAllday = findViewById(R.id.swAllday);
        btnLocation = findViewById(R.id.btnLocation);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);

        final DateTime startDateTime = new DateTime(temp.getStartDay(), temp.getStartMonth(), temp.getStartYear(), temp.getStartHour(), temp.getStartMinute());
        final DateTime endDateTime = new DateTime(temp.getEndDay(), temp.getEndMonth(), temp.getEndYear(), temp.getEndHour(), temp.getEndMinute());

        etName.setText(temp.getName());
        etNote.setText(temp.getNote());
        etLocation.setText(temp.getLocation());

        // lần đầu tiên DatePickerDialog hiện lên sẽ show ngày mặc định là ngày hiện tại

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

        btnConfirm.setBackgroundColor(color);
        btnConfirm.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                String name = etName.getText().toString();
                String note = etNote.getText().toString();
                String location = etLocation.getText().toString();

                if (name.length() != 0 && note.length() != 0 && location.length() != 0) {
                    temp.setName(name);
                    temp.setNote(note);
                    temp.setLocation(location);
                    temp.setStartDay(startDateTime.getmDay());
                    temp.setStartMonth(startDateTime.getmMonth());
                    temp.setStartYear(startDateTime.getmYear());
                    temp.setStartHour(startDateTime.getmHour());
                    temp.setStartMinute(startDateTime.getmMinute());
                    temp.setEndDay(endDateTime.getmDay());
                    temp.setEndMonth(endDateTime.getmMonth());
                    temp.setEndYear(endDateTime.getmYear());
                    temp.setEndHour(endDateTime.getmHour());
                    temp.setEndMinute(endDateTime.getmMinute());
                    temp.setAllDay(swAllday.isChecked());
                    temp.setLatitude(place.getLatLng().latitude);
                    temp.setLongtitude(place.getLatLng().longitude);
                    if (FLAG == bss.intern.planb.View.Home.View.FLAG_CREATE_NEW) {
                        presenter.createAgenda(temp);
                    } else {

                        presenter.editAgenda(temp);
                    }
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("AgendaEvent", temp);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(View.this, "Please fill in all required field", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        btnCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                finish();
            }
        });

        etLocation.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .build(View.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        btnLocation.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (etLocation.getText().length() != 0) {
                    Intent intent = new Intent(View.this, bss.intern.planb.View.ShowOnMap.View.class);
                    if (FLAG == bss.intern.planb.View.Home.View.FLAG_EDIT) {
                        intent.putExtra("latitude", temp.getLatitude());
                        intent.putExtra("longitude", temp.getLongitude());
                        startActivity(intent);
                    } else {
                        intent.putExtra("latitude", place.getLatLng().latitude);
                        intent.putExtra("longitude", place.getLatLng().longitude);
                        intent.putExtra("FLAG", bss.intern.planb.View.Home.View.FLAG_TEMP_LOCATION);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void successful() {
        if (FLAG == bss.intern.planb.View.Home.View.FLAG_CREATE_NEW)
            Toast.makeText(this, "Success create new event", Toast.LENGTH_SHORT).show();
        if (FLAG == bss.intern.planb.View.Home.View.FLAG_EDIT)
            Toast.makeText(this, "Success update event", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = Autocomplete.getPlaceFromIntent(data);
                etLocation.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
