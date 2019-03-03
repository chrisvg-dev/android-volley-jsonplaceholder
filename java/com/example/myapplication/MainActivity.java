package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText nombre, apellidos, edad;
    private Button insert, show;
    private TextView result;
    private RequestQueue requestQueue;

    private ListView lista;
    private ArrayList lista2parse;
    private String urlData = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //this.nombre = (EditText)findViewById(R.id.txtNombre);
        //this.apellidos = (EditText)findViewById(R.id.txtApellido);
        //this.edad = (EditText)findViewById(R.id.txtEdad);
        //this.insert = (Button)findViewById(R.id.btnRegistrar);
        this.show = (Button)findViewById(R.id.btnMostrar);
        this.lista = (ListView)findViewById(R.id.listJSON);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        lista2parse = new ArrayList();

        this.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista2parse = new ArrayList();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        urlData,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try{
                                    for(int i=0;i<response.length();i++){
                                        JSONObject student = response.getJSONObject(i);

                                        String firstName = student.getString("title");
                                        String lastName = student.getString("body");
                                        String age = student.getString("id");

                                        lista2parse.add(firstName + "\n\n" + lastName);
                                    }
                                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, lista2parse);
                                    lista.setAdapter(adaptador);
                                }catch (Exception e){
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Snackbar.make(null, "Replace with your own action", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                );

                // Add JsonArrayRequest to the RequestQueue
                requestQueue.add(jsonArrayRequest);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
