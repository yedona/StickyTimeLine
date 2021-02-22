package com.yedona.stickytimeline;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author: yedona
 * @date: 2021/2/22 14:15
 * @description
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private StickyTimeLineAdapter adapter;
    private StickyTimeLineSpanSizeLookUp spanSizeLookup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecycleView();

        initEditModel();

        initAddAction();

        initDeleteAction();


    }

    private void initRecycleView() {

        rv = ((RecyclerView) findViewById(R.id.rv));

        ArrayList<DateBean> list = DateBean.getList();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        spanSizeLookup = new StickyTimeLineSpanSizeLookUp(list, gridLayoutManager.getSpanCount());
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);

        rv.setLayoutManager(gridLayoutManager);

        adapter = new StickyTimeLineAdapter(list, this);

        rv.setAdapter(adapter);

        rv.addItemDecoration(new StickyTimeLineItemDecoration(this, list));


        adapter.setOnItemClickListener(new StickyTimeLineAdapter.OnItemClickListener() {
            @Override
            public void onClick(DateBean bean, int position) {

                Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initEditModel() {


        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btnEdit = (Button) view;

                if (adapter != null) {

                    if (adapter.isEditModel) {

                        btnEdit.setText("Edit");
                        adapter.notifyDataSetChanged();


                    } else {
                        btnEdit.setText("Done");
                        adapter.notifyDataSetChanged();
                    }

                    adapter.isEditModel = !adapter.isEditModel;

                }

            }
        });

    }

    private void initAddAction() {

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateBean e = new DateBean();

                e.isSelect = false;
                e.date = "2020-01-01";

                adapter.list.add(0, e);

                adapter.notifyDataSetChanged();
                spanSizeLookup.notifyDataSetChanged();

            }
        });


    }

    private void initDeleteAction() {

        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Iterator<DateBean> iterator = adapter.list.iterator();

                while (iterator.hasNext()) {
                    DateBean next = iterator.next();
                    if (next.isSelect) {
                        iterator.remove();
                    }
                }
                adapter.notifyDataSetChanged();

                spanSizeLookup.notifyDataSetChanged();

            }
        });


    }


}