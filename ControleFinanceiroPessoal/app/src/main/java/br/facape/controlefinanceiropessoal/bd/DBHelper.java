package br.facape.controlefinanceiropessoal.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by valerio on 08/12/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "controlefinanceiro";
    private static final int VERSAO_BANCO  = 2;

    public DBHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("CLODIN", "OnCreate");

        db.execSQL(" CREATE TABLE categoria (" +
                   "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                   "  descricao VARCHAR(25), " +
                   "  tipo CHAR(1) " + // R-RECEITA D-DESPESA
                   " );");

        db.execSQL(" CREATE TABLE lancamento (" +
                   "  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                   "  id_categoria INTEGER, " +
                   "  data DATE, " +
                   "  valor NUMERIC(15, 2), " +
                   "  descricao VARCHAR(25), " +
                   "  observacao VARCHAR(255) " +
                   " );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((oldVersion <= 1) && (newVersion >= 2)) {
            Log.i("Clodin", "Versao2");
            db.execSQL(" INSERT INTO categoria VALUES (NULL, 'Alimentação', 'D');");
            db.execSQL(" INSERT INTO categoria VALUES (NULL, 'Renda Fixa', 'R');");
        }
    }
}
