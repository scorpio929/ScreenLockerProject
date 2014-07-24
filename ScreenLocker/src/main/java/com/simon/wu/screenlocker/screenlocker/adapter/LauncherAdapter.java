package com.simon.wu.screenlocker.screenlocker.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simon.wu.screenlocker.screenlocker.R;

import java.util.List;

/**
 * Created by Administrator on 2014/7/17.
 */
public class LauncherAdapter extends ArrayAdapter {
    private List<ResolveInfo> appList;
    private int resource;
    private Context context;
    private PackageManager pm;

    public LauncherAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.appList = objects;
        pm = context.getPackageManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = View.inflate(context, resource, null);
        ImageView icon = (ImageView) layout.findViewById(R.id.icon);
        TextView appName = (TextView) layout.findViewById(R.id.launcher_name);
        ResolveInfo temp = appList.get(position);
        icon.setImageDrawable(temp.activityInfo.loadIcon(pm));
        appName.setText(temp.activityInfo.loadLabel(pm));
        return layout;
    }
}
