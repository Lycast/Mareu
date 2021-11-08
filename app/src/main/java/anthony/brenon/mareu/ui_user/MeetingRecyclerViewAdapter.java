package anthony.brenon.mareu.ui_user;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anthony.brenon.mareu.R;
import anthony.brenon.mareu.callback.OnDeleteClick;
import anthony.brenon.mareu.databinding.ItemMeetingBinding;
import anthony.brenon.mareu.model.Meeting;

/**
 * Created by Lycast on 16/09/2021.
 */
class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private List<Meeting> meetings;
    private OnDeleteClick callback;

    public MeetingRecyclerViewAdapter(List<Meeting> meetings, OnDeleteClick callback) {
        this.meetings = meetings;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);

        String populateReport;
        holder.binding.imgRowMeeting.setColorFilter(meeting.getColor(), PorterDuff.Mode.SRC_ATOP);
        Resources res = holder.itemView.getContext().getResources();
        populateReport = String.format(res.getString(R.string.meeting_populate), meeting.getTopic(), meeting.getTime(), meeting.getRoom());
        holder.binding.tvRowReportMeeting.setText(populateReport);

        holder.binding.tvRowParticipant.setText(meeting.getParticipants());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemMeetingBinding binding;

        public ViewHolder(ItemMeetingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.imgRowDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (callback != null) {
                callback.onClickDeleteButton(getAdapterPosition());
            }
        }
    }
}



