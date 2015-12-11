package br.facape.controlefinanceiropessoal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.adapter.CategoriaAdapter;
import br.facape.controlefinanceiropessoal.bd.CategoriaBD;
import br.facape.controlefinanceiropessoal.model.Categoria;

public class CategoriasActivity extends AppCompatActivity {
    private Button btnNovo;
    private ListView lvCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnNovo      = (Button) findViewById(R.id.btnNovo);
        lvCategorias = (ListView) findViewById(R.id.lvCategorias);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriasActivity.this,
                                           CadastroCategoriaActivity.class);
                startActivity(intent);
            }
        });

        listarCategorias();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void listarCategorias() {
        CategoriaBD categoriaBD = new CategoriaBD(CategoriasActivity.this);
        List<Categoria> listaCategorias = categoriaBD.listarTodas();
        lvCategorias.setAdapter(new CategoriaAdapter(CategoriasActivity.this,
                                                     listaCategorias));
    }

}
