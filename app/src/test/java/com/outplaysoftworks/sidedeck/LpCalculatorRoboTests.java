package com.outplaysoftworks.sidedeck;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Billy on 7/25/2017.
 */
@RunWith(RobolectricTestRunner.class)

//@Config(manifest = "src/main/AndroidManifest.xml", sdk = 18)
@Config(constants = BuildConfig.class)
public class LpCalculatorRoboTests {
    MainActivity activity;

    @Before
    public void initSettingsForTesting() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.KEYallowNegativeLp), false);
        editor.putString(context.getString(R.string.KEYplayerOneDefaultNameSetting), "Player 1");
        editor.putString(context.getString(R.string.KEYplayerTwoDefaultNameSetting), "Player 2");
        editor.putString(context.getString(R.string.KEYdefaultLpSetting), "8000");
        editor.commit();
        activity = Robolectric.setupActivity(MainActivity.class);
    }


    @After
    public void cleanUpModel(){
        LpCalculatorModel.clearEnteredValue();
        LpCalculatorModel.setPlayer1Name(LpCalculatorModel.getPlayer1Name());
        LpCalculatorModel.setPlayer1Lp(LpCalculatorModel.getPlayer1Lp());
        LpCalculatorModel.setPlayer2Lp(LpCalculatorModel.getPlayer2Lp());
        LpCalculatorModel.setAllowsNegativeLp(false);
        LpCalculatorModel.setLpDefault(8000);
    }

    @Test
    public void onClickButton1_appends1ToEnteredValue() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        TextView tv = (TextView)activity.findViewById(R.id.LpCalculatorTextEnteredValue);
        assertEquals("1", tv.getText().toString());
    }

    @Test
    public void addsToEnteredValueWhenNumberClicked() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton2).performClick();
        activity.findViewById(R.id.LpCalculatorButton3).performClick();
        activity.findViewById(R.id.LpCalculatorButton4).performClick();
        activity.findViewById(R.id.LpCalculatorButton5).performClick();
        activity.findViewById(R.id.LpCalculatorButton6).performClick();
        TextView tv = (TextView)activity.findViewById(R.id.LpCalculatorTextEnteredValue);
        assertEquals("Entered value should display 123456", "123456", tv.getText().toString());
    }

    @Test
    public void doesNotAddToEnteredValueIfAtSixDigits() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton2).performClick();
        activity.findViewById(R.id.LpCalculatorButton3).performClick();
        activity.findViewById(R.id.LpCalculatorButton4).performClick();
        activity.findViewById(R.id.LpCalculatorButton5).performClick();
        activity.findViewById(R.id.LpCalculatorButton6).performClick();
        activity.findViewById(R.id.LpCalculatorButton6).performClick();
        TextView tv = (TextView)activity.findViewById(R.id.LpCalculatorTextEnteredValue);
        assertEquals("Entered value should display 123456", "123456", tv.getText().toString());
    }

    @Test
    public void onClickClearButton_clearEnteredValue() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton2).performClick();
        activity.findViewById(R.id.LpCalculatorButton3).performClick();
        activity.findViewById(R.id.LpCalculatorButton4).performClick();
        activity.findViewById(R.id.LpCalculatorButton5).performClick();
        activity.findViewById(R.id.LpCalculatorButton6).performClick();
        activity.findViewById(R.id.LpCalculatorButtonClear).performClick();
        assertEquals("Entered value should be blank", "", ((TextView) activity.findViewById(R.id.LpCalculatorTextEnteredValue)).getText().toString());
    }

    @Test
    public void onClickEnteredValue_clearEnteredValue(){
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton2).performClick();
        activity.findViewById(R.id.LpCalculatorButton3).performClick();
        activity.findViewById(R.id.LpCalculatorButton4).performClick();
        activity.findViewById(R.id.LpCalculatorButton5).performClick();
        activity.findViewById(R.id.LpCalculatorButton6).performClick();
        activity.findViewById(R.id.LpCalculatorTextEnteredValue).performClick();
        assertEquals("Entered value should be blank", "", ((TextView) activity.findViewById(R.id.LpCalculatorTextEnteredValue)).getText().toString());
    }

    @Test
    public void onClickDoubleZero_appendsDoubleZero() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton00).performClick();
        assertEquals("Entered value should be 100", "100", ((TextView) activity.findViewById(R.id.LpCalculatorTextEnteredValue)).getText().toString());
    }

    @Test
    public void onClickTripleZero_appendsTripleZero() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton000).performClick();
        assertEquals("Entered value should be 1000", "1000", ((TextView) activity.findViewById(R.id.LpCalculatorTextEnteredValue)).getText().toString());
    }

    @Test
    public void onClickUndo_unexecutesLastAddition() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton000).performClick();
        activity.findViewById(R.id.LpCalculatorButtonPlusPlayer1).performClick();
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
        assertEquals("Player 1 LP should be 8000", "8000", ((TextView) activity.findViewById(R.id.LpCalculatorTextPlayer1Lp)).getText().toString());
    }

    @Test
    public void onClickUndo_unexecutesLastSubtraction() {
        activity.findViewById(R.id.LpCalculatorButton1).performClick();
        activity.findViewById(R.id.LpCalculatorButton000).performClick();
        activity.findViewById(R.id.LpCalculatorButtonPlusPlayer1).performClick();
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
        assertEquals("Player 1 LP should be 8000", "8000", ((TextView) activity.findViewById(R.id.LpCalculatorTextPlayer1Lp)).getText().toString());
    }

    @Test
    public void onClickUndo_doesNothingIfNoCommandsInHistory() {
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
    }

    @Test
    public void onClickUndo_unexecutesLastTurnIncrement(){
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
        assertEquals("Turn Should Display Turn\n2", "Turn\n2", ((Button) activity.findViewById(R.id.LpCalculatorButtonTurn)).getText().toString());
    }

    @Test
    public void onClickUndo_unexecutesLastTurnDecrement(){
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        activity.findViewById(R.id.LpCalculatorButtonTurn).performLongClick();
        activity.findViewById(R.id.LpCalculatorButtonUndo).performClick();
        assertEquals("Turn Should Display Turn\n3", "Turn\n3", ((Button) activity.findViewById(R.id.LpCalculatorButtonTurn)).getText().toString());
    }

    @Test
    public void onInit_setsUpTurnDisplay() {
        assertEquals("Turn Should Display Turn\n1", "Turn\n1", ((Button) activity.findViewById(R.id.LpCalculatorButtonTurn)).getText().toString());
    }

    @Test
    public void onClickTurn_incrementsTurnDisplay() {
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        assertEquals("Turn Should Display Turn\n2", "Turn\n2", ((Button) activity.findViewById(R.id.LpCalculatorButtonTurn)).getText().toString());
    }

    @Test
    public void onLongClickTurn_decrementTurnDisplay() {
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        activity.findViewById(R.id.LpCalculatorButtonTurn).performClick();
        activity.findViewById(R.id.LpCalculatorButtonTurn).performLongClick();
        assertEquals("Turn Should Display Turn\n2", "Turn\n2", ((Button) activity.findViewById(R.id.LpCalculatorButtonTurn)).getText().toString());
    }

    @Test
    public void onLongClickTurn_doNothingIfOnTurn1() {
        activity.findViewById(R.id.LpCalculatorButtonTurn).performLongClick();
        assertEquals("Turn Should Display Turn\n1", "Turn\n1", ((Button) activity.findViewById(R.id.LpCalculatorButtonTurn)).getText().toString());
    }

