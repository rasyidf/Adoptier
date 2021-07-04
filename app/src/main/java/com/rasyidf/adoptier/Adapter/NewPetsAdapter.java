package com.rasyidf.adoptier.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rasyidf.adoptier.Activity.PetContentActivity;
import com.rasyidf.adoptier.Objects.Pet;
import com.rasyidf.adoptier.R;

import java.util.List;


public class NewPetsAdapter extends RecyclerView.Adapter<NewPetsAdapter.myViewHolder> {

    private List<Pet> petList;
    Context context;


    public NewPetsAdapter(Context context,List<Pet>petList){
        this.petList = petList;
        this.context = context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false));
    }



    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

       final Pet model = petList.get(position);

        holder.name.setText(model.getName());
        holder.age.setText(model.getAge());
        holder.type.setText(model.getType());
        Glide.with(context).load(model.getUriKey())
                .into(holder.petImageRv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PetContentActivity.class);
                intent.putExtra("name",model.getName());
                intent.putExtra("age",model.getAge());
                intent.putExtra("type",model.getType());
                intent.putExtra("city",model.getPetCity());
                intent.putExtra("info",model.getPetMoreInfo());
                intent.putExtra("image",model.getUriKey());
                intent.putExtra("fullName",model.getFullName());
                intent.putExtra("phoneNumber",model.getPhoneNumber());

                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return petList.size();
    }


    class  myViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView age;
        TextView type;
        ImageView petImageRv;


        public myViewHolder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            type = itemView.findViewById(R.id.type);
            petImageRv = itemView.findViewById(R.id.petImageRv);

        }

    }


}

