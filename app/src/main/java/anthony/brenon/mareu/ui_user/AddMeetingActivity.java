package anthony.brenon.mareu.ui_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.databinding.ActivityAddMeetingBinding;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private ActivityAddMeetingBinding binding;
    private Meeting meeting;
    private MeetingApiService service;
    int hour, minute;
    String timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        getSupportActionBar().setTitle("Create new meeting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = DI.getMeetingApiService();

        //get populate meeting
        meeting = new Meeting(
                binding.tiEdTopic.getText().toString(),
                binding.tiEdRoom.getText().toString(),
                binding.tiEdParticipants.getText().toString(),
                binding.btnPickerDate.getText().toString());

        //listener PickerDate
        binding.btnPickerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        //listener PickerTime
        binding.btnPickerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        //listener create meeting
        binding.buttonCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.createMeeting(meeting);
            }
        });
    }

    private void initView() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    //DatePicker
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = datePicker.getDayOfMonth() + " / " + datePicker.getMonth() + " / " + datePicker.getYear();
        binding.btnPickerDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        binding.btnPickerTime.setText(Calendar.HOUR_OF_DAY + " : " + Calendar.MINUTE);
    }
}