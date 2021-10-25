package anthony.brenon.mareu.ui_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.callback.Listener;
import anthony.brenon.mareu.databinding.ActivityListMareuBinding;
import anthony.brenon.mareu.di.DI;
import anthony.brenon.mareu.model.Meeting;
import anthony.brenon.mareu.service.MeetingApiService;

public class ListMareuActivity extends AppCompatActivity implements Listener, DatePickerDialog.OnDateSetListener {

    private ActivityListMareuBinding binding;

    private MeetingApiService service;
    private List<Meeting> meetings;
    private MeetingRecyclerViewAdapter adapter;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        openAddMeeting();

        service = DI.getMeetingApiService();
        meetings = service.getMeetingList();

        initList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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

    private void initList() {
        adapter = new MeetingRecyclerViewAdapter(meetings, this);
        binding.listMeetingsRecyclerView.setAdapter(adapter);
        binding.listMeetingsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
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

    //menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        return true;
    }

    //TODO a completer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.room_filter:

                return true;
            case R.id.date_filter:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getYear();
        meetings = service.filterDateList(date);
        initList();
    }
}