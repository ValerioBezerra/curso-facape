package br.facape.controlefinanceiropessoal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.adapter.LancamentoAdapter;
import br.facape.controlefinanceiropessoal.bd.CategoriaBD;
import br.facape.controlefinanceiropessoal.bd.LancamentoBD;
import br.facape.controlefinanceiropessoal.model.Categoria;
import br.facape.controlefinanceiropessoal.model.Lancamento;


public class LancamentosActivity extends AppCompatActivity {
    private Button btnNovo;
    private ListView lvLancamentos;
    private TextView txtReceita;
    private TextView txtDespesa;
    private List<Lancamento> listaLancamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamentos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnNovo       = (Button) findViewById(R.id.btnNovo);
        lvLancamentos = (ListView) findViewById(R.id.lvLancamentos);
        txtReceita    = (TextView) findViewById(R.id.txtReceita);
        txtDespesa    = (TextView) findViewById(R.id.txtDespesa);

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lancamento lancamento = new Lancamento();
                Bundle extras = new Bundle();
                extras.putSerializable("lancamento", lancamento);
                Intent intent = new Intent(LancamentosActivity.this, CadastroLancamentoActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });

        lvLancamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lancamento lancamento = listaLancamentos.get(position);

                Bundle extras = new Bundle();
                extras.putSerializable("lancamento", lancamento);
                Intent intent = new Intent(LancamentosActivity.this, CadastroLancamentoActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });

        lvLancamentos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LancamentoBD repositorioLancamento = new LancamentoBD(LancamentosActivity.this);
                Lancamento lancamento = listaLancamentos.get(position);
                repositorioLancamento.excluir(lancamento);

                listarLancamentos();
                return true;
            }
        });
        listarLancamentos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0)
            listarLancamentos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private void listarLancamentos(){
        LancamentoBD repositorioLancamento = new LancamentoBD(LancamentosActivity.this);
        listaLancamentos = repositorioLancamento.listarTodas();

        double receita = 0;
        double despesa = 0;
        for (Lancamento lancamento: listaLancamentos) {
            Categoria categoria = new CategoriaBD(LancamentosActivity.this).listar(lancamento.getIdCategoria());
            if (categoria.getTipo().equals("R"))
                receita += lancamento.getValor();
            else
                despesa += lancamento.getValor();
        }

        txtReceita.setText(new DecimalFormat("0.00").format(receita));
        txtDespesa.setText(new DecimalFormat("0.00").format(despesa));

        lvLancamentos.setAdapter(new LancamentoAdapter(LancamentosActivity.this, listaLancamentos));
    }
}