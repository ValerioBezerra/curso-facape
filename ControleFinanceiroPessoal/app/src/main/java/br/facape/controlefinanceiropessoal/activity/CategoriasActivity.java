package br.facape.controlefinanceiropessoal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.adapter.CategoriaAdapter;
import br.facape.controlefinanceiropessoal.model.Categoria;

public class CategoriasActivity extends AppCompatActivity {
    private Button btnNovo;
    private ListView lvCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        btnNovo      = (Button) findViewById(R.id.btnNovo);
        lvCategorias = (ListView) findViewById(R.id.lvCategorias);

        listarCategorias();
    }

    private void listarCategorias() {
        List<Categoria> listaCategorias = new ArrayList<Categoria>();

        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setDescricao("Carro");
        categoria.setTipo("D");
        listaCategorias.add(categoria);

        categoria = new Categoria();
        categoria.setId(2);
        categoria.setDescricao("Alimentação");
        categoria.setTipo("D");
        listaCategorias.add(categoria);

        categoria = new Categoria();
        categoria.setId(3);
        categoria.setDescricao("Remuneração");
        categoria.setTipo("R");
        listaCategorias.add(categoria);

        lvCategorias.setAdapter(new CategoriaAdapter(CategoriasActivity.this,
                                                     listaCategorias));
    }

}
