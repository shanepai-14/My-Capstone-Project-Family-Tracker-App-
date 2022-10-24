package com.example.gpshelper.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpshelper.Activity.circle_members_map;
import com.example.gpshelper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class membersadepter extends RecyclerView.Adapter<membersadepter.membersadepterViewHolder> {

    DatabaseReference databaseReference, currentreference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;






    ArrayList<String> namelist, idlist;
    Context context;
    public membersadepter(){
        //default constructor
    }
    public membersadepter(ArrayList<String> namelist, ArrayList<String> idlist, Context context) {
        this.namelist = namelist;
        this.idlist = idlist;
        this.context = context;
    }

    @NonNull
    @Override
    public membersadepterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrecyclerview, parent,false);
        membersadepterViewHolder membersadepterView = new membersadepterViewHolder(v,context,namelist,idlist);
        return membersadepterView;
    }

    @Override
    public void onBindViewHolder(@NonNull membersadepterViewHolder holder,int position) {
        membersadepterViewHolder mv = (membersadepterViewHolder) holder;
        //users userobj = namelist.get(position);
        //holder.nametxt.setText(userobj.getFirstname());
       final String strobj = namelist.get(position);

        holder.nametxt.setText(strobj);


        String strobj1 = idlist.get(position);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        currentreference = FirebaseDatabase.getInstance().getReference("users").child(useruid).child("circle_members").child(strobj1);

        mv.txt_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,holder.txt_option);
                popupMenu.inflate(R.menu.option_menu);

                popupMenu.setOnMenuItemClickListener(item->{
                    switch (item.getItemId()){
                        case R.id.menu_remove:
                            currentreference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Family Remove"+strobj1, Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(holder.getAdapterPosition());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Failed"+e, Toast.LENGTH_SHORT).show();
                                }
                            });

                            break;

                    }

                   return false;
                });
           popupMenu.show();
            }
        });




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, circle_members_map.class);
                intent.putExtra("joined_uid",strobj1);
                context.startActivity(intent);
                //Toast.makeText(context,"response:"+strobj1,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        int list1 = namelist.size();
        int list2 = idlist.size();
        return list1 | list2;
    }


    public class membersadepterViewHolder extends RecyclerView.ViewHolder {
        TextView nametxt,txt_option;
        Context c;
        ArrayList<String> namearraylist, idarraylist;
        FirebaseUser firebaseUser;
        FirebaseAuth firebaseAuth;
        public membersadepterViewHolder (@NonNull View itemView, Context c, ArrayList<String> namearraylist, ArrayList<String> idarraylist)
        {
            super(itemView);
            this.c = c;
            this.namearraylist = namearraylist;
            this.idarraylist = idarraylist;

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            txt_option = itemView.findViewById(R.id.txt_option);
            nametxt = itemView.findViewById(R.id.textview_name);
        }



    }
}


/*class membersadepterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView nametxt, idtxt;
    Context c;
    ArrayList<String> namearraylist, idarraylist;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    public membersadepterViewHolder(@NonNull View itemView, Context c, ArrayList<String> namearraylist, ArrayList<String> idarraylist) {
        super(itemView);
        this.c = c;
        this.namearraylist = namearraylist;
        this.idarraylist = idarraylist;

        itemView.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        nametxt = itemView.findViewById(R.id.textview_name);
        idtxt = itemView.findViewById(R.id.textview_id);
    }

    @Override
    public void onClick(View v) {
        membersadepter obj = new membersadepter();
        //String uid = obj.strobj1;
        Toast.makeText(c,"response:"+obj.strobj1,Toast.LENGTH_LONG).show();
        /*Intent intent = new Intent(c, circle_members_map.class);
        intent.putExtra("joined_uid",);
        c.startActivity(intent);*/
   // }
//}