//    @Test
//    public void onClickSubtract_goesToZeroIfWillMakeNegativeAndAllowNegativeLpIsDisabled() {
//        activity.findViewById(R.id.LpCalculatorButton1).performClick();
//        activity.findViewById(R.id.LpCalculatorButton0).performClick();
//        activity.findViewById(R.id.LpCalculatorButton000).performClick();
//        activity.findViewById(R.id.LpCalculatorButtonMinusPlayer1).performClick();
//
//        assertEquals("Should go to 0", "0", ((TextView) activity.findViewById(R.id.LpCalculatorTextPlayer1Lp)).getText().toString());
//    }
//
//    @Test
//    public void onClickSubtract_goesNegativeIfWillMakeNegativeAndAllowNegativeLpIsEnabled() {
//        LpCalculatorModel.setAllowsNegativeLp(false);
//        activity.findViewById(R.id.LpCalculatorButton1).performClick();
//        activity.findViewById(R.id.LpCalculatorButton0).performClick();
//        activity.findViewById(R.id.LpCalculatorButton000).performClick();
//        activity.findViewById(R.id.LpCalculatorButtonMinusPlayer1).performClick();
//        assertEquals("Should go to -2000", "-2000", ((TextView) activity.findViewById(R.id.LpCalculatorTextPlayer1Lp)).getText().toString());
//    }
}