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

import data.Project;

public class ProjectAdapter extends ArrayAdapter<Project> {

    private Context mContext;
    private LayoutInflater mInflater;

    public ProjectAdapter(@NonNull Context context, int resource, @NonNull List<Project> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Project project = (Project) getItem(position);
        TeamViewHolder teamViewHolder;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.project_item,null);
            teamViewHolder = new TeamViewHolder();
            teamViewHolder.name = (TextView)convertView.findViewById(R.id.project_name);
            teamViewHolder.image = (LinearLayout) convertView.findViewById(R.id.project_image);
            convertView.setTag(teamViewHolder);
        }else{
            // view = convertView;
            teamViewHolder = (TeamViewHolder) convertView.getTag();
        }

        teamViewHolder.name.setText(project.getName());
       /* Bitmap bg = project.getPicture();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bg);
        teamViewHolder.image.setBackgroundDrawable(bitmapDrawable);*/
        return convertView;
    }
}
class TeamViewHolder{
    TextView name;
    LinearLayout image;
}