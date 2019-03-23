package com.initforjesus.datenightquestions.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.initforjesus.datenightquestions.R;
import com.initforjesus.datenightquestions.persistence.Question;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                showQuestionsAvailable(questions);
            }
        });

    }

    public void generateQuestion(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }

    private void showQuestionsAvailable(List<Question> questionList) {
        TextView textView = findViewById(R.id.questions_Available);
        int questionCount = 0;
        if (questionList != null) {
            questionCount = questionList.size();
        }
        textView.setText("There are " + questionCount + " questions available");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.manage_questions:
//                intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                // call about code
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
