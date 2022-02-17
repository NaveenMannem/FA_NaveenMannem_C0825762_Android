package com.example.fa_naveenmannem_c0825762_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class First extends AppCompatActivity {
    RadioButton normal,terrain,none, hybrid, satellite;
    Button curr_location,places;
    String select_map="";
    RadioGroup radioGrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        findIds();

        // This overrides the radiogroup onCheckListener
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    select_map=checkedRadioButton.getText()+"";
                }
            }
        });

        curr_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(none.isChecked()==false&&normal.isChecked()==false
                    &&terrain.isChecked()==false&&satellite.isChecked()==false
                        &&hybrid.isChecked()==false){
                    Toast.makeText(First.this, "Select map type", Toast.LENGTH_SHORT).show();

                }else if(!getPermissison()){
                   getPermissison();
                }
                else{
                    Intent intent=new Intent(First.this,MapsActivity.class);
                    intent.putExtra("TYPE",select_map);
                    intent.putExtra("MODEL","");
                    startActivity(intent);
                }
            }
        });

        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(First.this,Fav.class));
            }
        });
    }

    public boolean getPermissison(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity( new Intent(First.this,MapsActivity.class));
                    finish();
                }
                break;
        }
    }

    private void findIds() {
        none=findViewById(R.id.none);
        normal=findViewById(R.id.normal);
        terrain=findViewById(R.id.terrain);
        hybrid=findViewById(R.id.hybrid);
        satellite=findViewById(R.id.satellite);
        curr_location=findViewById(R.id.currentLocation);
        places=findViewById(R.id.savedPlaces);
        radioGrp=findViewById(R.id.radioGrp);




    }
}