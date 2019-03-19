package com.initforjesus.datenightquestions.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.initforjesus.datenightquestions.R;
import com.initforjesus.datenightquestions.persistence.Question;
import com.initforjesus.datenightquestions.persistence.Source;

import java.util.GregorianCalendar;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //turn off back arrow at top of screen
        setContentView(R.layout.activity_question);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                Question question = questionViewModel.pickRandomQuestion();
                displayQuestion(question);

            }

        });
    }

    private void displayQuestion(Question question) {
        TextView questionText = findViewById(R.id.QuestionText);
        questionText.setText(question.getQuestion());
        questionText.setMovementMethod(new ScrollingMovementMethod());
        TextView categoryText = findViewById(R.id.CategoryText);
        categoryText.setText(question.getCategory());

        questionViewModel.getSource(question.getSourceID()).observe(this, new Observer<Source>() {

            @Override
            public void onChanged(Source source){
                TextView sourceText = findViewById(R.id.SourceText);

                if (source.getSourceURL() == null) {
                    sourceText.setText(source.getSourceName());
                }
                else {

                    sourceText.setText(Html.fromHtml("<a href=\"" + source.getSourceURL()+"\">"+source.getSourceName()+"</a>"));
                    sourceText.setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        });

        //remove on change
        questionViewModel.getAllQuestions().removeObservers(this);
    }

    public void questionAnswered(View view){
        Question question = questionViewModel.getSelectedQuestion();
        question.setAnswered(new GregorianCalendar());
        question.setTimesSkipped(0);
        questionViewModel.update(question);
        NavUtils.navigateUpFromSameTask(this);
    }

    public void skipQuestion(View view) {
        Question question = questionViewModel.getSelectedQuestion();
        question.setTimesSkipped(question.getTimesSkipped() + 1);
        questionViewModel.update(question);
        //TODO add skip funtionality
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Question question = questionViewModel.getSelectedQuestion();
        displayQuestion(question);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click back again to exit without selecting Answered or Ingnore/Skip for this question", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

//        System.out.println("******* BACK PRESSED *******");
//        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Press back again to exit without selecting Answered or Ingnore/Skip for this question", 4);
//        snackbar.show();
    }
}
