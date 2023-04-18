package br.ufpe.cin.residencia.banco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 3000; // Duração da splash screen em milissegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Configure a imagem da splash screen
        ImageView splashImage = findViewById(R.id.splash_image);
        // Você pode configurar a imagem da splash screen programaticamente, se necessário
        // splashImage.setImageResource(R.drawable.nova_imagem);

        // Inicialize o temporizador para a splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Após a duração da splash screen, inicie a activity principal
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }

}
