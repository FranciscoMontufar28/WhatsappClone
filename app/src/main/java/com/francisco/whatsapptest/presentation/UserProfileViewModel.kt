package com.francisco.whatsapptest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.francisco.domain.OnGetUserInformationResponse
import com.francisco.domain.UserDomain
import com.francisco.domain.usercases.AuthenticationUserCases
import com.francisco.domain.usercases.FireStoreDatabaseUserCases
import com.francisco.whatsapptest.util.Event
import java.lang.Exception
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    val fireStoreDatabaseUserCases: FireStoreDatabaseUserCases,
    val authenticationUserCases: AuthenticationUserCases
) :
    ViewModel() {

    sealed class UserProfileNavigation {
        data class UserData(val userDomain: UserDomain) : UserProfileNavigation()
        data class Error(val message: String) : UserProfileNavigation()
        object HideLoading : UserProfileNavigation()
        object ShowLoading : UserProfileNavigation()
    }

    private val _event: MutableLiveData<Event<UserProfileNavigation>> =
        MutableLiveData()

    val event: LiveData<Event<UserProfileNavigation>> get() = _event

    fun getUserInformation() {
        _event.value = Event(UserProfileNavigation.ShowLoading)
        val currentUser = authenticationUserCases.getAuthCurrentUser.invoke()
        currentUser?.let { id ->
            fireStoreDatabaseUserCases.getUserInformation.invoke(id, object :
                OnGetUserInformationResponse {
                override fun addOnSuccessListener(userDomain: UserDomain) {
                    _event.value = Event(UserProfileNavigation.HideLoading)
                    _event.value = Event(UserProfileNavigation.UserData(userDomain))
                }

                override fun addOnFailureListener(exception: Exception) {
                    _event.value = Event(UserProfileNavigation.HideLoading)
                    _event.value =
                        Event(UserProfileNavigation.Error("There is an error getting the information"))
                }
            })
        }
    }
}