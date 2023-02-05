package zxc.studio.vpt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import zxc.studio.vpt.ui.elements.colourManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pallette#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pallette extends Fragment implements View.OnClickListener {

    private static final String TAG = "palette_fragment";

    private Button decreaseRedHeader;
    private Button increaseRedHeader;
    private Button decreaseGreenHeader;
    private Button increaseGreenHeader;
    private Button decreaseBlueHeader;
    private Button increaseBlueHeader;
    private Button decreaseRedTitle;
    private Button increaseRedTitle;
    private Button decreaseGreenTitle;
    private Button increaseGreenTitle;
    private Button decreaseBlueTitle;
    private Button increaseBlueTitle;
    private Button decreaseRedBorder;
    private Button increaseRedBorder;
    private Button decreaseGreenBorder;
    private Button increaseGreenBorder;
    private Button decreaseBlueBorder;
    private Button increaseBlueBorder;
    private Button decreaseRedSubtract;
    private Button increaseRedSubtract;
    private Button decreaseGreenSubtract;
    private Button increaseGreenSubtract;
    private Button decreaseBlueSubtract;
    private Button increaseBlueSubtract;
    private Button decreaseRedBackground;
    private Button increaseRedBackground;
    private Button decreaseGreenBackground;
    private Button increaseGreenBackground;
    private Button decreaseBlueBackground;
    private Button increaseBlueBackground;

    private SeekBar redSeekBarHeader;
    private SeekBar blueSeekBarHeader;
    private SeekBar greenSeekBarHeader;
    private SeekBar redSeekBarTitle;
    private SeekBar blueSeekBarTitle;
    private SeekBar greenSeekBarTitle;
    private SeekBar redSeekBarBorder;
    private SeekBar blueSeekBarBorder;
    private SeekBar greenSeekBarBorder;
    private SeekBar redSeekBarSubtract;
    private SeekBar blueSeekBarSubtract;
    private SeekBar greenSeekBarSubtract;
    private SeekBar redSeekBarBackground;
    private SeekBar blueSeekBarBackground;
    private SeekBar greenSeekBarBackground;

    private TextView redProgressTitle;
    private TextView greenProgressTitle;
    private TextView blueProgressTitle;
    private TextView redProgressHeader;
    private TextView greenProgressHeader;
    private TextView blueProgressHeader;
    private TextView redProgressBorder;
    private TextView greenProgressBorder;
    private TextView blueProgressBorder;
    private TextView redProgressSubtract;
    private TextView greenProgressSubtract;
    private TextView blueProgressSubtract;
    private TextView redProgressBackground;
    private TextView greenProgressBackground;
    private TextView blueProgressBackground;
    private TextView headerColourTextView;
    private TextView titleColourTextView;
    private TextView borderColourTextView;
    private TextView subtractColourTextView;
    private TextView backgroundColourTextView;
    private TextView expandHeader;
    private TextView expandTitle;
    private TextView expandBorder;
    private TextView expandSubtract;
    private TextView expandBackground;
    private TextView presetName;
    private TextView presetLeft;
    private TextView presetRight;

    private LinearLayout headerColourOptionsLinearLayout;
    private LinearLayout titleColourOptionsLinearLayout;
    private LinearLayout borderColourOptionsLinearLayout;
    private LinearLayout subtractColourOptionsLinearLayout;
    private LinearLayout backgroundColourOptionsLinearLayout;

    private int redHeader;
    private int greenHeader;
    private int blueHeader;
    private int redTitle;
    private int greenTitle;
    private int blueTitle;
    private int redBorder;
    private int greenBorder;
    private int blueBorder;
    private int redSubtract;
    private int greenSubtract;
    private int blueSubtract;
    private int redBackground;
    private int greenBackground;
    private int blueBackground;


    public pallette() {
    }

    public static pallette newInstance() {
        pallette fragment = new pallette();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pallette, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view){
        setIDS(view);
        setListeners();
    }

    private void setIDS(View view){
        headerColourTextView=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewHeader);
        titleColourTextView=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewTitle);
        borderColourTextView=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewBorder);
        subtractColourTextView=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewSubtract);
        backgroundColourTextView=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewBackground);
        headerColourOptionsLinearLayout=view.findViewById(R.id.palletteFragment_linearLayout_changeColoursLayoutHeader);
        titleColourOptionsLinearLayout=view.findViewById(R.id.palletteFragment_linearLayout_changeColoursLayoutTitle);
        borderColourOptionsLinearLayout=view.findViewById(R.id.palletteFragment_linearLayout_changeColoursLayoutBorder);
        subtractColourOptionsLinearLayout=view.findViewById(R.id.palletteFragment_linearLayout_changeColoursLayoutSubtract);
        backgroundColourOptionsLinearLayout=view.findViewById(R.id.palletteFragment_linearLayout_changeColoursLayoutBackground);
        redSeekBarHeader=view.findViewById(R.id.palletteFragment_seekBar_redSeekBarHeader);
        blueSeekBarHeader=view.findViewById(R.id.palletteFragment_seekBar_blueSeekBarHeader);
        greenSeekBarHeader=view.findViewById(R.id.palletteFragment_seekBar_greenSeekBarHeader);
        redSeekBarTitle=view.findViewById(R.id.palletteFragment_seekBar_redSeekBarTitle);
        blueSeekBarTitle=view.findViewById(R.id.palletteFragment_seekBar_blueSeekBarTitle);
        greenSeekBarTitle=view.findViewById(R.id.palletteFragment_seekBar_greenSeekBarTitle);
        redSeekBarBorder=view.findViewById(R.id.palletteFragment_seekBar_redSeekBarBorder);
        blueSeekBarBorder=view.findViewById(R.id.palletteFragment_seekBar_blueSeekBarBorder);
        greenSeekBarBorder=view.findViewById(R.id.palletteFragment_seekBar_greenSeekBarBorder);
        redSeekBarSubtract=view.findViewById(R.id.palletteFragment_seekBar_redSeekBarSubtract);
        blueSeekBarSubtract=view.findViewById(R.id.palletteFragment_seekBar_blueSeekBarSubtract);
        greenSeekBarSubtract=view.findViewById(R.id.palletteFragment_seekBar_greenSeekBarSubtract);
        redSeekBarBackground=view.findViewById(R.id.palletteFragment_seekBar_redSeekBarBackground);
        blueSeekBarBackground=view.findViewById(R.id.palletteFragment_seekBar_blueSeekBarBackground);
        greenSeekBarBackground=view.findViewById(R.id.palletteFragment_seekBar_greenSeekBarBackground);
        redProgressTitle=view.findViewById(R.id.palletteFragment_textView_redProgressTextViewTitle);
        greenProgressTitle=view.findViewById(R.id.palletteFragment_textView_greenProgressTextViewTitle);
        blueProgressTitle=view.findViewById(R.id.palletteFragment_textView_blueProgressTextViewTitle);
        redProgressHeader=view.findViewById(R.id.palletteFragment_textView_redProgressTextViewHeader);
        greenProgressHeader=view.findViewById(R.id.palletteFragment_textView_greenProgressTextViewHeader);
        blueProgressHeader=view.findViewById(R.id.palletteFragment_textView_blueProgressTextViewHeader);
        redProgressBorder=view.findViewById(R.id.palletteFragment_textView_redProgressTextViewBorder);
        greenProgressBorder=view.findViewById(R.id.palletteFragment_textView_greenProgressTextViewBorder);
        blueProgressBorder=view.findViewById(R.id.palletteFragment_textView_blueProgressTextViewBorder);
        redProgressSubtract=view.findViewById(R.id.palletteFragment_textView_redProgressTextViewSubtract);
        greenProgressSubtract=view.findViewById(R.id.palletteFragment_textView_greenProgressTextViewSubtract);
        blueProgressSubtract=view.findViewById(R.id.palletteFragment_textView_blueProgressTextViewSubtract);
        redProgressBackground=view.findViewById(R.id.palletteFragment_textView_redProgressTextViewBackground);
        greenProgressBackground=view.findViewById(R.id.palletteFragment_textView_greenProgressTextViewBackground);
        blueProgressBackground=view.findViewById(R.id.palletteFragment_textView_blueProgressTextViewBackground);
        decreaseRedHeader=view.findViewById(R.id.palletteFragment_button_reduceRedButtonHeader);
        increaseRedHeader=view.findViewById(R.id.palletteFragment_button_increaseRedButtonHeader);
        decreaseGreenHeader=view.findViewById(R.id.palletteFragment_button_reduceGreenButtonHeader);
        increaseGreenHeader=view.findViewById(R.id.palletteFragment_button_increaseGreenButtonHeader);
        decreaseBlueHeader=view.findViewById(R.id.palletteFragment_button_reduceBlueButtonHeader);
        increaseBlueHeader=view.findViewById(R.id.palletteFragment_button_increaseBlueButtonHeader);
        decreaseRedTitle=view.findViewById(R.id.palletteFragment_button_reduceRedButtonTitle);
        increaseRedTitle=view.findViewById(R.id.palletteFragment_button_increaseRedButtonTitle);
        decreaseGreenTitle=view.findViewById(R.id.palletteFragment_button_reduceGreenButtonTitle);
        increaseGreenTitle=view.findViewById(R.id.palletteFragment_button_increaseGreenButtonTitle);
        decreaseBlueTitle=view.findViewById(R.id.palletteFragment_button_reduceBlueButtonTitle);
        increaseBlueTitle=view.findViewById(R.id.palletteFragment_button_increaseBlueButtonTitle);
        decreaseRedBorder=view.findViewById(R.id.palletteFragment_button_reduceRedButtonBorder);
        increaseRedBorder=view.findViewById(R.id.palletteFragment_button_increaseRedButtonBorder);
        decreaseGreenBorder=view.findViewById(R.id.palletteFragment_button_reduceGreenButtonBorder);
        increaseGreenBorder=view.findViewById(R.id.palletteFragment_button_increaseGreenButtonBorder);
        decreaseBlueBorder=view.findViewById(R.id.palletteFragment_button_reduceBlueButtonBorder);
        increaseBlueBorder=view.findViewById(R.id.palletteFragment_button_increaseBlueButtonBorder);
        decreaseRedSubtract=view.findViewById(R.id.palletteFragment_button_reduceRedButtonSubtract);
        increaseRedSubtract=view.findViewById(R.id.palletteFragment_button_increaseRedButtonSubtract);
        decreaseGreenSubtract=view.findViewById(R.id.palletteFragment_button_reduceGreenButtonSubtract);
        increaseGreenSubtract=view.findViewById(R.id.palletteFragment_button_increaseGreenButtonSubtract);
        decreaseBlueSubtract=view.findViewById(R.id.palletteFragment_button_reduceBlueButtonSubtract);
        increaseBlueSubtract=view.findViewById(R.id.palletteFragment_button_increaseBlueButtonSubtract);
        decreaseRedBackground=view.findViewById(R.id.palletteFragment_button_reduceRedButtonBackground);
        increaseRedBackground=view.findViewById(R.id.palletteFragment_button_increaseRedButtonBackground);
        decreaseGreenBackground=view.findViewById(R.id.palletteFragment_button_reduceGreenButtonBackground);
        increaseGreenBackground=view.findViewById(R.id.palletteFragment_button_increaseGreenButtonBackground);
        decreaseBlueBackground=view.findViewById(R.id.palletteFragment_button_reduceBlueButtonBackground);
        increaseBlueBackground=view.findViewById(R.id.palletteFragment_button_increaseBlueButtonBackground);
        expandHeader=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewHeaderExpand);
        expandTitle=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewTitleExpand);
        expandBorder=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewBorderExpand);
        expandSubtract=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewSubtractExpand);
        expandBackground=view.findViewById(R.id.palletteFragment_textView_changeColoursTextViewBackgroundExpand);
        presetName=view.findViewById(R.id.palletteFragment_textView_presetName);
        presetLeft=view.findViewById(R.id.palletteFragment_button_presetLeft);
        presetRight=view.findViewById(R.id.palletteFragment_button_presetRight);
    }

    private void setListeners(){
        decreaseRedHeader.setOnClickListener(this);
        increaseRedHeader.setOnClickListener(this);
        decreaseGreenHeader.setOnClickListener(this);
        increaseGreenHeader.setOnClickListener(this);
        decreaseBlueHeader.setOnClickListener(this);
        increaseBlueHeader.setOnClickListener(this);
        decreaseRedTitle.setOnClickListener(this);
        increaseRedTitle.setOnClickListener(this);
        decreaseGreenTitle.setOnClickListener(this);
        increaseGreenTitle.setOnClickListener(this);
        decreaseBlueTitle.setOnClickListener(this);
        increaseBlueTitle.setOnClickListener(this);
        decreaseRedBorder.setOnClickListener(this);
        increaseRedBorder.setOnClickListener(this);
        decreaseGreenBorder.setOnClickListener(this);
        increaseGreenBorder.setOnClickListener(this);
        decreaseBlueBorder.setOnClickListener(this);
        increaseBlueBorder.setOnClickListener(this);
        decreaseRedSubtract.setOnClickListener(this);
        increaseRedSubtract.setOnClickListener(this);
        decreaseGreenSubtract.setOnClickListener(this);
        increaseGreenSubtract.setOnClickListener(this);
        decreaseBlueSubtract.setOnClickListener(this);
        increaseBlueSubtract.setOnClickListener(this);
        decreaseRedBackground.setOnClickListener(this);
        increaseRedBackground.setOnClickListener(this);
        decreaseGreenBackground.setOnClickListener(this);
        increaseGreenBackground.setOnClickListener(this);
        decreaseBlueBackground.setOnClickListener(this);
        increaseBlueBackground.setOnClickListener(this);
        headerColourTextView.setOnClickListener(this);
        titleColourTextView.setOnClickListener(this);
        borderColourTextView.setOnClickListener(this);
        subtractColourTextView.setOnClickListener(this);
        backgroundColourTextView.setOnClickListener(this);
        expandHeader.setOnClickListener(this);
        expandTitle.setOnClickListener(this);
        expandBorder.setOnClickListener(this);
        expandSubtract.setOnClickListener(this);
        expandBackground.setOnClickListener(this);
        presetRight.setOnClickListener(this);
        presetLeft.setOnClickListener(this);
        presetName.setOnClickListener(this);
        setSeekBarChangeListener();
    }

    private void setSeekBarChangeListener(){
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedSeekBar(seekBar,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        redSeekBarHeader.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBarHeader.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBarHeader.setOnSeekBarChangeListener(seekBarChangeListener);
        redSeekBarTitle.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBarTitle.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBarTitle.setOnSeekBarChangeListener(seekBarChangeListener);
        redSeekBarBorder.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBarBorder.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBarBorder.setOnSeekBarChangeListener(seekBarChangeListener);
        redSeekBarSubtract.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBarSubtract.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBarSubtract.setOnSeekBarChangeListener(seekBarChangeListener);
        redSeekBarBackground.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBarBackground.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBarBackground.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void increaseBluePressedHeader(){
        blueHeader += 1;
        if (blueHeader > 255){
            blueHeader = 0;
        }
        blueSeekBarHeader.setProgress(blueHeader);
        updateHeaderBlue();
    }
    private void decreaseBluePressedHeader(){
        blueHeader -= 1;
        if (blueHeader < 0){
            blueHeader = 255;
        }
        blueSeekBarHeader.setProgress(blueHeader);
        updateHeaderBlue();
    }
    private void increaseRedPressedHeader(){
        redHeader += 1;
        if (redHeader > 255){
            redHeader = 0;
        }
        redSeekBarHeader.setProgress(redHeader);
        updateHeaderRed();
    }
    private void decreaseRedPressedHeader(){
        redHeader -= 1;
        if (redHeader < 0){
            redHeader = 255;
        }
        redSeekBarHeader.setProgress(redHeader);
        updateHeaderRed();
    }
    private void increaseGreenPressedHeader(){
        greenHeader += 1;
        if (greenHeader > 255){
            greenHeader = 0;
        }
        greenSeekBarHeader.setProgress(greenHeader);
        updateHeaderGreen();
    }
    private void decreaseGreenPressedHeader(){
        greenHeader -= 1;
        if (greenHeader < 0){
            greenHeader = 255;
        }
        greenSeekBarHeader.setProgress(greenHeader);
        updateHeaderGreen();
    }


    private void increaseBluePressedTitle(){
        blueTitle += 1;
        if (blueTitle > 255){
            blueTitle = 0;
        }
        blueSeekBarTitle.setProgress(blueTitle);
        updateTitleBlue();
    }
    private void decreaseBluePressedTitle(){
        blueTitle -= 1;
        if (blueTitle < 0){
            blueTitle = 255;
        }
        blueSeekBarTitle.setProgress(blueTitle);
        updateTitleBlue();
    }
    private void increaseRedPressedTitle(){
        redTitle += 1;
        if (redTitle > 255){
            redTitle = 0;
        }
        redSeekBarTitle.setProgress(redTitle);
        updateTitleRed();
    }
    private void decreaseRedPressedTitle(){
        redTitle -= 1;
        if (redTitle < 0){
            redTitle = 255;
        }
        redSeekBarTitle.setProgress(redTitle);
        updateTitleRed();
    }
    private void increaseGreenPressedTitle(){
        greenTitle += 1;
        if (greenTitle > 255){
            greenTitle = 0;
        }
        greenSeekBarTitle.setProgress(greenTitle);
        updateTitleGreen();
    }
    private void decreaseGreenPressedTitle(){
        greenTitle -= 1;
        if (greenTitle < 0){
            greenTitle = 255;
        }
        greenSeekBarTitle.setProgress(greenTitle);
        updateTitleGreen();
    }

    private void increaseBluePressedBorder(){
        blueBorder += 1;
        if (blueBorder > 255){
            blueBorder = 0;
        }
        blueSeekBarBorder.setProgress(blueBorder);
        updateBorderBlue();
    }
    private void decreaseBluePressedBorder(){
        blueBorder -= 1;
        if (blueBorder < 0){
            blueBorder = 255;
        }
        blueSeekBarBorder.setProgress(blueBorder);
        updateBorderBlue();
    }
    private void increaseRedPressedBorder(){
        redBorder += 1;
        if (redBorder > 255){
            redBorder = 0;
        }
        redSeekBarBorder.setProgress(redBorder);
        updateBorderRed();
    }
    private void decreaseRedPressedBorder(){
        redBorder -= 1;
        if (redBorder < 0){
            redBorder = 255;
        }
        redSeekBarBorder.setProgress(redBorder);
        updateBorderRed();
    }
    private void increaseGreenPressedBorder(){
        greenBorder += 1;
        if (greenBorder > 255){
            greenBorder = 0;
        }
        greenSeekBarBorder.setProgress(greenBorder);
        updateBorderGreen();
    }
    private void decreaseGreenPressedBorder(){
        greenBorder -= 1;
        if (greenBorder < 0){
            greenBorder = 255;
        }
        greenSeekBarBorder.setProgress(greenBorder);
        updateBorderGreen();
    }

    private void increaseBluePressedSubtract(){
        blueSubtract += 1;
        if (blueSubtract > 255){
            blueSubtract = 0;
        }
        blueSeekBarSubtract.setProgress(blueSubtract);
        updateSubtractBlue();
    }
    private void decreaseBluePressedSubtract(){
        blueSubtract -= 1;
        if (blueSubtract < 0){
            blueSubtract = 255;
        }
        blueSeekBarSubtract.setProgress(blueSubtract);
        updateSubtractBlue();
    }
    private void increaseRedPressedSubtract(){
        redSubtract += 1;
        if (redSubtract > 255){
            redSubtract = 0;
        }
        redSeekBarSubtract.setProgress(redSubtract);
        updateSubtractRed();
    }
    private void decreaseRedPressedSubtract(){
        redSubtract -= 1;
        if (redSubtract < 0){
            redSubtract = 255;
        }
        redSeekBarSubtract.setProgress(redSubtract);
        updateSubtractRed();
    }
    private void increaseGreenPressedSubtract(){
        greenSubtract += 1;
        if (greenSubtract > 255){
            greenSubtract = 0;
        }
        greenSeekBarSubtract.setProgress(greenSubtract);
        updateSubtractGreen();
    }
    private void decreaseGreenPressedSubtract(){
        greenSubtract -= 1;
        if (greenSubtract < 0){
            greenSubtract = 255;
        }
        greenSeekBarSubtract.setProgress(greenSubtract);
        updateSubtractGreen();
    }

    private void increaseBluePressedBackground(){
        blueBackground += 1;
        if (blueBackground > 255){
            blueBackground = 0;
        }
        blueSeekBarBackground.setProgress(blueBackground);
        updateBackgroundBlue();
    }
    private void decreaseBluePressedBackground(){
        blueBackground -= 1;
        if (blueBackground < 0){
            blueBackground = 255;
        }
        blueSeekBarBackground.setProgress(blueBackground);
        updateBackgroundBlue();
    }
    private void increaseRedPressedBackground(){
        redBackground += 1;
        if (redBackground > 255){
            redBackground = 0;
        }
        redSeekBarBackground.setProgress(redBackground);
        updateBackgroundRed();
    }
    private void decreaseRedPressedBackground(){
        redBackground -= 1;
        if (redBackground < 0){
            redBackground = 255;
        }
        redSeekBarBackground.setProgress(redBackground);
        updateBackgroundRed();
    }
    private void increaseGreenPressedBackground(){
        greenBackground += 1;
        if (greenBackground > 255){
            greenBackground = 0;
        }
        greenSeekBarBackground.setProgress(greenBackground);
        updateBackgroundGreen();
    }
    private void decreaseGreenPressedBackground(){
        greenBackground -= 1;
        if (greenBackground < 0){
            greenBackground = 255;
        }
        greenSeekBarBackground.setProgress(greenBackground);
        updateBackgroundGreen();
    }

    private void updateHeaderBlue(){
        blueProgressHeader.setText(String.valueOf(blueHeader));
        updateHeaderColourTemporary();
    }
    private void updateHeaderRed(){
        redProgressHeader.setText(String.valueOf(redHeader));
        updateHeaderColourTemporary();
    }
    private void updateHeaderGreen(){
        greenProgressHeader.setText(String.valueOf(greenHeader));
        updateHeaderColourTemporary();
    }

    private void updateTitleBlue(){
        blueProgressTitle.setText(String.valueOf(blueTitle));
        updateTitleColourTemporary();
    }
    private void updateTitleRed(){
        redProgressTitle.setText(String.valueOf(redTitle));
        updateTitleColourTemporary();
    }
    private void updateTitleGreen(){
        greenProgressTitle.setText(String.valueOf(greenTitle));
        updateTitleColourTemporary();
    }

    private void updateBorderBlue(){
        blueProgressBorder.setText(String.valueOf(blueBorder));
        updateBorderColourTemporary();
    }
    private void updateBorderRed(){
        redProgressBorder.setText(String.valueOf(redBorder));
        updateBorderColourTemporary();
    }
    private void updateBorderGreen(){
        greenProgressBorder.setText(String.valueOf(greenBorder));
        updateBorderColourTemporary();
    }

    private void updateSubtractBlue(){
        blueProgressSubtract.setText(String.valueOf(blueSubtract));
        updateSubtractColourTemporary();
    }
    private void updateSubtractRed(){
        redProgressSubtract.setText(String.valueOf(redSubtract));
        updateSubtractColourTemporary();
    }
    private void updateSubtractGreen(){
        greenProgressSubtract.setText(String.valueOf(greenSubtract));
        updateSubtractColourTemporary();
    }

    private void updateBackgroundBlue(){
        blueProgressBackground.setText(String.valueOf(blueBackground));
        updateBackgroundColourTemporary();
    }
    private void updateBackgroundRed(){
        redProgressBackground.setText(String.valueOf(redBackground));
        updateBackgroundColourTemporary();
    }
    private void updateBackgroundGreen(){
        greenProgressBackground.setText(String.valueOf(greenBackground));
        updateBackgroundColourTemporary();
    }

    private boolean headerOptionsVisible(){
        ViewGroup.LayoutParams params = headerColourOptionsLinearLayout.getLayoutParams();
        return params.height != 0;
    }
    private void showHeaderOptions(){
        ViewGroup.LayoutParams params = headerColourOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        headerColourOptionsLinearLayout.setLayoutParams(params);
        expandHeader.setText("-");
    }
    private void hideHeaderOptions(){
        ViewGroup.LayoutParams params = headerColourOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        headerColourOptionsLinearLayout.setLayoutParams(params);
        expandHeader.setText("+");
    }
    private boolean titleOptionsVisible(){
        ViewGroup.LayoutParams params = titleColourOptionsLinearLayout.getLayoutParams();
        return params.height != 0;
    }
    private void showTitleOptions(){
        ViewGroup.LayoutParams params = titleColourOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        titleColourOptionsLinearLayout.setLayoutParams(params);
        expandTitle.setText("-");
    }
    private void hideTitleOptions(){
        ViewGroup.LayoutParams params = titleColourOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        titleColourOptionsLinearLayout.setLayoutParams(params);
        expandTitle.setText("+");
    }
    private boolean borderOptionsVisible(){
        ViewGroup.LayoutParams params = borderColourOptionsLinearLayout.getLayoutParams();
        return params.height != 0;
    }
    private void showBorderOptions(){
        ViewGroup.LayoutParams params = borderColourOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        borderColourOptionsLinearLayout.setLayoutParams(params);
        expandBorder.setText("-");
    }
    private void hideBorderOptions(){
        ViewGroup.LayoutParams params = borderColourOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        borderColourOptionsLinearLayout.setLayoutParams(params);
        expandBorder.setText("+");
    }
    private boolean subtractOptionsVisible(){
        ViewGroup.LayoutParams params = subtractColourOptionsLinearLayout.getLayoutParams();
        return params.height != 0;
    }
    private void showSubtractOptions(){
        ViewGroup.LayoutParams params = subtractColourOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        subtractColourOptionsLinearLayout.setLayoutParams(params);
        expandSubtract.setText("-");
    }
    private void hideSubtractOptions(){
        ViewGroup.LayoutParams params = subtractColourOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        subtractColourOptionsLinearLayout.setLayoutParams(params);
        expandSubtract.setText("+");
    }
    private boolean backgroundOptionsVisible(){
        ViewGroup.LayoutParams params = backgroundColourOptionsLinearLayout.getLayoutParams();
        return params.height != 0;
    }
    private void showBackgroundOptions(){
        ViewGroup.LayoutParams params = backgroundColourOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        backgroundColourOptionsLinearLayout.setLayoutParams(params);
        expandBackground.setText("-");
    }
    private void hideBackgroundOptions(){
        ViewGroup.LayoutParams params = backgroundColourOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        backgroundColourOptionsLinearLayout.setLayoutParams(params);
        expandBackground.setText("+");
    }

    @SuppressLint("NewApi")
    private void updateHeaderColourTemporary(){
        Color colour = colourManager.rgbToColour(redHeader,greenHeader,blueHeader);
        workout_activity activity = (workout_activity) getActivity();
        assert activity != null;
        activity.changeHeaderColour(colour);
    }
    @SuppressLint("NewApi")
    private void updateTitleColourTemporary(){
        Color colour = colourManager.rgbToColour(redTitle,greenTitle,blueTitle);
        workout_activity activity = (workout_activity) getActivity();
        assert activity != null;
        activity.changeTitleColour(colour);
    }
    @SuppressLint("NewApi")
    private void updateBorderColourTemporary(){
        Color colour = colourManager.rgbToColour(redBorder,greenBorder,blueBorder);
        workout_activity activity = (workout_activity) getActivity();
        assert activity != null;
        activity.changeBorderColour(colour);
    }
    @SuppressLint("NewApi")
    private void updateSubtractColourTemporary(){
        Color colour = colourManager.rgbToColour(redSubtract,greenSubtract,blueSubtract);
        workout_activity activity = (workout_activity) getActivity();
        assert activity != null;
        activity.changeSubtractColour(colour);
    }
    @SuppressLint("NewApi")
    private void updateBackgroundColourTemporary(){
        Color colour = colourManager.rgbToColour(redBackground,greenBackground,blueBackground);
        workout_activity activity = (workout_activity) getActivity();
        assert activity != null;
        activity.changeBackgroundColour(colour);
    }


    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.palletteFragment_textView_changeColoursTextViewHeader:{
                if (headerOptionsVisible()){
                    hideHeaderOptions();
                } else {
                    showHeaderOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewTitle:{
                if (titleOptionsVisible()){
                    hideTitleOptions();
                } else {
                    showTitleOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewBorder:{
                if (borderOptionsVisible()){
                    hideBorderOptions();
                } else {
                    showBorderOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewSubtract:{
                if (subtractOptionsVisible()){
                    hideSubtractOptions();
                } else {
                    showSubtractOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewBackground:{
                if (backgroundOptionsVisible()){
                    hideBackgroundOptions();
                } else {
                    showBackgroundOptions();
                }
                break;
            }
            case R.id.palletteFragment_button_increaseBlueButtonHeader:{
                increaseBluePressedHeader();
                break;
            }
            case R.id.palletteFragment_button_increaseRedButtonHeader:{
                increaseRedPressedHeader();
                break;
            }
            case R.id.palletteFragment_button_increaseGreenButtonHeader:{
                increaseGreenPressedHeader();
                break;
            }
            case R.id.palletteFragment_button_reduceBlueButtonHeader:{
                decreaseBluePressedHeader();
                break;
            }
            case R.id.palletteFragment_button_reduceGreenButtonHeader:{
                decreaseGreenPressedHeader();
                break;
            }
            case R.id.palletteFragment_button_reduceRedButtonHeader:{
                decreaseRedPressedHeader();
                break;
            }
            case R.id.palletteFragment_button_increaseBlueButtonTitle:{
                increaseBluePressedTitle();
                break;
            }
            case R.id.palletteFragment_button_increaseRedButtonTitle:{
                increaseRedPressedTitle();
                break;
            }
            case R.id.palletteFragment_button_increaseGreenButtonTitle:{
                increaseGreenPressedTitle();
                break;
            }
            case R.id.palletteFragment_button_reduceBlueButtonTitle:{
                decreaseBluePressedTitle();
                break;
            }
            case R.id.palletteFragment_button_reduceGreenButtonTitle:{
                decreaseGreenPressedTitle();
                break;
            }
            case R.id.palletteFragment_button_reduceRedButtonTitle:{
                decreaseRedPressedTitle();
                break;
            }
            case R.id.palletteFragment_button_increaseBlueButtonBorder:{
                increaseBluePressedBorder();
                break;
            }
            case R.id.palletteFragment_button_increaseRedButtonBorder:{
                increaseRedPressedBorder();
                break;
            }
            case R.id.palletteFragment_button_increaseGreenButtonBorder:{
                increaseGreenPressedBorder();
                break;
            }
            case R.id.palletteFragment_button_reduceBlueButtonBorder:{
                decreaseBluePressedBorder();
                break;
            }
            case R.id.palletteFragment_button_reduceGreenButtonBorder:{
                decreaseGreenPressedBorder();
                break;
            }
            case R.id.palletteFragment_button_reduceRedButtonBorder:{
                decreaseRedPressedBorder();
                break;
            }
            case R.id.palletteFragment_button_increaseBlueButtonSubtract:{
                increaseBluePressedSubtract();
                break;
            }
            case R.id.palletteFragment_button_increaseRedButtonSubtract:{
                increaseRedPressedSubtract();
                break;
            }
            case R.id.palletteFragment_button_increaseGreenButtonSubtract:{
                increaseGreenPressedSubtract();
                break;
            }
            case R.id.palletteFragment_button_reduceBlueButtonSubtract:{
                decreaseBluePressedSubtract();
                break;
            }
            case R.id.palletteFragment_button_reduceGreenButtonSubtract:{
                decreaseGreenPressedSubtract();
                break;
            }
            case R.id.palletteFragment_button_reduceRedButtonSubtract:{
                decreaseRedPressedSubtract();
                break;
            }
            case R.id.palletteFragment_button_increaseBlueButtonBackground:{
                increaseBluePressedBackground();
                break;
            }
            case R.id.palletteFragment_button_increaseRedButtonBackground:{
                increaseRedPressedBackground();
                break;
            }
            case R.id.palletteFragment_button_increaseGreenButtonBackground:{
                increaseGreenPressedBackground();
                break;
            }
            case R.id.palletteFragment_button_reduceBlueButtonBackground:{
                decreaseBluePressedBackground();
                break;
            }
            case R.id.palletteFragment_button_reduceGreenButtonBackground:{
                decreaseGreenPressedBackground();
                break;
            }
            case R.id.palletteFragment_button_reduceRedButtonBackground:{
                decreaseRedPressedBackground();
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewHeaderExpand:{
                if (headerOptionsVisible()){
                    hideHeaderOptions();
                    Log.d(TAG, "onClick: ");
                } else {
                    showHeaderOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewTitleExpand:{
                if (titleOptionsVisible()){
                    hideTitleOptions();
                } else {
                    showTitleOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewBorderExpand:{
                if (borderOptionsVisible()){
                    hideBorderOptions();
                } else {
                    showBorderOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewSubtractExpand:{
                if (subtractOptionsVisible()){
                    hideSubtractOptions();
                } else {
                    showSubtractOptions();
                }
                break;
            }
            case R.id.palletteFragment_textView_changeColoursTextViewBackgroundExpand:{
                if (backgroundOptionsVisible()){
                    hideBackgroundOptions();
                } else {
                    showBackgroundOptions();
                }
                break;
            }
            case R.id.palletteFragment_button_presetLeft:{
                presetSetScrollLeft();
                break;
            }
            case R.id.palletteFragment_button_presetRight:{
                presetSetScrollRight();
                break;
            }
        }
    }

    private void presetSetScrollRight() {
    }

    private void presetSetScrollLeft() {

    }

    @SuppressLint("NonConstantResourceId")
    private void progressChangedSeekBar(SeekBar seekBar, int progress){
        switch(seekBar.getId()) {
            case R.id.palletteFragment_seekBar_redSeekBarHeader: {
                redHeader = progress;
                updateHeaderRed();
                break;
            }
            case R.id.palletteFragment_seekBar_greenSeekBarHeader: {
                greenHeader = progress;
                updateHeaderGreen();
                break;
            }
            case R.id.palletteFragment_seekBar_blueSeekBarHeader: {
                blueHeader = progress;
                updateHeaderBlue();
                break;
            }
            case R.id.palletteFragment_seekBar_redSeekBarTitle: {
                redTitle = progress;
                updateTitleRed();
                break;
            }
            case R.id.palletteFragment_seekBar_greenSeekBarTitle: {
                greenTitle = progress;
                updateTitleGreen();
                break;
            }
            case R.id.palletteFragment_seekBar_blueSeekBarTitle: {
                blueTitle = progress;
                updateTitleBlue();
                break;
            }
            case R.id.palletteFragment_seekBar_redSeekBarBorder: {
                redBorder = progress;
                updateBorderRed();
                break;
            }
            case R.id.palletteFragment_seekBar_greenSeekBarBorder: {
                greenBorder = progress;
                updateBorderGreen();
                break;
            }
            case R.id.palletteFragment_seekBar_blueSeekBarBorder: {
                blueBorder = progress;
                updateBorderBlue();
                break;
            }
            case R.id.palletteFragment_seekBar_redSeekBarSubtract: {
                redSubtract = progress;
                updateSubtractRed();
                break;
            }
            case R.id.palletteFragment_seekBar_greenSeekBarSubtract: {
                greenSubtract = progress;
                updateSubtractGreen();
                break;
            }
            case R.id.palletteFragment_seekBar_blueSeekBarSubtract: {
                blueSubtract = progress;
                updateSubtractBlue();
                break;
            }
            case R.id.palletteFragment_seekBar_redSeekBarBackground: {
                redBackground = progress;
                updateBackgroundRed();
                break;
            }
            case R.id.palletteFragment_seekBar_greenSeekBarBackground: {
                greenBackground = progress;
                updateBackgroundGreen();
                break;
            }
            case R.id.palletteFragment_seekBar_blueSeekBarBackground: {
                blueBackground = progress;
                updateBackgroundBlue();
                break;
            }
        }
    }
}