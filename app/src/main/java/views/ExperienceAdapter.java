package views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.constructure.R;

import java.util.List;

import data.Experience;

public class ExperienceAdapter extends ArrayAdapter<Experience> {

    private int resourceId;

    public ExperienceAdapter(@NonNull Context context, int resource, @NonNull List<Experience> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Experience experience = (Experience)getItem(position);
        View view;
        ExperienceViewHolder experienceViewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            experienceViewHolder = new ExperienceViewHolder();
            experienceViewHolder.team = (TextView)view.findViewById(R.id.exp_team);
            experienceViewHolder.project = (TextView)view.findViewById(R.id.exp_project);
            experienceViewHolder.start = (TextView)view.findViewById(R.id.exp_start);
            experienceViewHolder.end = (TextView)view.findViewById(R.id.exp_end);
            view.setTag(experienceViewHolder);
        }
        else {
            view = convertView;
            experienceViewHolder = (ExperienceViewHolder)view.getTag();
        }

        experienceViewHolder.team.setText(experience.getTeam());
        experienceViewHolder.project.setText(experience.getProject());
        experienceViewHolder.start.setText(experience.getStart());
        experienceViewHolder.end.setText(experience.getEnd());
        return view;
    }
}

class ExperienceViewHolder{
    TextView team;
    TextView project;
    TextView start;
    TextView end;
}
