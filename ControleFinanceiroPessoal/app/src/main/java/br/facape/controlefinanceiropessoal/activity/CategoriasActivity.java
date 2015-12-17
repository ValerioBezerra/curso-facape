package br.facape.controlefinanceiropessoal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.adapter.CategoriaAdapter;
import br.facape.controlefinanceiropessoal.bd.CategoriaBD;
import br.facape.controlefinanceiropessoal.model.Categoria;
import br.facape.controlefinanceiropessoal.webservice.CategoriaWebService;

public class CategoriasActivity extends AppCompatActivity {
    private Button btnNovo;
    private ListView lvCategorias;

    private List<Categoria> listaCategorias;
    private Handler handlerCategoria;

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
                Categoria categoria = new Categoria();

                Bundle extras = new Bundle();
                extras.putSerializable("categoria", categoria);

                Intent intent = new Intent(CategoriasActivity.this,
                        CadastroCategoriaActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });

        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Categoria categoria = listaCategorias.get(position);

                Bundle extras = new Bundle();
                extras.putSerializable("categoria", categoria);

                Intent intent = new Intent(CategoriasActivity.this,
                        CadastroCategoriaActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent, 0);
            }
        });

        lvCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Categoria categoria = listaCategorias.get(position);
//                CategoriaBD categoriaBD = new CategoriaBD(CategoriasActivity.this);
//                categoriaBD.excluir(categoria);
//                listarCategorias();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            CategoriaWebService categoriaWebService =
                                    new CategoriaWebService();
                            categoriaWebService.excluir(categoria);
                            listarCategoriasWebService();
                        } catch (Exception ex) {
                            Log.i("Erro", ex.toString());
                        }
                    }
                }).start();


                return true;
            }
        });

        handlerCategoria = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                lvCategorias.setAdapter(new CategoriaAdapter(CategoriasActivity.this,
                        listaCategorias));
            }
        };


            //listarCategorias();
        listarCategoriasWebService();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            //listarCategorias();
            listarCategoriasWebService();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void listarCategorias() {
        CategoriaBD categoriaBD = new CategoriaBD(CategoriasActivity.this);
        listaCategorias = categoriaBD.listarTodas();
        lvCategorias.setAdapter(new CategoriaAdapter(CategoriasActivity.this,
                listaCategorias));
    }

    private void listarCategoriasWebService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CategoriaWebService categoriaWebService = new CategoriaWebService();
                try {
                    listaCategorias = categoriaWebService.listarCategorias();
                    handlerCategoria.sendMessage(new Message());
                } catch (Exception e) {
                    Log.e("Erro", e.toString());
                }
            }
        }).start();
    }

}
