package anthony.brenon.mareu.ui_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import java.util.Objects;
import java.util.Random;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.databinding.ActivityAddMeetingBinding;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityAddMeetingBinding binding;
    private MeetingApiService service;
    private StringBuilder emailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        addParticipantListener();
        implementsListeners();
        createMeetingListener();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.create_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = DI.getMeetingApiService();

        //autoComplete rooms list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.list_room));
        binding.tiEdRoom.setAdapter(adapter);
    }

    // implements listener date picker and time picker
    private void implementsListeners() {
        //listener PickerDate
        binding.btnPickerDate.setOnClickListener(view -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "datePicker");
        });
        //listener PickerTime
        binding.btnPickerTime.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });
    }

    // initialisation view
    private void initView() {
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    // set date picker
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
        binding.btnPickerDate.setText(date);
    }

    // set time picker
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        binding.btnPickerTime.setText(String.format("%02d", hour) + "h" + String.format("%02d", minute));
    }

    // add participant with chip group
    private void addParticipant() {
        Chip chip = new Chip(this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setChipIconResource(R.drawable.ic_baseline_contact_mail_24);
        chip.setText(Objects.requireNonNull(binding.tiEdParticipants.getText()).toString());

        chip.setOnCloseIconClickListener(view -> binding.chipGroup.removeView(chip));
        binding.chipGroup.addView(chip);
        binding.tiEdParticipants.setText("");
    }

    //check if mail is valid
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // listener for add participants
    private void addParticipantListener() {
        binding.tiEdParticipants.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isEmailValid(v.getText())) {
                    addParticipant();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.not_mail_valid), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    // listener for create meeting
    private void createMeetingListener() {
        binding.buttonCreateMeeting.setOnClickListener(view -> {
            //get chip group text
            emailsText = new StringBuilder();
            for (int i = 0; i < binding.chipGroup.getChildCount(); i++) {
                String email = ((Chip) binding.chipGroup.getChildAt(i)).getText().toString();
                emailsText.append(email);
                emailsText.append(", ");
            }
            if (!Objects.requireNonNull(binding.tiEdTopic.getText()).toString().isEmpty() &&
                    !binding.tiEdRoom.getText().toString().isEmpty() &&
                    !emailsText.toString().isEmpty() &&
                    !binding.btnPickerDate.getText().toString().isEmpty() &&
                    !binding.btnPickerTime.getText().toString().isEmpty()) {
                    populateMeeting ();
                    finish();
            } else {
                Toast.makeText(this, R.string.create_meeting_is_empty, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //populate meeting
    private void populateMeeting () {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        Meeting meeting = new Meeting(
                Objects.requireNonNull(binding.tiEdTopic.getText()).toString(),
                binding.tiEdRoom.getText().toString(),
                emailsText.toString(),
                binding.btnPickerDate.getText().toString(),
                binding.btnPickerTime.getText().toString(),
                color
        );
        service.createMeeting(meeting);
    }
}