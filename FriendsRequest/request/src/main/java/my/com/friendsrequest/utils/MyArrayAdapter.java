package my.com.friendsrequest.utils;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import my.com.friendsrequest.Friend;
import my.com.friendsrequest.R;


public class MyArrayAdapter extends BaseAdapter {

    private List<Friend> items;
    private LayoutInflater layoutInflater;

    public MyArrayAdapter(Context context, List<Friend> items) {
        super();
        layoutInflater = LayoutInflater.from(context);
        this.items = items;
    }

    private static class ViewHolder {
        TextView textView;
        String key;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Friend getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_activity, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.list_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.key = (items.get(position)).toString();
        viewHolder.textView.setText(String.valueOf(" " + (position + 1)).concat(". ").concat(viewHolder.key));
        return convertView;
    }

}
