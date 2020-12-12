package com.thang.a17047711_thaivanthang_api_crud_mock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnDelete, btnUpdate;
    EditText editFirstName, editLastName, editSalary;
    RadioButton radioMale, radioFemale;
    ListView lvName;
    ArrayList nameList;
    ArrayList idList;
    ArrayAdapter adapter;
    int index;
    Button btnLoad;
    String radioSelected = "";
    String url = "https://5fcecba83e19cc00167c62ce.mockapi.io/users";
    TextView tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idList = new ArrayList();
        nameList = new ArrayList();

        lvName = findViewById(R.id.lvName);

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editSalary = findViewById(R.id.editSalary);

        radioMale.setChecked(true);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, nameList);
        lvName.setAdapter(adapter);

        CompoundButton.OnCheckedChangeListener listenerRadio
                = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //btnUpdate.setEnabled(true);
                if (radioMale.isChecked())
                {
                    radioSelected = radioMale.getText().toString().trim();
                }
                else
                {
                    radioSelected = radioFemale.getText().toString().trim();
                }
            }
        };

        radioMale.setOnCheckedChangeListener(listenerRadio);
        radioFemale.setOnCheckedChangeListener(listenerRadio);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFirstName.setText("");
                editLastName.setText("");
                editSalary.setText("");
                radioFemale.setChecked(false);
                radioMale.setChecked(true);
                GetArrayJson(url);
                adapter.notifyDataSetChanged();
            }
        });

        lvName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>
                                            adapterView, View view, int i, long l) {
                index = i;
                //name_edt.setText(nameList.get(index).toString());
                User user = new User();
                user = (User) idList.get(index);
                editFirstName.setText(user.getFirstname());
                editLastName.setText(user.getLastname());
                if (user.getGender().equals("Male"))
                    radioMale.setChecked(true);
                else
                    radioFemale.setChecked(true);
                editSalary.setText(user.getSalary());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostApi(url);
                GetArrayJson(url);
                adapter.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFirstName.setText("");
                editLastName.setText("");
                editSalary.setText("");
                radioFemale.setChecked(false);
                radioMale.setChecked(false);
                DeleteApi(url, ((User) idList.get(index)).getId());
                GetArrayJson(url);
                adapter.notifyDataSetChanged();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioMale.isChecked())
                {
                    radioSelected = radioMale.getText().toString().trim();
                }
                else
                {
                    radioSelected = radioFemale.getText().toString().trim();
                }
                PutApi(url, ((User) idList.get(index)).getId());
                GetArrayJson(url);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void GetData(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                tvDisplay.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error make by API server!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void GetJson(String url){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tvDisplay.setText(response.getString("LASTNAME").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by get JsonObject...", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    //Lấy danh sách users
    private void GetArrayJson(String url){
        //
        idList.clear();
        nameList.clear();
        //
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject object = (JSONObject) response.get(i);

                        int id = object.getInt("id");
                        String firstname = object.getString("firstname").toString();
                        String lastname = object.getString("lastname").toString();
                        String gender = object.getString("gender").toString();
                        String salary = object.getString("salary").toString();
                        User us= new User(id,firstname,lastname,gender,salary);
                        idList.add(us);
                        nameList.add(us);
                        adapter.notifyDataSetChanged();
                        //

                        //Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi lấy danh sách users", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void PostApi(String url){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi thêm user", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("firstname", editFirstName.getText().toString().trim());
                params.put("lastname", editLastName.getText().toString().trim());
                params.put("gender", radioSelected);
                params.put("salary", editSalary.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void PutApi(String url, final int id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                url + '/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi sửa user"+String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("firstname", editFirstName.getText().toString().trim());
                params.put("lastname", editLastName.getText().toString().trim());
                params.put("gender", radioSelected);
                params.put("salary", editSalary.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void DeleteApi(String url, final int id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + '/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi khi xóa users"+String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}