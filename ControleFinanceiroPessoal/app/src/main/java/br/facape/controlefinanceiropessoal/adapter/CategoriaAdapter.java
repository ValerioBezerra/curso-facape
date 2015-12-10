package br.facape.controlefinanceiropessoal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.model.Categoria;

public class CategoriaAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Categoria> listaCategorias;

    public CategoriaAdapter(Context context,
                            List<Categoria> listaCategorias) {
        this.layoutInflater  = (LayoutInflater)
                                context.getSystemService
                                (Context.LAYOUT_INFLATER_SERVICE);
        this.listaCategorias = listaCategorias;
    }

    @Override
    public int getCount() {
        return listaCategorias.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCategorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaCategorias.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.adapter_categorias,
                                           parent, false);

        TextView txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
        TextView txtTipo      = (TextView) view.findViewById(R.id.txtTipo);


        txtDescricao.setText(listaCategorias.get(position).getDescricao());

        if (listaCategorias.get(position).getTipo().equals("D"))
            txtTipo.setText("Despesa");
        else
            txtTipo.setText("Receita");

        return view;
    }
}
