package com.example.soul.cap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class SectionsPagerAdapter extends FragmentPagerAdapter{


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return  chatsFragment;


            //RequestsFragment requestsFragment = new RequestsFragment();
              //  return requestsFragment;

            case 1:
                //ChatsFragment chatsFragment = new ChatsFragment();
                //return  chatsFragment;

            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 1;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "CHATS";
                //return "REQUESTS";

            case 1:
                return "CHATS";

            case 2:
                return "FRIENDS";

            default:
                return null;
        }

    }

}
