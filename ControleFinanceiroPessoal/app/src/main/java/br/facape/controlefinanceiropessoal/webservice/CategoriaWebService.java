package br.facape.controlefinanceiropessoal.webservice;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.facape.controlefinanceiropessoal.model.Categoria;

/**
 * Created by valerio on 15/12/15.
 */
public class CategoriaWebService {

    public List<Categoria> listarCategorias() throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().
                              url("http://10.42.0.1/web/curso_facape/categoria_json/retornar_categorias").
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
}








