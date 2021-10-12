package anthony.brenon.mareu.ui_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import anthony.brenon.mareu.callback.Listener;
import anthony.brenon.mareu.databinding.ActivityListMareuBinding;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;

public class ListMareuActivity extends AppCompatActivity implements Listener {

    private ActivityListMareuBinding binding;

    private MeetingApiService service;
    private List<Meeting> meetings;
    private MeetingRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        openAddMeeting();

        service = DI.getMeetingApiService();
        meetings = service.getMeetingList();

        adapter = new MeetingRecyclerViewAdapter(meetings, this);
        binding.listMeetingsRecyclerView.setAdapter(adapter);
        binding.listMeetingsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    /**
     * Open add meeting
     */
    private void openAddMeeting() {
        binding.listMeetingFabAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getBaseContext(), AddMeetingActivity.class);
            startActivity(intent);
        });
    }
    /**
     * init activity
     */
    private void initView() {
        binding = ActivityListMareuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    /**
     * remove a item in list
     * @param position
     */
    @Override
    public void onClickDeleteButton(int position) {
        service.deleteMeeting(meetings.get(position));
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, meetings.size());
    }


}