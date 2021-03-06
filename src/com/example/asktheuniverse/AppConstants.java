package com.example.asktheuniverse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.appspot.asktheuniverseaquestion.questionService.QuestionService;
import com.appspot.asktheuniverseaquestion.questionService.QuestionServiceRequest;
import com.appspot.asktheuniverseaquestion.questionService.QuestionServiceRequestInitializer;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

public class AppConstants {
	public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();
	public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
	
	public static final String WEB_CLIENT_ID = "394729362556.apps.googleusercontent.com";
	public static final String AUDIENCE = "server:client_id:" + WEB_CLIENT_ID;
	
	public static QuestionService getApiServiceHandle(GoogleAccountCredential credential) {
        // Use a builder to help formulate the API request.
		QuestionService.Builder askTheUniverse = new QuestionService.Builder(AppConstants.HTTP_TRANSPORT,
                AppConstants.JSON_FACTORY, credential);
		askTheUniverse.setRootUrl("http://10.0.2.226:8080/_ah/api/");
		
		//disable gzip when local testing, dev_appserver expects non gzip. works in the cloud though.
        askTheUniverse.setGoogleClientRequestInitializer(new QuestionServiceRequestInitializer(){
                        @Override
                        protected void initializeQuestionServiceRequest(QuestionServiceRequest<?> request) {
                        request.setDisableGZipContent(true);
                        }
                });

        return askTheUniverse.build();
    }
}
