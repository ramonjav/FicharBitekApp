package com.example.ficharbitekapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ficharbitekapp.MainActivity;
import com.example.ficharbitekapp.PrincipalActivity;
import com.example.ficharbitekapp.R;
import com.example.ficharbitekapp.databinding.FragmentHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle extras = getActivity().getIntent().getExtras();

        Button btn_inicio = root.findViewById(R.id.button_iniciar);
        Button btn_final = root.findViewById(R.id.button_final);

        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                insertarUser(extras.getString("id_user"), date, hour, "0", "inicio");
            }
        });

        btn_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                insertarUser(extras.getString("id_user"), date, hour, "0", "final");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void insertarUser(String id, String date, String hour, String confirm, String accion) {
        String URL = "http://192.168.10.34/git_bitek_fichar/Bitek_Fichar/bitek_fichar/api/api_fichar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");

                    if(message.equals("Confirmar")){

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setMessage("El ultimo registro que ha seleccionado ha sido " + accion + ". ¿Esta seguro que quiere continuar?").setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        insertarUser(id, date, hour, "1", accion);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });


                        alert.setTitle("Acción Repetida");
                        alert.show();

                    }

                    if(message.equals("Cool")){
                        Toast.makeText(getActivity(),"Se ha fichado de manera correcta", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", id);
                parametros.put("date", date);
                parametros.put("hour", hour);
                parametros.put("confirm", confirm);
                parametros.put("accion", accion);
                return parametros;
            }
        };

        RequestQueue resquestQueque = Volley.newRequestQueue(getActivity());
        resquestQueque.add(stringRequest);
    }
}