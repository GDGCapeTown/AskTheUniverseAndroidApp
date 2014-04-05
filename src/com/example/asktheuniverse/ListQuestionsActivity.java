package com.example.asktheuniverse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


import com.appspot.asktheuniverseaquestion.questionService.QuestionService;
import com.appspot.asktheuniverseaquestion.questionService.model.AskTheUniverseAQuestionQuestion;
import com.appspot.asktheuniverseaquestion.questionService.model.AskTheUniverseAQuestionQuestionCollection;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.accounts.Account;
import android.accounts.AccountManager;

public class ListQuestionsActivity extends Activity {
	private QuestionsDataAdapter mListAdapter;
	ListView listView = null;
	private Application app = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_questions);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		app = (Application) this.getApplicationContext();
				
		listView = (ListView) findViewById(R.id.questions_list_view);
		mListAdapter = new QuestionsDataAdapter(app);
		listView.setAdapter(mListAdapter);
	}

	
	@Override
	protected void onStart(){
		super.onStart();
		
		Log.d("DEBUG", "onstart of ListQuestionActivity called");
		final AsyncTask<Integer, Void, AskTheUniverseAQuestionQuestionCollection> getAndDisplayQuestions =
	            new AsyncTask<Integer, Void, AskTheUniverseAQuestionQuestionCollection> () {
	                @Override
	                protected AskTheUniverseAQuestionQuestionCollection doInBackground(Integer... integers) {
	                    // Retrieve service handle.
	                    QuestionService apiServiceHandle = AppConstants.getApiServiceHandle(app.credential);

	                    try {
	                    	Log.d("DEBUG", "run async api call");
	                    	QuestionService.QuestionServiceOperations getQuestionsCommand = apiServiceHandle.questionService();
	                    	AskTheUniverseAQuestionQuestionCollection questions = getQuestionsCommand.getAllQuestions().execute();
	                        return questions;
	                    } catch (IOException e) {
	                    	e.printStackTrace();
	                    }
	                    
	                    Log.d("DEBUG", "async returning null");
	                    return null;
	                }

	                @Override
	                protected void onPostExecute(AskTheUniverseAQuestionQuestionCollection questions) {
	                	displayQuestions(questions);
	                }
	            };
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			public void onItemClick(AdapterView parent, View v, int position, long id){
				AskTheUniverseAQuestionQuestion question = mListAdapter.getItem(position);
				Bundle bundle = new Bundle();
				
				Log.d("DEBUG", "questionId from listview callback is: " + question.getId());
				bundle.putLong("questionId", question.getId());
				bundle.putString("questionText", question.getQuestion());
				
				Intent i = new Intent(ListQuestionsActivity.this, com.example.asktheuniverse.AnswerQuestionActivity.class);
				i.putExtras(bundle);
				
				startActivity(i);
			}
		});
		
		getAndDisplayQuestions.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	static class QuestionsDataAdapter extends ArrayAdapter<AskTheUniverseAQuestionQuestion> {
        QuestionsDataAdapter(Application application) {
            super(application.getApplicationContext(), R.layout.list_view_items,
                    application.questions);
        }

        void replaceData(AskTheUniverseAQuestionQuestionCollection questions) {
            clear();
            List<AskTheUniverseAQuestionQuestion> list = questions.getItems();
            
            if (list == null || list.isEmpty()){
            	Toast t = Toast.makeText(this.getContext(), "No questions left, universe is answered.", Toast.LENGTH_LONG);
        		t.show();
            }
            else{
            	for (AskTheUniverseAQuestionQuestion question : list) {
            		//Log.d("DEBUG", question.toString());
            		add(question);
            	}
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AskTheUniverseAQuestionQuestion question = (AskTheUniverseAQuestionQuestion)this.getItem(position);
            
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_items, null);
            
            }
            
            TextView questionText = (TextView) convertView.findViewById(R.id.question_item);
            questionText.setText(question.getQuestion());
            
            return convertView;
        }
    }
	
	private void displayQuestions(AskTheUniverseAQuestionQuestionCollection questions) {
		Log.d("DEBUG","displayQuestions called");
		if (questions == null){
			Toast t = Toast.makeText(this, "Failed to fetch questions, please try again.", Toast.LENGTH_LONG);
    		t.show();
    		this.finish();
		}
		else{
			mListAdapter.replaceData(questions);
		}
	}
}
