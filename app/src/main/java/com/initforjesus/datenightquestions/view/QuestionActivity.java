package com.initforjesus.datenightquestions.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.initforjesus.datenightquestions.R;
import com.initforjesus.datenightquestions.persistence.Question;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                displayRandomQuestion();
            }
        });
    }

    private void displayRandomQuestion() {
        Question question = questionViewModel.pickRandomQuestion();
        TextView textView = findViewById(R.id.textView);
        textView.setText(question.getQuestion());
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Question question = questionViewModel.getSelectedQuestion();
        TextView textView = findViewById(R.id.textView);
        textView.setText(question.getQuestion());
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manage_questions:
                // call manage questions code
                return true;
            case R.id.settings:
                // call settings code
                return true;
            case R.id.about:
                // call about code
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
