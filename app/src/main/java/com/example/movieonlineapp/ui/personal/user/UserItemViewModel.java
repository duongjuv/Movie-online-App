package com.example.movieonlineapp.ui.personal.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieonlineapp.domain.model.User;

public class UserItemViewModel extends ViewModel {
     private MutableLiveData<User> _mUser = new MutableLiveData<>();
     private LiveData<User> mUser = _mUser;
     private static UserItemViewModel instance;
     private UserItemViewModel(){

     }
     public static UserItemViewModel getInstance(){
         if(instance== null){
             synchronized (UserItemViewModel.class){
                 if(instance == null){
                     instance = new UserItemViewModel();
                 }
             }
         }
         return instance;
     }

     public void setUserData(User user){
         _mUser.setValue(user);
     }

     public LiveData<User> getUserData(){
         return this.mUser;
     }
}
