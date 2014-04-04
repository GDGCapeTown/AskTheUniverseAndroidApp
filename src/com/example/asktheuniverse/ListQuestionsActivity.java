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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListQuestionsActivity extends Activity {
	private QuestionsDataAdapter mListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_questions);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		ListView listView = (ListView) findViewById(R.id.questions_list_view);
		mListAdapter = new QuestionsDataAdapter((Application) this.getApplicationContext());
		listView.setAdapter(mListAdapter);
		
		Log.d("DEBUG","oncreate.");
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
	
	AsyncTask<Integer, Void, AskTheUniverseAQuestionQuestionCollection> getAndDisplayQuestions =
            new AsyncTask<Integer, Void, AskTheUniverseAQuestionQuestionCollection> () {
                @Override
                protected AskTheUniverseAQuestionQuestionCollection doInBackground(Integer... integers) {
                    // Retrieve service handle.
                    QuestionService apiServiceHandle = AppConstants.getApiServiceHandle();

                    try {
                    	Log.d("DEBUG", "run async api call");
                    	QuestionService.QuestionServiceOperations getQuestionsCommand = apiServiceHandle.questionService();
                    	AskTheUniverseAQuestionQuestionCollection questions = getQuestionsCommand.getAllQuestions().execute();
                        return questions;
                    } catch (IOException e) {
                    	Log.d("DEBUG", e.getStackTrace().toString());
                    }
                    
                    Log.d("DEBUG", "async returning null");
                    return null;
                }

                @Override
                protected void onPostExecute(AskTheUniverseAQuestionQuestionCollection questions) {
                    if (questions!=null) {
                        displayQuestions(questions);
                    } else {
                    }
                }
            };
	
	private void displayQuestions(AskTheUniverseAQuestionQuestionCollection questions) {
		Log.d("DEBUG","displayQuestions called");
	    mListAdapter.replaceData(questions);
	}
}
