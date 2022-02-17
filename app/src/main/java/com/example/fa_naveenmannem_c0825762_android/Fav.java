package com.example.fa_naveenmannem_c0825762_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fav extends AppCompatActivity {
    RecyclerView  recyclerView;
    DbHelper dbHelper;
    List<DataModel> all_places;

    Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        findIds();
        getData();
    }

    private void getData() {
        dbHelper=new DbHelper(Fav.this);
        all_places =dbHelper.getAllPlaces();

        adapter = new Adapter(Fav.this, all_places);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findIds() {
        recyclerView=findViewById(R.id.recycler);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

        Context context;
        List<DataModel> list;


        public Adapter(Context context, List<DataModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_design, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            DataModel model = list.get(position);
            holder.name.setText("Latitude: "+model.getLat()+"\n"+"Longitude: "+model.getLng()+"\n"+model.getPlaceName());



            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dbHelper.deletePlace(model);
                    getData();

                }
            });

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Fav.this,MapsActivity.class);
                    intent.putExtra("TYPE","");
                    intent.putExtra("MODEL",model);
                    startActivity(intent);
                    finish();

                }
            });




        }




        @Override
        public int getItemCount() {
            return list.size();
        }



        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            Button delete,edit;


            public MyViewHolder(View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                delete = itemView.findViewById(R.id.delete);
                edit = itemView.findViewById(R.id.edit);


            }
        }
    }
}