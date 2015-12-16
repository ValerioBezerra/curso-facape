package br.facape.controlefinanceiropessoal.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.facape.controlefinanceiropessoal.model.Lancamento;

/**
 * Created by pablo on 13/12/2015.
 */
public class LancamentoBD {

    private SQLiteDatabase db;

    public LancamentoBD(Context context) {
        this.db = new DBHelper(context).getWritableDatabase();
    }

    public List<Lancamento> listarTodas(){
        List<Lancamento> listaLancamentos = new ArrayList<Lancamento>();

        Cursor cursor = db.rawQuery("SELECT * FROM lancamento", null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Lancamento lancamento = new Lancamento();

                lancamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                lancamento.setIdCategoria(cursor.getInt(cursor.getColumnIndex("id_categoria")));
                lancamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                lancamento.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                lancamento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                lancamento.setData(cursor.getString(cursor.getColumnIndex("data")));
                listaLancamentos.add(lancamento);

                cursor.moveToNext();
            }
        }

        return listaLancamentos;
    }

    public void inserir(Lancamento lancamento) {
        try {
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();

            contentValues.put("id_categoria", lancamento.getIdCategoria());
            contentValues.put("descricao",lancamento.getDescricao());
            contentValues.put("observacao",lancamento.getObservacao());
            contentValues.put("valor",lancamento.getValor());
            contentValues.put("data",lancamento.getData());

            db.insert("lancamento", null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void editar(Lancamento lancamento){
        try {
            db.beginTransaction();

            ContentValues contentValues = new ContentValues();
            contentValues.put("id_categoria", lancamento.getIdCategoria());
            contentValues.put("observacao",lancamento.getObservacao());
            contentValues.put("valor",lancamento.getValor());
            contentValues.put("data",lancamento.getData());

            if (db.update("lancamento", contentValues, "id = ?", new String[]{String.valueOf(lancamento.getId())}) > 0)
                db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void excluir(Lancamento lancamento){
        try {
            db.beginTransaction();

            if (db.delete("lancamento", "id = ?", new String[]{String.valueOf(lancamento.getId() )}) > 0)
                db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
