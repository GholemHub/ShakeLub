package com.example.shakelab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InfoShakeDialog extends AppCompatDialogFragment {
    private TextView nameShake;
    private ImageView shakeView;
    private TextView layers;
    private TextView ingredients;

    private String name;
    private String Image;
    private Note note;

    private AnyChartView anyChartView;


    InfoShakeDialog(Note note){
        this.note = note;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_info_shake, null);

        shakeView = view.findViewById(R.id.shake_view);
        nameShake = view.findViewById(R.id.text_view_shake_name);

        layers = view.findViewById(R.id.text_view_ingredients_count);
        ingredients = view.findViewById(R.id.IngredientsInfo);

        createAnyChartView(view);//CREATING PIE-CAHRT

        setLayers();
        setIngredients();
        nameShake.setText(note.getShakeName());

        Image = note.getShakeImage();



        if(note.getShakeImage() != null && note.getShakeImage() != ""){
            Picasso.get().load(Image).into(shakeView);
        }


        builder.setView(view).setTitle("").setNegativeButton("cansel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    private void createAnyChartView(View view) {
        anyChartView = view.findViewById(R.id.any_chart_view);

        setupPieChart(MakeIngredientsArray(note.getListPercentOfIngredients()));//SETTING VALUES OT PIE-CHART
    }

    public List<String> MakeIngredientsArray(String str){
    //TAKING THE STRING ARRAY AND MAKE A NEW ARRAY
        List<String> list = new ArrayList<String>();

        String str2 = "";
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != 47){
                str2 += str.charAt(i);
            }
            else if(str.charAt(i) == 47){
                Log.d("STR", "LOGGER: " + str2);
                list.add(str2);
                str2 ="";
            }
        }
        return list;
    }

    public void setupPieChart(List<String> list){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        List<String> str = getString(note);

        for(int i = 0; i < note.getCountOfLayers(); i++)
        {

            dataEntries.add(new ValueDataEntry(str.get(i), Integer.parseInt (list.get(i))));
            Log.d("NumIngredient","note.getShakeName() " + str.get(i) );
        }

        pie.data(dataEntries);

        anyChartView.setChart(pie);
    }

    private List<String> getString(Note note) {
        List<String> listIngredients = new ArrayList<>();
        //note.getShakeIngredientsString();
                String str1 = "";
                char str2 = '/';
        for(int i = 0; i < note.getShakeIngredientsString3().length(); i++){
            if(note.getShakeIngredientsString3().charAt(i) == str2){
                listIngredients.add(str1);
                str1 = "";
            }else{
                str1 += note.getShakeIngredientsString3().charAt(i);
            }
        }


    return listIngredients;
    }

    private void setIngredients() {
        ingredients.setText(note.getShakeIngredientsString2());
    }

    private void setLayers() {
        layers.setText( layers.getText() +" " + note.getCountOfLayers());
    }
}
