package br.facape.controlefinanceiropessoal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.facape.controlefinanceiropessoal.R;
import br.facape.controlefinanceiropessoal.model.Categoria;
import br.facape.controlefinanceiropessoal.model.Lancamento;

/**
 * Created by pablo on 09/12/2015.
 */
public class LancamentoAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Lancamento> listaLancamentos;

    public LancamentoAdapter(Context context, List<Lancamento> listaLancamentos) {
        this.layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaLancamentos = listaLancamentos;
    }

    @Override
    public int getCount() {
        return listaLancamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLancamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaLancamentos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.adapter_lancamentos, parent, false);

        TextView txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
        TextView txtData      = (TextView) view.findViewById(R.id.txtData);
        TextView txtValor     = (TextView) view.findViewById(R.id.txtValor);

        txtDescricao.setText(listaLancamentos.get(position).getDescricao());
        txtData.setText(listaLancamentos.get(position).getData());
        txtValor.setText(new DecimalFormat("0.00").format(listaLancamentos.get(position).getValor()));

        return view;
    }
}
