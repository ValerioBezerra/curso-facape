package br.facape.controlefinanceiropessoal.webservice;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.facape.controlefinanceiropessoal.model.Categoria;

/**
 * Created by valerio on 15/12/15.
 */
public class CategoriaWebService {
    private static final String URL     = "http://grupogabinete.com.br/curso_facape/categoria_json/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public List<Categoria> listarCategorias() throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().
                              url(URL + "retornar_categorias").
                              build();
        Response response = okHttpClient.newCall(request).execute();
        String resposta = response.body().string();
        Log.i("Response", resposta);

        List<Categoria> listaCategorias = new ArrayList<Categoria>();

        if (!resposta.equals("")) {
            JSONArray jsonArray = new JSONObject(resposta).
                                  getJSONArray("categorias");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Categoria categoria = new Categoria();
                categoria.setId(jsonObject.getInt("id"));
                categoria.setDescricao(jsonObject.getString("descricao"));
                categoria.setTipo(jsonObject.getString("tipo"));
                listaCategorias.add(categoria);
            }
        }

        return listaCategorias;
    }

    public void inserir(Categoria categoria) throws Exception {
        JSONObject json = new JSONObject();
        json.put("descricao", categoria.getDescricao());
        json.put("tipo", categoria.getTipo());

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON,
                json.toString());
        Request request = new Request.
                              Builder().
                              url(URL + "inserir").
                              post(requestBody).
                              build();
        Response response = okHttpClient.newCall(request).execute();
        Log.i("Resposta", response.body().string());
    }

    public void editar(Categoria categoria) throws Exception {
        JSONObject json = new JSONObject();
        json.put("descricao", categoria.getDescricao());
        json.put("tipo", categoria.getTipo());

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON,
                json.toString());
        Request request = new Request.
                Builder().
                url(URL + "editar/" + categoria.getId()).
                post(requestBody).
                build();
        Response response = okHttpClient.newCall(request).execute();
        Log.i("Resposta", response.body().string());
    }

    public void excluir(Categoria categoria) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().
                url(URL + "excluir/" + categoria.getId()).
                build();
        Response response = okHttpClient.newCall(request).execute();
        String resposta = response.body().string();
        Log.i("Response", resposta);
    }

}








