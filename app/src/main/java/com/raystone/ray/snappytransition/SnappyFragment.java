package com.raystone.ray.snappytransition;

import android.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.w3c.dom.ProcessingInstruction;

/**
 * Created by Ray on 1/9/2016.
 */
public class SnappyFragment extends Fragment {

    private View mView;
    private TextSwitcher mTextSwitcher;
    private Button mButton;
    private static final String[] TEXTS = {"First","Second","Third"};
    private int mPosition = 0;

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
        return mView;
    }

}
