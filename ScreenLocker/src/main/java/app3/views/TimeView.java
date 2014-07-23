package app3.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simon.wu.screenlock.app3.R;
import com.simon.wu.screenlock.app3.utils.TimerManager;

/**
 * TODO: document your custom view class.
 */
public class TimeView extends RelativeLayout {
    private View timeLayout;
    private TextView timeTv;
    private TextView dateTv;
    private Context mContext;
    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                setTime();
            }
        }
    };

    private void setTime() {
        timeTv.setText(TimerManager.getTime());
        dateTv.setText(TimerManager.getDate());
    }

    public TimeView(Context context) {
        super(context);
        mContext = context;
        timeLayout = View.inflate(context, R.layout.layout_module_launchtime, this);
        initViews();
        setTime();
    }

    private void initViews() {
        timeTv = (TextView) timeLayout.findViewById(R.id.tvtime);
        dateTv = (TextView) timeLayout.findViewById(R.id.tvdate);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Toast.makeText(mContext, "u can see me", Toast.LENGTH_SHORT).show();
        mContext.registerReceiver(timeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Toast.makeText(mContext, "u can't see me anymore", Toast.LENGTH_SHORT).show();
        mContext.unregisterReceiver(timeReceiver);
    }
}
