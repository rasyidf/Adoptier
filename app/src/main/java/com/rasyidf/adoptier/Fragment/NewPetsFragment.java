package com.rasyidf.adoptier.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.rasyidf.adoptier.Adapter.NewPetsAdapter;
import com.rasyidf.adoptier.Objects.Pet;
import com.rasyidf.adoptier.R;

import java.util.ArrayList;
import java.util.List;


public class NewPetsFragment extends Fragment {
  ArrayList<Pet> petsname = new ArrayList<>();
  NewPetsAdapter adapter;
  RecyclerView recyclerView;
  Query query;
  DatabaseReference databaseReference;
  private String title;
  private int page;
  private List<Pet> petList;
  private ArrayList<String> mKeys = new ArrayList<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    databaseReference = database.getReference("adoptier/Pet");
    setHasOptionsMenu(true);

  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    petList = new ArrayList<>();

    updateList();

    final View view = inflater.inflate(R.layout.new_fragment, container, false);

    recyclerView = (RecyclerView) view.findViewById(R.id.newpetstable);


    LinearLayoutManager llm = new LinearLayoutManager(getContext());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(llm);
    adapter = new NewPetsAdapter(getActivity(), petList);


    recyclerView.setAdapter(adapter);


    return view;

  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  private void updateList() {
    databaseReference.addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {


        Pet pet = dataSnapshot.getValue(Pet.class);
        petList.add(pet);

        adapter.notifyDataSetChanged();

      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

}
