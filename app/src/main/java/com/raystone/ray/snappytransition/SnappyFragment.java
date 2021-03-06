package com.raystone.ray.snappytransition;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewSwitcher;


import org.w3c.dom.ProcessingInstruction;

import java.util.Date;
import java.util.logging.SocketHandler;

/**
 * Created by Ray on 1/9/2016.
 */
public class SnappyFragment extends Fragment {

    private View mView;
    private TextSwitcher mTextSwitcher;
    private Button mButton;
    private static final String[] TEXTS = {"First","Second","Third"};
    private int mPosition = 0;
    private Button mDatePicker;
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private TextView mChangeFormat;
    private View mPortraitContent;
    private VideoView mVideoView;
    private View mPortraitPosition;

    public static SnappyFragment newInstance()
    {return new SnappyFragment();}

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mView = null;
        mTextSwitcher = null;
        mButton = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,container,savedInstanceState);


        mView = inflater.inflate(R.layout.activity_main,container,false);

        mButton = (Button)mView.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextSwitcher.setText(TEXTS[mPosition]);
                mPosition = (mPosition + 1) % TEXTS.length;
            }
        });

        mDatePicker = (Button)mView.findViewById(R.id.date_picker_button);
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setTargetFragment(SnappyFragment.this,REQUEST_DATE);
                datePickerFragment.show(getFragmentManager(),DIALOG_DATE);
            }
        });

        mChangeFormat = (TextView)mView.findViewById(R.id.text_format);
        String text = "visit <a href = \"https://play.google.com/store/apps/details?id=com.raystone.ray.goplaces\">my homepage</a>";
        mChangeFormat.setText(Html.fromHtml(text));
        mChangeFormat.setMovementMethod(LinkMovementMethod.getInstance());

        Animation in = AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_out);
        mTextSwitcher = (TextSwitcher)mView.findViewById(R.id.text_switch);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(in);
        mTextSwitcher.setOutAnimation(out);
        mView.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Snappy Fragment","Height: "+Integer.toString(mView.getHeight()));
                Toast toast = Toast.makeText(getActivity(),"Height: "+Integer.toString(mView.getHeight()), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT,100,100);
                toast.show();
            }
        });


        /*
        if(savedInstanceState == null) {
            mView = inflater.inflate(R.layout.rotate_video, container, false);
            mPortraitContent = (View) mView.findViewById(R.id.main_portrait_content);
            mPortraitPosition = (View) mView.findViewById(R.id.main_portrait_position);
            mVideoView = (VideoView) mView.findViewById(R.id.main_videoview);
            mVideoView.post(new Runnable() {
                @Override
                public void run() {
                    initVideoView();
                }
            });}
            */

        return mView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_DATE:
                if(resultCode != Activity.RESULT_OK)
                    return;
                else
                {
                    Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                    mDatePicker.setText(date.toString());
                }
                break;
        }
    }

    private void setVideo()
    {
        switch(getResources().getConfiguration().orientation)
        {
            case Configuration.ORIENTATION_LANDSCAPE:
            {
                mPortraitContent.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
                        .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                mVideoView.setLayoutParams(params);
                break;
            }

            case Configuration.ORIENTATION_UNDEFINED:
            case Configuration.ORIENTATION_PORTRAIT:
            default:
            {
                mPortraitContent.setVisibility(View.VISIBLE);
                int[] locationArray = new int[2];
                mPortraitPosition.getLocationOnScreen(locationArray);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mPortraitPosition.getWidth(),mPortraitPosition.getHeight());
                params.leftMargin = locationArray[0];
                params.rightMargin = locationArray[1];
                mVideoView.setLayoutParams(params);
                break;
            }
        }
    }

    private void initVideoView() {
        mVideoView.setMediaController(new MediaController(getActivity()));

        // dummy path
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/"
                + "bigbuck");
        //raw/bigbuck.m4v  //F:\AndroidStepByStep\SnappyTransition\app\src\main\res\raw\bigbuck.m4v
        mVideoView.setVideoURI(uri);
        setVideo();
        mVideoView.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        setVideo();
        super.onConfigurationChanged(newConfig);
    }

}
