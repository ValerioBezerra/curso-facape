package br.facape.controlefinanceiropessoal.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.facape.controlefinanceiropessoal.model.Categoria;

    public class CategoriaBD {
        private SQLiteDatabase bd;

        public CategoriaBD(Context context) {
            this.bd = new DBHelper(context).getWritableDatabase();
        }

        public List<Categoria> listarTodas() {
            List<Categoria> listaCategorias = new ArrayList<Categoria>();

            Cursor cursor = bd.rawQuery("SELECT * FROM categoria",
                                        null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    categoria.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                    categoria.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                    listaCategorias.add(categoria);

                    cursor.moveToNext();
                }
            }

            return listaCategorias;
        }

        public Categoria listar(int id) {
            Categoria categoria = null;

            Cursor cursor = bd.rawQuery(" SELECT * FROM categoria " +
                                        " WHERE id = ?",
                    new String[]{String.valueOf(id)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                categoria = new Categoria();
                categoria.setId(cursor.getInt(cursor.getColumnIndex("id")));
                categoria.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                categoria.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
            }

            return categoria;
        }

        public void inserir(Categoria categoria) {
            try {
                bd.beginTransaction();

                ContentValues contentValues = new ContentValues();
                contentValues.put("descricao", categoria.getDescricao());
                contentValues.put("tipo", categoria.getTipo());

                if (bd.insert("categoria", null, contentValues) > 1)
                    bd.setTransactionSuccessful();
            } finally {
                bd.endTransaction();
            }
        }

        public void editar(Categoria categoria) {
            try {
                bd.beginTransaction();

                ContentValues contentValues = new ContentValues();
                contentValues.put("descricao", categoria.getDescricao());
                contentValues.put("tipo", categoria.getTipo());

                if (bd.update("categoria", contentValues,
                        "id = ?",
                        new String[]{String.valueOf(categoria.getId())}) > 0) {
                    bd.setTransactionSuccessful();
                }
            } finally {
                bd.endTransaction();
            }
        }

        public void excluir(Categoria categoria) {
            try {
                bd.beginTransaction();

                if (bd.delete("categoria", "id = ?",
                              new String[] {String.valueOf(categoria.getId())}) > 0)
                    bd.setTransactionSuccessful();
            } finally {
                 bd.endTransaction();
            }
        }
}
