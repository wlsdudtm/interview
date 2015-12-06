package com.example.interviewproject.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.interviewproject.R;
import com.example.interviewproject.databinding.PersonItemBinding;
import com.example.interviewproject.model.Person;
import com.example.interviewproject.viewmodel.PersonItemViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by jinyoung on 2015-12-04.
 */
public class PersonItemAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Person> mPersonList;

    private PersonItemViewModel.ItemDataListener mDataListener;

    private final int TYPE_MAN_HEADER = 0;
    private final int TYPE_WOMAN_HEADER = 1;
    private final int TYPE_ITEM = 2;

    private int mManPosition = 0;
    private int mWomanPosition = 0;

    public PersonItemAdater(PersonItemViewModel.ItemDataListener dataListener){
        mPersonList = Collections.emptyList();
        mDataListener = dataListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_MAN_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view_man, parent, false);
            return new VHManHeader(v);
        }else if(viewType == TYPE_WOMAN_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view_woman, parent, false);
            return new VHWomanHeader(v);
        }else{
            PersonItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.person_item, parent, false);

            return new VHItem(binding);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mWomanPosition == 0){
            return;
        }

        if(holder instanceof VHItem && position> mWomanPosition){
            ((VHItem)holder).bindPerson(mPersonList.get(position - 2));
        }else if(holder instanceof VHItem && position> mManPosition){
            ((VHItem) holder).bindPerson(mPersonList.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mManPosition){
            return 0;
        }else if(position == mWomanPosition){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return mPersonList.size()+2;
    }

    public int getPositionAndDeletePerson(Person person){
        int position = -1;
        for(int i=0;i<mPersonList.size();i++){
            if(person.getId() == mPersonList.get(i).getId()){
                mPersonList.remove(i);
                position = i;
            }
        }
        if(position+2 > mWomanPosition){
            return position+2;
        }else if(position+1 > mManPosition){
            mWomanPosition -= 1;
            return position+1;
        }

        return -1;
    }

    public void setPersonList(List<Person> personList){
        mPersonList = personList;
    }

    public void setWomanPosition(int womanPosition) {
        this.mWomanPosition = womanPosition;
    }



    class VHItem extends RecyclerView.ViewHolder{
        PersonItemBinding binding;

        public VHItem(PersonItemBinding binding) {
            super(binding.cardview);
            this.binding = binding;
        }

        void bindPerson(Person person){
            if(binding.getViewModel() == null){
                binding.setViewModel(new PersonItemViewModel(itemView.getContext(), person, mDataListener));
            }else{
                binding.getViewModel().setPerson(person);
            }
        }
    }

    class VHManHeader extends RecyclerView.ViewHolder{
        TextView textview;
        public VHManHeader(View itemView){
            super(itemView);
            this.textview = (TextView)itemView.findViewById(R.id.manlist);
        }
    }

    class VHWomanHeader extends RecyclerView.ViewHolder{
        TextView textview;
        public VHWomanHeader(View itemView){
            super(itemView);
            this.textview = (TextView)itemView.findViewById(R.id.womanlist);
        }
    }

}
