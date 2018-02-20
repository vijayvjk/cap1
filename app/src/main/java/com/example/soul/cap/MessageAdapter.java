package com.example.soul.cap;

/**
 * Created by soul on 1/19/2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AkshayeJH on 24/07/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText,messageTextreceive,usertime,clienttime;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            usertime = (TextView) view.findViewById(R.id.time_text_layout2);
            clienttime = (TextView) view.findViewById(R.id.time_text_layout1);
            messageTextreceive = (TextView) view.findViewById(R.id.message_text_receive_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            //displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            mAuth = FirebaseAuth.getInstance();

        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);
        String cu=mAuth.getCurrentUser().getUid();

        String from_user = c.getFrom();
        String message_type = c.getType();


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();

                //viewHolder.displayName.setText(name);

                Picasso.with(viewHolder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.default_avatar).into(viewHolder.profileImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
// you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        String localTime = date.format(currentLocalTime);


        if(message_type.equals("text")) {

            if(from_user.equals(cu)){


                viewHolder.messageTextreceive.setBackgroundResource(R.drawable.receiver_background);
                viewHolder.messageTextreceive.setTextColor(Color.WHITE);


                viewHolder.profileImage.setVisibility(View.INVISIBLE);
                viewHolder.messageText.setVisibility(View.INVISIBLE);
                viewHolder.clienttime.setVisibility(View.INVISIBLE);
                viewHolder.usertime.setVisibility(View.VISIBLE);
                viewHolder.messageTextreceive.setVisibility(View.VISIBLE);

                viewHolder.messageTextreceive.setText(c.getMessage());
                viewHolder.usertime.setText(localTime);
            }
            else{




                viewHolder.messageText.setBackgroundResource(R.drawable.transmitter_background);
                viewHolder.messageText.setTextColor(Color.BLACK);

                viewHolder.profileImage.setVisibility(View.VISIBLE);
                viewHolder.messageText.setVisibility(View.VISIBLE);
                viewHolder.clienttime.setVisibility(View.VISIBLE);
                viewHolder.usertime.setVisibility(View.INVISIBLE);
                viewHolder.messageTextreceive.setVisibility(View.INVISIBLE);

                viewHolder.messageText.setText(c.getMessage());
                viewHolder.clienttime.setText(localTime);
            }




            viewHolder.messageImage.setVisibility(View.INVISIBLE);


        } else {

            viewHolder.messageText.setVisibility(View.INVISIBLE);
            Picasso.with(viewHolder.profileImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);

        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}
