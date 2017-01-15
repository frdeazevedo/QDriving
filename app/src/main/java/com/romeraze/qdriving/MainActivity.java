package com.romeraze.qdriving;

import android.content.res.Configuration;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener{
    Button button_correct, button_wrong, button_repeat;
    TextToSpeech tts;
    Question question;
    List<Question> questionList;
    int currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGUI();
        initVoice();
        initQuestions();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            Locale language = tts.getLanguage();
            int result = tts.setLanguage(language);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This language is not supported");
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    @Override
    public void onClick(View v) {
        if(currentQuestion > questionList.size()) {
            currentQuestion = 0;
        }

        question = questionList.get(currentQuestion);

        switch(v.getId()) {
            case R.id.button_repeat:
                tts.speak(question.getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_correct:
                this.currentQuestion++;
                if(question.answerTheQuestion(true)) {
                    tts.speak("Você acertou!", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    tts.speak("Você errou!", TextToSpeech.QUEUE_FLUSH, null);
                }

                while(tts.isSpeaking()) {
                }

                tts.speak("Próxima questão!", TextToSpeech.QUEUE_FLUSH, null);

                while(tts.isSpeaking()) {
                }

                getNextQuestion();

                tts.speak(question.getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.button_wrong:
                this.currentQuestion++;
                if(question.answerTheQuestion(false)) {
                    tts.speak("Você acertou!", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    tts.speak("Você errou!", TextToSpeech.QUEUE_FLUSH, null);
                }

                while(tts.isSpeaking()) {
                }

                tts.speak("Próxima questão!", TextToSpeech.QUEUE_FLUSH, null);

                while(tts.isSpeaking()) {
                }

                getNextQuestion();

                tts.speak(question.getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
                break;
        }
    }

    private void getNextQuestion() {
        if(currentQuestion >= questionList.size()) {
            currentQuestion = 0;
        }

        question = questionList.get(currentQuestion);
    }

    private void initGUI() {
        button_correct = (Button)findViewById(R.id.button_correct);
        button_wrong = (Button)findViewById(R.id.button_wrong);
        button_repeat = (Button)findViewById(R.id.button_repeat);

        button_correct.setOnClickListener((View.OnClickListener) this);
        button_wrong.setOnClickListener((View.OnClickListener) this);
        button_repeat.setOnClickListener((View.OnClickListener) this);
    }

    private void initVoice() {
        tts = new TextToSpeech(this, this);
    }

    private void initQuestions() {
        currentQuestion = 0;

        questionList = new ArrayList<Question>();
        questionList.add(new Question("O território aduaneiro compreende todo o território nacional", true));
        questionList.add(new Question("Duplicatas descontadas são classificadas no Passivo", true));
        questionList.add(new Question("Servidor público não pode ser demitido em nenhuma hipótese", false));
        questionList.add(new Question("O servidor nomeado tem 15 dias para tomar posse", false));
        questionList.add(new Question("Despesas diferidas têm a mesma natureza de despesas antecipadas", true));
    }
}
