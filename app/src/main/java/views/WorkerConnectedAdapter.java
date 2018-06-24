package views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.constructure.R;

import java.util.List;

import data.Worker;

public class WorkerConnectedAdapter extends ArrayAdapter<Worker> {

    private Context mContext;
    private LayoutInflater mInflater;

    public WorkerConnectedAdapter(@NonNull Context context, int resource, @NonNull List<Worker> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Worker worker = (Worker)getItem(position);
        WorkerViewHolder workerViewHolder;

        if(convertView==null){
            convertView = mInflater.inflate(R.layout.worker_connected_item,null);
            workerViewHolder = new WorkerViewHolder();
            workerViewHolder.name = (TextView)convertView.findViewById(R.id.worker_connected_name);
            workerViewHolder.role = (TextView)convertView.findViewById(R.id.worker_connected_role);
            workerViewHolder.speciality = (TextView)convertView.findViewById(R.id.worker_connected_speciality);
            workerViewHolder.image = (ImageView)convertView.findViewById(R.id.worker_connected_image);
            convertView.setTag(workerViewHolder);
        }else{
            // view = convertView;
            workerViewHolder = (WorkerViewHolder) convertView.getTag();
        }

        workerViewHolder.name.setText(worker.getName());
        workerViewHolder.role.setText(worker.getNote());
        workerViewHolder.speciality.setText(worker.getSpeciality());
        //image
       /* Bitmap bg = project.getPicture();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bg);
        teamViewHolder.image.setBackgroundDrawable(bitmapDrawable);*/
        return convertView;
    }
}
class WorkerViewHolder{
    TextView role;
    TextView name;
    TextView speciality;
    ImageView image;
}
