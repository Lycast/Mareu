package anthony.brenon.mareu.ui_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.databinding.ActivityAddMeetingBinding;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;

public class AddMeetingActivity extends AppCompatActivity {

    private ActivityAddMeetingBinding binding;
    private Meeting meeting;
    private MeetingApiService service;

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
                binding.tiEdTime.getText().toString());

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
}