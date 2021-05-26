package dev.stefifox.scuola.sportapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import dev.stefifox.scuola.sportapp.core.*;

public class AllSport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allsport);

        final TabLayout sportList = findViewById(R.id.sportsall);
        final LinearLayout allsport = findViewById(R.id.visualizer);

        if(!MainActivity.sport.isEmpty()){
            for(int i = 0; i<MainActivity.sport.size(); i++) {
                Sport temp = MainActivity.sport.get(i);
                sportList.addTab(sportList.newTab().setText(temp.getNome()));
            }
            sportList.addTab(sportList.newTab().setText("Tutte"));
            popola(1);
        }else {
            TextView temp = new TextView(AllSport.this);
            temp.setText("Nessuna connessione");
            temp.setTextSize(20);
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            allsport.addView(temp);
        }


        sportList.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Tutte")){
                    popolaTutte();
                }else{
                    int pos = sportList.getSelectedTabPosition() + 1;
                    popola(pos);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void popola(int pos){
        final LinearLayout teamView = findViewById(R.id.visualizer);
        int count = 0;

        teamView.removeAllViews();
        //System.out.println("Posizione " + pos);
        for (int i = 0; i<MainActivity.squadre.size(); i++){

            Squadra s = MainActivity.squadre.get(i);
            Sport sp = s.getSport();
            //System.out.println("Sport id: " + sp.getId() + " - " + sp.getDescizione() + "-" + s.getNome());
            if(sp.getId() == pos){
                TextView temp = new TextView(AllSport.this);
                ImageView like = new ImageView(AllSport.this);
                LinearLayout tempLayout = new LinearLayout(AllSport.this);

                like.setImageDrawable(getDrawable(R.drawable.star));
                temp.setText(s.getNome());
                temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                temp.setTextSize(20);

                tempLayout.addView(temp);
                tempLayout.addView(like);
                tempLayout.setGravity(Gravity.CENTER);
                teamView.addView(tempLayout);
                count ++;
            }
        }
        if(count == 0){
            TextView temp = new TextView(AllSport.this);
            temp.setText("Non ho trovato squadre");
            temp.setTextSize(20);
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            teamView.addView(temp);
        }
    }

    private void popolaTutte(){
        final LinearLayout teamView = findViewById(R.id.visualizer);
        int count = 0;

        teamView.removeAllViews();
        for (int i = 0; i<MainActivity.squadre.size(); i++){

            Squadra s = MainActivity.squadre.get(i);
            Sport sp = s.getSport();
            //System.out.println("Sport id: " + sp.getId() + " - " + sp.getDescizione() + "-" + s.getNome());
            TextView temp = new TextView(AllSport.this);
            ImageView like = new ImageView(AllSport.this);
            LinearLayout tempLayout = new LinearLayout(AllSport.this);

            like.setImageDrawable(getDrawable(R.drawable.star));
            temp.setText(s.getNome() + " " + sp.getNome());
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            temp.setTextSize(20);

            tempLayout.addView(temp);
            tempLayout.addView(like);
            tempLayout.setGravity(Gravity.CENTER);
            teamView.addView(tempLayout);
            count ++;
        }
        if(count == 0){
            TextView temp = new TextView(AllSport.this);
            temp.setText("Non ho trovato squadre");
            temp.setTextSize(20);
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            teamView.addView(temp);
        }
    }

}
