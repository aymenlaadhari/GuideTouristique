package com.example.navigation;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.splash);

        //Programmer des runnables pour �tre ex�cut� apr�s certain moment
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Passage vers la deuxieme activit� apres 3 secondes
                Intent i = new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                //fermer l'activit� en cours
                finish();
            }
        }, 2000);
    }

}
