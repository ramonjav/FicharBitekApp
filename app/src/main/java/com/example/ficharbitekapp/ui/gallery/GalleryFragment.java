package com.example.ficharbitekapp.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ficharbitekapp.AdaptaterNoti;
import com.example.ficharbitekapp.R;
import com.example.ficharbitekapp.databinding.FragmentGalleryBinding;
import com.example.ficharbitekapp.notifications;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    RecyclerView noti_lista;
    AdaptaterNoti adapter;
    List<notifications> notificationsList;
    String id_user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Bundle extras = getActivity().getIntent().getExtras();
        id_user = extras.getString("id_user");

        noti_lista = root.findViewById(R.id.list_noti);
        noti_lista.setLayoutManager(new GridLayoutManager(getContext(), 1));

        notificationsList = new ArrayList<>();

        obtenernoti();

        adapter = new AdaptaterNoti(getContext(), notificationsList);
        noti_lista.setAdapter(adapter);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void obtenernoti(){
        String URL = "http://192.168.10.34/git_bitek_fichar/Bitek_Fichar/bitek_fichar/api/api_notifi.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Usuarios");

                    for(int i = 0 ; i<jsonArray.length() ; i++){
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        notificationsList.add(
                                new notifications(
                                       jobject.getString("id_reg"),
                                        jobject.getString("fecha"),
                                        jobject.getString("hora"),
                                        jobject.getString("accion"),
                                        jobject.getString("aceptado_trabajador"),
                                        jobject.getString("aceptado_empresa"),
                                        jobject.getString("estado")
                                )
                        );

                    }

                    adapter = new AdaptaterNoti(getContext(), notificationsList);
                    noti_lista.setAdapter(adapter);

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
                parametros.put("id_user", id_user);

                return parametros;
            }
        };

        RequestQueue resquestQueque = Volley.newRequestQueue(getActivity());
        resquestQueque.add(stringRequest);
    }
}