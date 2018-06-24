package views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.constructure.R;

import java.util.List;

import data.Team;

public class TeamAdapter extends ArrayAdapter<Team> {

    private Context mContext;
    private LayoutInflater mInflater;

    public TeamAdapter(@NonNull Context context, int resource, @NonNull List<Team> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Team team = (Team)getItem(position);
        TeamItemViewHolder teamViewHolder;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.team_item,null);
            teamViewHolder = new TeamItemViewHolder();
            teamViewHolder.name = (TextView)convertView.findViewById(R.id.team_item_name);
            convertView.setTag(teamViewHolder);
        }else{
            // view = convertView;
            teamViewHolder = (TeamItemViewHolder) convertView.getTag();
        }

        teamViewHolder.name.setText(team.getName());
       /* Bitmap bg = project.getPicture();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bg);
        teamViewHolder.image.setBackgroundDrawable(bitmapDrawable);*/
        return convertView;
    }
}
class TeamItemViewHolder{
    TextView name;
}