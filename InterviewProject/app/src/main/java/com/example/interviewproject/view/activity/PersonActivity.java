package com.example.interviewproject.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.interviewproject.R;
import com.example.interviewproject.databinding.PersonActivityBinding;
import com.example.interviewproject.model.Person;
import com.example.interviewproject.view.adapter.PersonItemAdater;
import com.example.interviewproject.viewmodel.PersonItemViewModel;
import com.example.interviewproject.viewmodel.PersonViewModel;

import java.util.List;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class PersonActivity extends AppCompatActivity implements PersonViewModel.DataListener, PersonItemViewModel.ItemDataListener{

    private PersonViewModel personViewModel;
    private PersonActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        personViewModel = new PersonViewModel(this);
        personViewModel.setDataListener(this);
        binding = DataBindingUtil.setContentView(this, R.layout.person_activity);
        binding.setViewModel(personViewModel);
        setRecyclerView(binding.recyclerview);
    }

    private void setRecyclerView(RecyclerView recyclerView){
        PersonItemAdater adapter = new PersonItemAdater(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onPersonListChanged(List<Person> personList, int womanPosition) {
        PersonItemAdater adapter = (PersonItemAdater) binding.recyclerview.getAdapter();
        adapter.setPersonList(personList);
        adapter.setWomanPosition(womanPosition);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onPersonDeleted(Person person) {
        PersonItemAdater adapter = (PersonItemAdater) binding.recyclerview.getAdapter();
        int position = adapter.getPositionAndDeletePerson(person);
        adapter.notifyItemRemoved(position);
        personViewModel.getPersonCountText();
    }
}
