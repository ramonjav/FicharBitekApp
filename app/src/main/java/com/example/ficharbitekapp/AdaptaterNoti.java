package com.example.ficharbitekapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  AdaptaterNoti extends RecyclerView.Adapter<AdaptaterNoti.notificationsViewHolder> {

    Context context;
    List<notifications> notifi_list;

    public AdaptaterNoti(Context context, List<notifications> notifi_list) {
        this.context = context;
        this.notifi_list = notifi_list;
    }

    @NotNull
    @Override
    public AdaptaterNoti.notificationsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noti, parent, false);
        return new notificationsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdaptaterNoti.notificationsViewHolder holder, int position) {
        holder.txt_idreg.setText(notifi_list.get(position).getId_reg() + " " + notifi_list.get(position).getAccion());
        holder.txt_fecha.setText(notifi_list.get(position).getFecha() + " " + notifi_list.get(position).getHora() + " " +  notifi_list.get(position).getEstado());

        holder.acep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarnotifi(notifi_list.get(position).getId_reg(), "1");
            }
        });

        holder.rechz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarnotifi(notifi_list.get(position).getId_reg(), "2");
            }
        });
    }



    @Override
    public int getItemCount() {
        return notifi_list.size();
    }


    public class notificationsViewHolder extends RecyclerView.ViewHolder {
        TextView txt_idreg, txt_fecha;
        Button acep, rechz;

        public notificationsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_idreg = itemView.findViewById(R.id.id_reg);
            txt_fecha = itemView.findViewById(R.id.id_fecha);
            acep = itemView.findViewById(R.id.itm_acep);
            rechz = itemView.findViewById(R.id.itm_denegar);

        }
    }
    
    private void actualizarnotifi(String id_reg, String acc){
        String URL = "http://192.168.10.34/git_bitek_fichar/Bitek_Fichar/bitek_fichar/api/api_setnoti.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_reg", id_reg);
                parametros.put("ac", acc);
                return parametros;
            }
        };

        RequestQueue resquestQueque = Volley.newRequestQueue(context);
        resquestQueque.add(stringRequest);
    }
}
