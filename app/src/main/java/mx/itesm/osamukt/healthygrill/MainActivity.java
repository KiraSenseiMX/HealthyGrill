package mx.itesm.osamukt.healthygrill;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private DataBaseManager manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private TextView tv;
    private ImageButton bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new DataBaseManager(this);
        lista = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.editText);
        bt = (ImageButton) findViewById(R.id.imageButton);
        bt.setOnClickListener(this);
        manager.eliminar("Asado de Res a la Pimienta");
        manager.insertar("Asado de Res a la Pimienta",
            "Convection Roast",
            "475 °F",
            "Charola",
            "1 corte de sirloin de res (2 kg aproximadamente)\n" +
                "Sal en grano\n" +
                "2 cucharaditas de azúcar\n" +
                "½ taza de granos de pimienta ligeramente aplastados con mortero o molcajete " +
                    "(Combinar pimienta negra, blanca y rosa)\n" +
                "1 barrita de mantequilla\n" +
                "2 dientes de ajo apachurrados\n",
            "Precalentar el horno. Colocar el corte de carne en la charola para hornear y condimentar " +
                "generosamente con sal y azúcar. Presionar las pimientas sobre toda la superficie de " +
                "la carne y colocar el Probe en el centro. Colocar la carne en el horno, conectar el " +
                "Probe y programar horneado hasta la temperatura interna deseada. Para seleccionar el " +
                "término deseado consultar la tabla en la página 7. Mientras la carne se hornea, " +
                "derretir la mantequilla con el ajo en un recipiente pequeño hasta que tome un ligero " +
                "color café. Remover los ajos y reservar la mantequilla. Cuando la carne esté lista, " +
                "retirar el horno, vaciar la mantequilla, cubrir con papel aluminio y reposar 10 " +
                "minutos antes de servir.");
        String[] from = new String[]{manager.CN_RECIPENAME,manager.CN_INGREDIENTS};
        int[] to = new int[]{android.R.id.text1,android.R.id.text2};
        cursor = manager.cargarCursorRecipes();
        adapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,cursor,from,to,0);
        lista.setAdapter(adapter);
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
            Toast.makeText(getApplicationContext(),"Buscando...",Toast.LENGTH_SHORT).show();
        }
        @Override
        protected Void doInBackground(Void ... voids) {
            cursor = manager.buscarReceta(tv.getText().toString());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.changeCursor(cursor);
            Toast.makeText(getApplicationContext(),"Listo",Toast.LENGTH_SHORT).show();
        }
    }
}
