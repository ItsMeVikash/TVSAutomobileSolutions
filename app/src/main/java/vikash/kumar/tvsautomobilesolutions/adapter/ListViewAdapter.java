package vikash.kumar.tvsautomobilesolutions.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import vikash.kumar.tvsautomobilesolutions.R;
import vikash.kumar.tvsautomobilesolutions.activities.MainActivity;
import vikash.kumar.tvsautomobilesolutions.model_pojo.DataModel;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<DataModel> arraylist;

    public ListViewAdapter(Context context ) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<DataModel>();
        this.arraylist.addAll(MainActivity.staffNameArrayList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return MainActivity.staffNameArrayList.size();
    }

    @Override
    public DataModel getItem(int position) {
        return MainActivity.staffNameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(MainActivity.staffNameArrayList.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        MainActivity.staffNameArrayList.clear();
        if (charText.length() == 0) {
            MainActivity.staffNameArrayList.addAll(arraylist);
        } else {
            for (DataModel wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainActivity.staffNameArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}