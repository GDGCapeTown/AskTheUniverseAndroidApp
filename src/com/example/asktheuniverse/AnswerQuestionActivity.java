package com.example.asktheuniverse;

import java.io.IOException;

import com.appspot.asktheuniverseaquestion.questionService.QuestionService;
import com.appspot.asktheuniverseaquestion.questionService.QuestionService.QuestionServiceOperations.AskQuestion;
import com.appspot.asktheuniverseaquestion.questionService.model.AskTheUniverseAQuestionQuestion;
import com.appspot.asktheuniverseaquestion.questionService.model.AskTheUniverseAQuestionQuestionCollection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerQuestionActivity extends Activity {

	private ProgressDialog progress;
	private Long questionId = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer_question);
		
		Bundle bundle = getIntent().getExtras();
		TextView questionView = (TextView) findViewById(R.id.answer_question_textview);
		questionView.setText(bundle.getString("questionText"));
		questionId = bundle.getLong("questionId");
	}

	public void answerQuestion(View v){
		progress = new ProgressDialog(this);
		progress.setTitle("Plaese wait");
		progress.setMessage("Submitting your answer");
		progress.setCancelable(false);
		
		EditText answerEditText = (EditText) findViewById(R.id.answer_question_edittext);
		String answer = answerEditText.getText().toString();
		
		AsyncTask<String, Void, Boolean> submitAnswerToService =
	            new AsyncTask<String, Void, Boolean> () {
	                @Override
	                protected Boolean doInBackground(String... answer) {
	                    // Retrieve service handle.
	                    QuestionService apiServiceHandle = AppConstants.getApiServiceHandle();

	                    try {
	                    	Log.d("DEBUG", "run async api call");
	                    	Log.d("DEBUG", "answer for question with id: " + questionId);
	                    	QuestionService.QuestionServiceOperations getQuestionsCommand = apiServiceHandle.questionService();
	                    	AskTheUniverseAQuestionQuestion result = getQuestionsCommand.sendAnswer(questionId, answer[0]).execute();
	                        return true;
	                    } catch (IOException e) {
	                    	e.printStackTrace();
	                    }
	                    
	                    Log.d("DEBUG", "async returning null");
	                    return false;
	                }

	                @Override
	                protected void onPostExecute(Boolean result) {
	                	handleResult(result);
	                }
	            };
	            
		progress.show();
		submitAnswerToService.execute(answer);
	}
	
    private void handleResult(Boolean sent){
    	progress.cancel();
    	if (sent == true){
    		Toast t = Toast.makeText(this, "Answer successful", Toast.LENGTH_LONG);
    		t.show();
    		this.finish();
    	}
    	else{
    		Toast t = Toast.makeText(this, "Answer failed. Try again.", Toast.LENGTH_LONG);
    		t.show();
    	}
    }
            
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
