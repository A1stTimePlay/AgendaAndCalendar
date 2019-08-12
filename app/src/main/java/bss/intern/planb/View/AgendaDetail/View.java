package bss.intern.planb.View.AgendaDetail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bss.intern.planb.Database.AgendaDatabase;
import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Presenter.EventDetail.Presenter;
import bss.intern.planb.R;
import bss.intern.planb.Util.DateTime;
import bss.intern.planb.WeekView.WeekViewEvent;

public class View extends AppCompatActivity implements IView {
    private Presenter presenter;

    private TextView tvName;
    private TextView tvNote;
    private TextView tvLocation;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private ImageView btnCancel;
    private ImageView btnEdit;
    private ImageView btnRemove;
    private android.view.View color;
    private AgendaEvent DisplayedAgendaEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_detail);

        Intent intent = getIntent();
        final WeekViewEvent temp = (WeekViewEvent) intent.getSerializableExtra("WeekViewEvent");

        AgendaDatabase db = AgendaDatabase.getINSTANCE(getApplication());
        presenter = new Presenter(this, db.agendaEventDao());

        tvName = findViewById(R.id.tvDetailName);
        tvNote = findViewById(R.id.tvDetailNote);
        tvLocation = findViewById(R.id.tvDetailLocation);
        tvStartDate = findViewById(R.id.tvDetailStartDate);
        tvEndDate = findViewById(R.id.tvDetailEndDate);
        btnCancel = findViewById(R.id.btnDetailCancel);
        btnEdit = findViewById(R.id.btnDetailEdit);
        btnRemove = findViewById(R.id.btnDetailRemove);
        color = findViewById(R.id.color);

        presenter.getAgendaEventById(temp);

        btnCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(View.this, bss.intern.planb.View.Home.View.class);
                startActivity(intent);
            }
        });

        btnRemove.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.deleteAgendaEvent(temp);
                Intent intent = new Intent(View.this, bss.intern.planb.View.Home.View.class);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(View.this, bss.intern.planb.View.AddAgenda.View.class);
                intent.putExtra("AgendaEvent", DisplayedAgendaEvent);
                intent.putExtra("FLAG", bss.intern.planb.View.Home.View.FLAG_EDIT);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void displayAgendaEventDetail(AgendaEvent agendaEvent) {
        DateTime start = new DateTime(agendaEvent.getStartDay(), agendaEvent.getStartMonth(), agendaEvent.getStartYear(), agendaEvent.getStartHour(), agendaEvent.getStartMinute());
        DateTime end = new DateTime(agendaEvent.getEndDay(), agendaEvent.getEndMonth(), agendaEvent.getEndYear(), agendaEvent.getEndHour(), agendaEvent.getEndMinute());
        tvName.setText(agendaEvent.getName());
        tvNote.setText(agendaEvent.getNote());
        tvLocation.setText(agendaEvent.getLocation());
        tvStartDate.setText(start.dateToString() + " - " + start.timeToString());
        tvEndDate.setText(end.dateToString() + " - " + end.timeToString());
        color.setBackgroundColor(agendaEvent.getColor());
        this.DisplayedAgendaEvent = agendaEvent;
    }

    @Override
    public void successfulDetele() {
        Toast toast = Toast.makeText(this, "Successful delete", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void successfulEdit() {
        Toast toast = Toast.makeText(this, "Successful edit", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("hello world");
                AgendaEvent temp = (AgendaEvent) data.getSerializableExtra("AgendaEvent");
                displayAgendaEventDetail(temp);
            }
            if (requestCode == Activity.RESULT_CANCELED) {
            }
        }
    }
}
