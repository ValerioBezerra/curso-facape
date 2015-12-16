package br.facape.controlefinanceiropessoal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.bd.CategoriaBD;
import br.facape.controlefinanceiropessoal.model.Categoria;

public class CadastroCategoriaActivity extends AppCompatActivity {
    private EditText edtDescricao;
    private RadioGroup rgTipo;

    private Categoria categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_categoria);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        rgTipo       = (RadioGroup) findViewById(R.id.rgTipo);

        categoria = (Categoria) getIntent().getSerializableExtra("categoria");

        if (categoria.getId() > 0) {
            edtDescricao.setText(categoria.getDescricao());

            if (categoria.getTipo().equals("R"))
                rgTipo.check(R.id.rbReceita);
            else
                rgTipo.check(R.id.rbDespesa);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_categoria, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        if (item.getItemId() == R.id.menuSalvar)
            salvar();

        return super.onOptionsItemSelected(item);
    }

    private void salvar() {
        if (testarCampos()) {
            categoria.setDescricao(edtDescricao.getText().toString());

            if (rgTipo.getCheckedRadioButtonId() == R.id.rbReceita)
                categoria.setTipo("R");
            else
                categoria.setTipo("D");

            CategoriaBD categoriaBD = new CategoriaBD(CadastroCategoriaActivity.this);

            if (categoria.getId() == 0)
                categoriaBD.inserir(categoria);
            else
                categoriaBD.editar(categoria);

            finish();
        }
    }

    private boolean testarCampos() {
        edtDescricao.setError(null);

        if (edtDescricao.getText().toString().trim().equals("")) {
            edtDescricao.setError("Descrição não preenchida");
            return false;
        }

        return true;
    }
}