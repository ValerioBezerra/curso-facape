package br.facape.controlefinanceiropessoal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.facape.controlefinanceiropessoal.R;

public class MainActivity extends AppCompatActivity {
    private Button btnCategorias;
    private Button btnLancamentos;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCategorias  = (Button) findViewById(R.id.btnCategorias);
        btnLancamentos = (Button) findViewById(R.id.btnLancamentos);
        btnSair        = (Button) findViewById(R.id.btnSair);

        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                                           CategoriasActivity.class);
                startActivity(intent);
            }
        });

        btnLancamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
