package com.example.asktheuniverse;

import java.io.IOException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class MainActivity extends Activity {

	private AuthorizationCheckTask mAuthTask;
	private Boolean authed = false;
	private String mEmailAccount = null;
	private Application app = null;
	ProgressDialog progress = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		app = (Application) this.getApplicationContext();
		progress = new ProgressDialog(this);
		progress.setCancelable(false);
		progress.setTitle("Signing in");
		progress.setMessage("Please wait while signing in..");
		progress.show();
		
		AccountManager am = AccountManager.get(this);
	    Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
		new AuthorizationCheckTask().execute(accounts[0].name);
	}
	
	public void startListActivity(View v){
		if (authed == false){
			Toast t = Toast.makeText(this, "You are not authenticated, please try later", Toast.LENGTH_LONG);
    		t.show();
			return;
		}
		
		Intent i = new Intent(this, com.example.asktheuniverse.ListQuestionsActivity.class);
		startActivity(i);
	}
	
	public void startAskQuestionActivity(View v){
		if (authed == false){
			Toast t = Toast.makeText(this, "You are not authenticated, please try later", Toast.LENGTH_LONG);
    		t.show();
			return;
		}
		
		Intent i = new Intent(this, com.example.asktheuniverse.AskQuestionActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class AuthorizationCheckTask extends AsyncTask<String, Integer, Boolean> {
		  @Override
		  protected Boolean doInBackground(String... emailAccounts) {

		    String emailAccount = emailAccounts[0];
		    // Ensure only one task is running at a time.
		    mAuthTask = this;
		    
		    try {
		      // If the application has the appropriate access then a token will be retrieved, otherwise
		      // an error will be thrown.
		    	
		      app.credential = GoogleAccountCredential.usingAudience(
		    		  MainActivity.this, AppConstants.AUDIENCE);
		      app.credential.setSelectedAccountName(emailAccount);

		      String accessToken = app.credential.getToken();
		      mEmailAccount = emailAccount;
		      
		      // Success.
		      return true;
		    } catch (GoogleAuthException unrecoverableException) {
		    	unrecoverableException.printStackTrace();
		        return false;
		    } catch (IOException ioException) {
		    	ioException.printStackTrace();
		        return false;
		    }
		  }
		  
		  @Override
		  protected void onProgressUpdate(Integer... stringIds) {
		  }

		  @Override
		  protected void onPreExecute() {
		    mAuthTask = this;
		  }

		  @Override
		  protected void onPostExecute(Boolean success) {
		    if (success) {
		    	authed = true;
		    	Toast t = Toast.makeText(MainActivity.this, "Signed in.", Toast.LENGTH_LONG);
	    		t.show();
		    } else {
		    	authed = false;
		    	Toast t = Toast.makeText(MainActivity.this, "Failed to sign in, try again later", Toast.LENGTH_LONG);
	    		t.show();
		    }
		    progress.cancel();
		    mAuthTask = null;
		  }

		  @Override
		  protected void onCancelled() {
			  progress.cancel();
			  mAuthTask = null;
		  }
		}


	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    if (mAuthTask!=null) {
	      mAuthTask.cancel(true);
	      mAuthTask = null;
	    }
	}
}

