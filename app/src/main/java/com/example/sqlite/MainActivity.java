package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);



        SQLiteMaDataBase maSQLdb = new SQLiteMaDataBase(this);
        /*
        maSQLdb.insertionCLIENTS("Mineas", "Gaël", 26);
        maSQLdb.insertionCLIENTS("Moreira Tavares", "Devy", 28);
        maSQLdb.insertionCLIENTS("Salamova", "Jeina", 26);
        maSQLdb.insertionCLIENTS("O'Mahony", "Kylian", 22);
        maSQLdb.insertionCLIENTS("Monkey D", "Luffy", 19);
        maSQLdb.insertionCLIENTS("Gold D", "Roger", 56);
        maSQLdb.insertionCLIENTS("Le Conquérant", "Gutz", 24);
        maSQLdb.insertionCLIENTS("Uzumaki", "Naruto", 32);

        */

        /*Cursor monCursor2 = maSQLdb.lireTable();*/
        Cursor cursorName = maSQLdb.lireTable();
        Cursor cursorAge = maSQLdb.lireTable();

        /*if(monCursor2.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Il n'y a pas de donnée dans la table", Toast.LENGTH_LONG).show();
        } else {
            monBuffer = new StringBuffer();
            monCursor2.moveToFirst();

            while (!monCursor2.isAfterLast()){

                monBuffer.append("Id: ").append(monCursor2.getInt(0)).append("\n\r\r");
                monBuffer.append("NOM: ").append(monCursor2.getString(1)).append("\n\r\r");
                monBuffer.append("PRENOM: ").append(monCursor2.getString(2)).append("\n\r\r");
                monBuffer.append("NOM: ").append(monCursor2.getInt(3)).append("\n\r\r");

                monBuffer.append("____________\n");
                monCursor2.moveToNext();
            }
            monCursor2.close();
            Log.d("Buffer", "MonBuffer :" + monBuffer);

            afficheMaBAse("Table: " + maSQLdb.NOM_TABLE, monBuffer);
        }*/
        Cursor cursor = maSQLdb.lireTable();

        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "Aucune correspondance trouvée.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<HashMap<String,String>> listItem = new ArrayList<HashMap<String,String>>();

            HashMap<String,String> map;

            map = new HashMap<String,String>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                map = new HashMap<String,String>();
                map.put("idClient" , String.valueOf(cursor.getInt(0)));
                map.put("NOM" , (cursor.getString(1)) );
                map.put("PRENOM" ,(cursor.getString(2)));
                map.put("AGE", (String.valueOf(cursor.getString(3))) );
                cursor.moveToNext();
                listItem.add(map);

            }
            SimpleAdapter monAdapter = new SimpleAdapter(this.getBaseContext(),
                    listItem, R.layout.affichage_customer, new String[] {"idClient","NOM", "PRENOM", "AGE"}, new int[] {R.id.numberCustomer,R.id.txtLastname,R.id.txtFirsname, R.id.txtAge});

            listView.setAdapter(monAdapter);
            cursor.close();


        }

    }
    /*
    public void afficheMaBAse(String titre, StringBuffer message) {
        AlertDialog aD = new AlertDialog.Builder(this).create();
        aD.setTitle(titre);
        aD.setMessage(message);

        aD.setButton(AlertDialog.BUTTON_POSITIVE, "Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onRestart();
            }
        });

        aD.setButton(AlertDialog.BUTTON_NEGATIVE, "QUIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        aD.show();
    }*/

}