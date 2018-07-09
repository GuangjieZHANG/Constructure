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

import data.WorkerSearched;

public class WorkerSearchedAdapter extends ArrayAdapter {

    private int resourceId;

    public WorkerSearchedAdapter(@NonNull Context context, int resource, @NonNull List<WorkerSearched> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WorkerSearched workerSearched = (WorkerSearched) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.cci = (TextView)view.findViewById(R.id.cci);
            viewHolder.certified = (ImageView)view.findViewById(R.id.certified_image);
            viewHolder.info = (TextView)view.findViewById(R.id.info);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        if(workerSearched.isCertified()){
            viewHolder.info.setText(workerSearched.getName()+";"+workerSearched.getHometown()+";已认证");
            viewHolder.certified.setImageResource(R.drawable.certified);
            viewHolder.cci.setText(workerSearched.getCci()+"%");
        }else {
            viewHolder.info.setText(workerSearched.getName()+";"+workerSearched.getHometown()+";"+workerSearched.getNotes());
            viewHolder.cci.setText(workerSearched.getCci()+"%");
            viewHolder.certified.setImageResource(R.drawable.non_certified);
        }

        return view;
    }
}

class ViewHolder{
    ImageView certified;
    TextView cci;
    TextView info;
}