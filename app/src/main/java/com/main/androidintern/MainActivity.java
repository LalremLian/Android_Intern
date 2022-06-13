package com.main.androidintern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.main.androidintern.adapter.RecyclerAdapter;
import com.main.androidintern.databinding.ActivityMainBinding;
import com.main.androidintern.model.ListModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;

    static final int READ_BLOCK_SIZE = 100;

    RecyclerAdapter recyclerAdapter;

    ArrayList<List<String>> infoPerPerson = new ArrayList<>();
    ArrayList<String> data = new ArrayList<>();
    ArrayList<ListModel> modelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.recyclerView.setHasFixedSize(true);
        mainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainBinding.btnSubmit.setOnClickListener(v ->
        {
            setData();
        });
        mainBinding.show.setOnClickListener(v ->
        {
            retriveList(infoPerPerson);
        });
    }

    //region getDataFromLocal
    private void getDataFromLocal() {
        //data = new ArrayList<>();
        try {
            FileInputStream fileIn = openFileInput("mytextfile.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();

            data.add(s);
            infoPerPerson.add(data);

            Log.e("list", infoPerPerson.size()+"");
            //textView.setText();

            //retriveList(infoPerPerson);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //endregion

    //region saveData
    private void setData() {
        try {
            FileOutputStream fileout = openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            //outputWriter.write(String.valueOf(infoPerPerson));
            outputWriter.write(mainBinding.edtImage.getText().toString());
            outputWriter.write("," + mainBinding.edtTitle.getText().toString());
            outputWriter.write("," + mainBinding.edtDescription.getText().toString());
            //outputWriter.write("," + password.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        getDataFromLocal();
    }
    //endregion

    private void retriveList(ArrayList<List<String>> listData){
        modelArrayList.clear();
        for(int i = 0; i<listData.size(); i++){
            String sData = listData.get(i).toString();
            Log.e("Arraydata", sData);
            String [] splitData = sData.split(",");

            String stImage = splitData[0].replace("[","");
            String stDescription = splitData[2].replace("]","");

            ListModel model = new ListModel(
                    stImage,
                    splitData[1],
                    stDescription
            );
/*            model.setImage(stImage);
            model.setTitle(splitData[1]);
            model.setDescription(stDescription);*/
            //model.setPassword(stPassword);
            Log.e("Model", model.toString());
            modelArrayList.add(i, model);
        }

        Log.e("FinalList", modelArrayList.toString());

        if(modelArrayList.size() > 0){
            recyclerAdapter = new RecyclerAdapter(MainActivity.this, modelArrayList);
            mainBinding.recyclerView.setAdapter(recyclerAdapter);
        }
    }
}