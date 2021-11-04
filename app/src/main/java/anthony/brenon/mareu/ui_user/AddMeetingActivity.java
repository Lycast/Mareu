package anthony.brenon.mareu.ui_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import java.util.Random;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.databinding.ActivityAddMeetingBinding;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityAddMeetingBinding binding;
    private Meeting meeting;
    private MeetingApiService service;

    //list rooms
    public static final String[] ROOMS = new String[]{
            "Berlin", "Londres", "Luxembourg", "Madrid", "Moscou", "Paris", "Pekin", "Rome", "Tokyo", "Washington"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        createMeetingListener();
        addParticipantListener();

        getSupportActionBar().setTitle("Create new meeting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = DI.getMeetingApiService();

        //autoComplete rooms list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ROOMS);
        binding.tiEdRoom.setAdapter(adapter);

        //listener PickerDate
        binding.btnPickerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
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
    }



    /**
     * initialise view
      */
    private void initView() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
        binding.btnPickerDate.setText(date);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        binding.btnPickerTime.setText(String.format("%02d", hour) + "h" + String.format("%02d", minute));
    }


    /**
     * group chip add participant
     */
    private void addParticipant() {
        Chip chip = new Chip(this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setChipIconResource(R.drawable.ic_baseline_contact_mail_24);
        chip.setText(binding.tiEdParticipants.getText().toString());

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.chipGroup.removeView(chip);
            }
        });
        binding.chipGroup.addView(chip);
        binding.tiEdParticipants.setText("");
    }


    /**
     * email valid test
     * @param email
     * @return
     */
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * listener add participant
     */
    void addParticipantListener() {
        binding.tiEdParticipants.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isEmailValid(v.getText())) {
                        addParticipant();
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.not_mail_valid), Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * Listener create meeting
     */
    private void createMeetingListener() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        binding.buttonCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get chip group text
                StringBuilder emailsText = new StringBuilder("");
                for (int i = 0; i < binding.chipGroup.getChildCount(); i++) {
                    String email = ((Chip) binding.chipGroup.getChildAt(i)).getText().toString();
                    emailsText.append(email);
                    emailsText.append(", ");
                }
                //populate meeting
                meeting = new Meeting(
                        binding.tiEdTopic.getText().toString(),
                        binding.tiEdRoom.getText().toString(),
                        emailsText.toString(),
                        binding.btnPickerDate.getText().toString(),
                        binding.btnPickerTime.getText().toString(),
                        color
                );
                service.createMeeting(meeting);
                finish();
            }
        });
    }
}