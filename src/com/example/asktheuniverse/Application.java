package com.example.asktheuniverse;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.Lists;
import com.appspot.asktheuniverseaquestion.questionService.model.AskTheUniverseAQuestionQuestion;
import java.util.ArrayList;

public class Application extends android.app.Application {
	ArrayList <AskTheUniverseAQuestionQuestion> questions = Lists.newArrayList();
	
	GoogleAccountCredential credential;
}
