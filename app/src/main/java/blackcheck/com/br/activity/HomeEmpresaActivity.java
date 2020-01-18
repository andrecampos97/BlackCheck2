package blackcheck.com.br.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import blackcheck.com.br.R;

public class HomeEmpresaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empresa);

        getSupportActionBar().hide();


    }
}
