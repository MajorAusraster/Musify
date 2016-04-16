package com.timon.gugb.musify.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timon.gugb.musify.R;

/**
 * Created by Timon on 04.03.2016.
 */
public class ControlView extends LinearLayout  {


    public static final int MODE_REPEAT=212;
    public static final int MODE_REPEAT_ONE=2312;
    public static final int MODE_SHUFFLE=12;

    public static final int BUTTON_TOGGLE_MODE=22;
    public static final int BUTTON_NEXT=2;
    public static final int BUTTON_PREVIOUS=800;

    private int currentMode=MODE_REPEAT;

    private TextView titleView,artistView;
    private ImageButton toggleModeBotton;
    private RelativeLayout mainContentHolder;
    private ImageView coverView;
    private ImageButton next,previous;

    private onClickListener listener;

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
        coverView= (ImageView) mainContentHolder.findViewById(R.id.controller_cover);
        next= (ImageButton) mainContentHolder.findViewById(R.id.controller_next);
        previous= (ImageButton) mainContentHolder.findViewById(R.id.controller_prev);
        addView(mainContentHolder);

        initListeners();

    }

    private void initListeners() {

        toggleModeBotton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(BUTTON_TOGGLE_MODE);
                }
            }
        });

        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(BUTTON_NEXT);
                }
            }
        });

        previous.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(BUTTON_PREVIOUS);
                }
            }
        });


    }

    public void setOnClickListener(onClickListener listener){
        this.listener=listener;
    }

    public void setText(CharSequence title,CharSequence artist){
        titleView.setText(title);
        artistView.setText(artist);
    }

    public int getCurrentMode(){
        return currentMode;
    }

    public void setCover(Bitmap bm){
        coverView.setPadding(0,0,0,0);
        coverView.setImageBitmap(bm);
    }

    public void setCoverToDefault(){
        coverView.setImageDrawable(getResources().getDrawable(R.drawable.ic_audiotrack_24dp));
       int padding= ((int) getResources().getDimension(R.dimen.padding_cover));
        coverView.setPadding(padding,padding,padding,padding);
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

    public interface onClickListener{
        void onClick(int item);
    }

}
