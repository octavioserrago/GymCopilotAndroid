package com.example.gymcopilotandroid;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listaDias;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaDias = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDias);
        ListView listViewDias = findViewById(R.id.listViewDias);
        listViewDias.setAdapter(adapter);
        ImageButton addNewTrainingDayButton = findViewById(R.id.AddNewTrainingDayButton);

        addNewTrainingDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoNombreDia();
            }
        });

        listViewDias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreDiaSeleccionado = listaDias.get(position);
                //TODO: Este elemento es importante para redireccionar proximamente, al dia correspondiente. En este caso solo agregue un toast para mostrar que el dia se selecciona
                Toast.makeText(MainActivity.this, "Día seleccionado: " + nombreDiaSeleccionado, Toast.LENGTH_SHORT).show();
            }
        });

        // Funcionalidad para eliminar un día de entrenamiento de la lista
        listViewDias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Eliminar día de entrenamiento");
                builder.setMessage("¿Estás seguro de que deseas eliminar este día?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listaDias.remove(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Día eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void mostrarDialogoNombreDia() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingresa el nombre del día de entrenamiento");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreDia = input.getText().toString().trim();
                if (!nombreDia.isEmpty()) {
                    listaDias.add(nombreDia);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Día de entrenamiento: " + nombreDia, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, ingresa un nombre válido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
