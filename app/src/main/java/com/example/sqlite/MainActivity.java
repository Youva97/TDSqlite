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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private StringBuffer monBuffer;
    private Context context;
    protected Button btnBaseData;
    protected EditText editTxtBaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBaseData = findViewById(R.id.btnBaseData);
        editTxtBaseData = findViewById(R.id.editTxtBaseData);



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

        btnBaseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = editTxtBaseData.getText().toString().trim();
                Cursor cursor;
                if (searchTerm.isEmpty()) cursor = maSQLdb.lireTable();
                else {
                    // Vérifiez si l'utilisateur a entré un nombre ou un prénom
                    try {
                        // Si l'utilisateur a entré un nombre, recherche par âge
                        int age = Integer.parseInt(searchTerm);
                        cursor = maSQLdb.lireTable(age);
                    } catch (NumberFormatException e) {
                        Log.d("catch", "passage catch " + searchTerm);
                        // Si l'utilisateur n'a pas entré un nombre, recherche par Prénom / Nom
                        cursor = maSQLdb.lireTable(searchTerm);
                        Log.d("catch", "passage catch " + searchTerm);

                    }
                }

                if (cursor.getCount() == 0) {
                    // Si pas d'enregistrements, affichez un message
                    Toast.makeText(MainActivity.this, "Aucune correspondance trouvée.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si des enregistrements sont trouvés, traitez les données
                    monBuffer = new StringBuffer();
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        monBuffer.append("id:" + cursor.getInt(0) + "\n\r\r");
                        monBuffer.append("NOM:" + cursor.getString(1) + "\n\r\r");
                        monBuffer.append("PRENOM:" + cursor.getString(2) + "\n\r\r");
                        monBuffer.append("AGE :" + cursor.getInt(3) + "\n");
                        monBuffer.append("__________\n");
                        cursor.moveToNext();
                    }
                    cursor.close();
                    Log.d("Buffer", "monBuffer" + monBuffer);
                    afficheMaBAse("Résultats de la recherche", monBuffer);
                }
            }
        });
    }
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
    }

}