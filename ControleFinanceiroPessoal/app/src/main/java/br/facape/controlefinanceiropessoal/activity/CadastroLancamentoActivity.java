package br.facape.controlefinanceiropessoal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.adapter.CategoriaAdapter;
import br.facape.controlefinanceiropessoal.bd.CategoriaBD;
import br.facape.controlefinanceiropessoal.bd.LancamentoBD;
import br.facape.controlefinanceiropessoal.model.Categoria;
import br.facape.controlefinanceiropessoal.model.Lancamento;


public class CadastroLancamentoActivity extends AppCompatActivity {
    private EditText edtDescricao;
    private EditText edtObservacao;
    private EditText edtValor;
    private EditText edtData;
    private Spinner spnCategoria;

    private Lancamento lancamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lancamento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtDescricao  = (EditText) findViewById(R.id.edtDescricao);
        edtObservacao = (EditText) findViewById(R.id.edtObservacao);
        edtValor      = (EditText) findViewById(R.id.edtValor);
        edtData       = (EditText) findViewById(R.id.edtData);
        spnCategoria  = (Spinner)  findViewById(R.id.spnCategoria);

        CategoriaBD categoriaBD = new CategoriaBD(CadastroLancamentoActivity.this);
        spnCategoria.setAdapter(new CategoriaAdapter(CadastroLancamentoActivity.this, categoriaBD.listarTodas()));


        lancamento = (Lancamento) getIntent().getSerializableExtra("lancamento");

        if (lancamento.getId() > 0){
            edtObservacao.setText(lancamento.getObservacao());
            edtValor.setText(lancamento.getValor().toString());
            edtData.setText(lancamento.getData());

//            Categoria categoria = categoriaBD.listarTodas(lancamento.getIdCategoria());
//            spnCategoria.setSelection(getIndex(spnCategoria, categoria.getId()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_lancamento, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSalvar)
            salvar();

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void salvar(){
        if (testarCampos()) {
            lancamento.setDescricao(edtDescricao.getText().toString());
            lancamento.setObservacao(edtObservacao.getText().toString());
            lancamento.setValor(Double.parseDouble(edtValor.getText().toString()));
            lancamento.setData(edtData.getText().toString());
            Categoria categoria = (Categoria) spnCategoria.getSelectedItem();
            lancamento.setIdCategoria(categoria.getId());

            LancamentoBD repositorioLancamento = new LancamentoBD(CadastroLancamentoActivity.this);
            if (lancamento.getId() == 0)
                repositorioLancamento.inserir(lancamento);
            else
                repositorioLancamento.editar(lancamento);

            finish();
        }
    }

    private boolean testarCampos(){
        edtDescricao.setError(null);
        edtValor.setError(null);
        edtData.setError(null);

        if (edtDescricao.getText().toString().trim().equals("")){
            edtDescricao.setError("Descricao não preenchida");
            return false;
        }

        if (edtValor.getText().toString().trim().equals("")){
            edtValor.setError("Valor não preenchido");
            return false;
        }

        if (edtData.getText().toString().trim().equals("")){
            edtData.setError("Data não preenchida");
            return false;
        }

        return true;
    }

    private int getIndex(Spinner spinner, int id){
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemIdAtPosition(i) == id){
                index = i;
            }
        }
        return index;
    }
}
