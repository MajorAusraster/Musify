package com.timon.gugb.musify.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.timon.gugb.musify.R;

/**
 * Created by Timon on 04.03.2016.
 */
public class ControlView extends FrameLayout {


    public static final int MODE_REPEAT=212;
    public static final int MODE_REPEAT_ONE=2312;
    public static final int MODE_SHUFFLE=12;

    private int currentMode=MODE_REPEAT;

    private TextView titleView,artistView;
    private ImageButton toggleModeBotton;
    private RelativeLayout mainContentHolder;

    public ControlView(Context context) {
        super(context);
        init(context);
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context c){
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View holder = inflater.inflate(R.layout.controler_view, null);

        mainContentHolder= (RelativeLayout) holder.findViewById(R.id.controller_mainLayout);
        titleView= (TextView) mainContentHolder.findViewById(R.id.controller_title);
        artistView= (TextView) mainContentHolder.findViewById(R.id.controller_artist);
        toggleModeBotton= (ImageButton) mainContentHolder.findViewById(R.id.controller_button);

        addView(mainContentHolder);
    }

    public void setText(CharSequence title,CharSequence artist){
        titleView.setText(title);
        artistView.setText(artist);
    }

    public int getCurrentMode(){
        return currentMode;
    }

    public void setCurrentMode(int currentMode){
        this.currentMode=currentMode;
        switch (currentMode){
            case MODE_REPEAT:
                toggleModeBotton.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat_24dp));
                break;
            case MODE_REPEAT_ONE:
                toggleModeBotton.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat_one_24dp));
                break;
            case MODE_SHUFFLE:
                toggleModeBotton.setImageDrawable(getResources().getDrawable(R.drawable.ic_shuffle_24dp));
                break;

        }
    }

}