package com.example.rvadddeleteupdate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Model> models = new ArrayList<Model>();
    RecyclerView rv;
    RvAdapter rvAdapter;
    TextView tvAdd, tvUpdate;
    EditText etEnterName,etEnterNumber;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv_list_item);
        tvAdd = findViewById(R.id.tv_add);
        etEnterName = findViewById(R.id.et_enter_name);
        etEnterNumber = findViewById(R.id.et_enter_phn);
        tvUpdate = findViewById(R.id.tv_update);

        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rvAdapter = new RvAdapter(getApplicationContext(), models,
                new RvAdapter.Onclick() {
                    @Override
                    public void onEvent(Model model, int pos) {
                        position = pos;
                        tvUpdate.setVisibility(View.VISIBLE);
                        etEnterName.setText(model.getName());
                        etEnterNumber.setText(model.getNumber());
                    }
                });
        rv.setAdapter(rvAdapter);

        tvAdd.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add: {
                insertMethod(String.valueOf(etEnterName.getText()),String.valueOf(etEnterNumber.getText()));
            }
            break;
            case R.id.tv_update: {
                models.get(position).setName(etEnterName.getText().toString());
                models.get(position).setNumber(etEnterNumber.getText().toString());
                rvAdapter.notifyDataSetChanged();
                tvUpdate.setVisibility(View.GONE);
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void insertMethod(String name, String number) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("number", number);
            Model model = gson.fromJson(String.valueOf(jsonObject), Model.class);
            models.add(model);
            rvAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
