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

import data.CurrentWorker;

public class CurrentWorkerAdapter extends ArrayAdapter<CurrentWorker> {

    private int resourceId;
    private Context mContext;
    private LayoutInflater mInflater;

    public CurrentWorkerAdapter(@NonNull Context context, int resource, @NonNull List<CurrentWorker> objects) {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CurrentWorker currentWorker = (CurrentWorker)getItem(position);
        CW_ViewHolder viewHolder;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.current_worker_item,null);
            viewHolder = new CW_ViewHolder();
            viewHolder.note = (TextView)convertView.findViewById(R.id.current_worker_role);
            viewHolder.number = (TextView) convertView.findViewById(R.id.current_worker_number);
            viewHolder.speciality = (TextView)convertView.findViewById(R.id.current_worker_type);
            convertView.setTag(viewHolder);
        }else{
           // view = convertView;
            viewHolder = (CW_ViewHolder)convertView.getTag();
        }

        viewHolder.note.setText(currentWorker.getNote());
        viewHolder.speciality.setText(currentWorker.getSpeciality());
        viewHolder.number.setText(currentWorker.getNumber()+"人");

        return convertView;
    }


}

class CW_ViewHolder{

    TextView note;
    TextView number;
    TextView speciality;
}