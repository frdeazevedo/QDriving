package com.romeraze.qdriving;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener{
    EditText input;
    Button button_clear, button_speak;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText)findViewById(R.id.input);
        button_clear = (Button)findViewById(R.id.button_clear);
        button_speak = (Button)findViewById(R.id.button_speak);
        button_clear.setOnClickListener((View.OnClickListener) this);
        button_speak.setOnClickListener((View.OnClickListener) this);

        tts = new TextToSpeech(this, this);
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
        switch(v.getId()) {
            case R.id.button_clear:
                input.setText("");
                break;
            case R.id.button_speak:
                String text = input.getText().toString();

                if(text.isEmpty()) {

                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
        }
    }
}
