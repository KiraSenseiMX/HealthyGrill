package mx.itesm.osamukt.healthygrill;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private DataBaseManager manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private AutoCompleteTextView tv;
    private ImageButton bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new DataBaseManager(this);
        lista = (ListView) findViewById(R.id.listView);
        tv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        bt = (ImageButton) findViewById(R.id.imageButton);
        bt.setOnClickListener(this);
        /*ELIMINAR O INSERTAR RECETAS A LA BASE DE DATOS
        * manager.eliminar(StrReceta);
        * manager.insertar(StrReceta,StrFunción,StrTemperatura,StrUtencilio,StrIngredientes,StrProcedimiento); */
        cursor = manager.cargarCursorRecipes();
        String[] RecipeNamesArray = getResources().getStringArray(R.array.recipies_array);
        ArrayAdapter<String> RecipeNamesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,RecipeNamesArray);
        tv.setThreshold(1);
        tv.setAdapter(RecipeNamesAdapter);
        /*TEST*/
        String[] from = new String[]{manager.CN_RECIPENAME,manager.CN_INGREDIENTS};
        int[] to = new int[]{android.R.id.text1,android.R.id.text2};
        adapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,cursor,from,to,0);
        lista.setAdapter(adapter);
        /*ENDTEST*/
    }
    @Override
     public void onClick(View view) {
        if (view.getId() == R.id.imageButton) {
            new BuscarTask().execute();
        }
    }
    private class BuscarTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Buscando ...",Toast.LENGTH_SHORT).show();
        }
        @Override
        protected Void doInBackground(Void ... voids) {
            cursor = manager.buscarReceta(tv.getText().toString());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.changeCursor(cursor);
            Toast.makeText(getApplicationContext(),"Encontrado",Toast.LENGTH_SHORT).show();
        }
    }
}
