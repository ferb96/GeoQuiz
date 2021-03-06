package com.example.ryuko.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String KEY_CHEAT="cheat";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.ryuko.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.ryuko.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    // add a boolean that passes thru savedInstances
    private boolean mHasUserCheated;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        // pass the boolen thru savedInstance
        savedInstanceState.putBoolean(KEY_CHEAT, mHasUserCheated);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHasUserCheated = true;
                showCheatInfo();
            }
        });
        // load the saved boolean
        // One-line simplification is suggested by Android Studio, not by me. I think it's good
        mHasUserCheated = savedInstanceState != null && savedInstanceState.getBoolean(KEY_CHEAT);
        // if the saved boolean turns out to be true, the user has cheated before
        if (mHasUserCheated) {
            showCheatInfo();
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    private void showCheatInfo() {
        // refactored from the old OnClickListener
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
    }
}
