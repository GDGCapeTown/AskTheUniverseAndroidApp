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
import android.widget.Toast;

public class AskQuestionActivity extends Activity {

	private ProgressDialog progress;
	private Application app = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_question);
		
		app = (Application) this.getApplicationContext();
	}
	
	public void submitQuestion(View v){
		progress = new ProgressDialog(this);
		progress.setTitle("Plaese wait");
		progress.setMessage("Submitting your question");
		progress.setCancelable(false);
		
		EditText questionEditText = (EditText) findViewById(R.id.ask_question_textview);
		String question = questionEditText.getText().toString();
		
		AsyncTask<String, Void, Boolean> submitQuestionToService =
	            new AsyncTask<String, Void, Boolean> () {
	                @Override
	                protected Boolean doInBackground(String... question) {
	                    // Retrieve service handle.
	                    QuestionService apiServiceHandle = AppConstants.getApiServiceHandle(app.credential);

	                    try {
	                    	Log.d("DEBUG", "run async api call");
	                    	QuestionService.QuestionServiceOperations getQuestionsCommand = apiServiceHandle.questionService();
	                    	AskTheUniverseAQuestionQuestion result = getQuestionsCommand.askQuestion(question[0]).execute();
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
		submitQuestionToService.execute(question);
	}
	
    private void handleResult(Boolean sent){
    	progress.cancel();
    	if (sent == true){
    		Toast t = Toast.makeText(this, "Submit successful", Toast.LENGTH_LONG);
    		t.show();
    		this.finish();
    	}
    	else{
    		Toast t = Toast.makeText(this, "Submit failed. Try again.", Toast.LENGTH_LONG);
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
