package com.simon.wu.screenlocker.screenlocker.views;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.simon.wu.screenlocker.screenlocker.R;
import com.simon.wu.screenlocker.screenlocker.adapter.LauncherAdapter;
import com.simon.wu.screenlocker.screenlocker.utils.LocalData;

import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link LauncherListDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LauncherListDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static LauncherListDialog fragment;
    private ListView launcherList;

    // TODO: Rename and change types of parameters
    private List<ResolveInfo> appList;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LauncherListDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static LauncherListDialog newInstance(List<ResolveInfo> appList) {
        if (fragment == null)
            fragment = new LauncherListDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (java.util.ArrayList<? extends android.os.Parcelable>) appList);
        fragment.setArguments(args);
        return fragment;
    }

    public LauncherListDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        if (getArguments() != null) {
            appList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launcher_list_dialog, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        launcherList = (ListView) this.getView().findViewById(R.id.launcher_list);
        LauncherAdapter launcherAdapter = new LauncherAdapter(getActivity(), R.layout.launcher_list_item, appList);
        launcherList.setAdapter(launcherAdapter);
        launcherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳到当前launcher
                ResolveInfo temp = appList.get(position);
                LocalData.currentLauncher = temp;
                Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER).addCategory(Intent.CATEGORY_HOME);
                intent.setComponent(new ComponentName(temp.activityInfo.packageName, temp.activityInfo.name));
                startActivity(intent);
                LauncherListDialog.fragment.dismiss();
            }
        });
    }

}
