package br.facape.controlefinanceiropessoal.bd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
