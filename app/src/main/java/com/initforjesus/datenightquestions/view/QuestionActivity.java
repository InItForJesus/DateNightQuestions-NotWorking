package com.initforjesus.datenightquestions.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.initforjesus.datenightquestions.R;
import com.initforjesus.datenightquestions.persistence.Question;
import com.initforjesus.datenightquestions.persistence.Source;

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
    }

    private void displaySourece(Source source){

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Question question = questionViewModel.getSelectedQuestion();
        displayQuestion(question);
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
