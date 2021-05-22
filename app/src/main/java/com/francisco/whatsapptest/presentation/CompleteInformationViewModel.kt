package com.francisco.whatsapptest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.UserDomain
import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.FireStoreCloudUserCases
import com.francisco.whatsapptest.util.Event
import javax.inject.Inject

class CompleteInformationViewModel @Inject constructor(
    val authenticationUserCases: AuthenticationUserCases,
    val fireStoreCloudUserCases: FireStoreCloudUserCases
) : ViewModel() {

    sealed class CompleteInformationNavigation {
        object VerificationSuccess : CompleteInformationNavigation()
        data class VerificationError(val error: String) : CompleteInformationNavigation()
        object HideLoading : CompleteInformationNavigation()
        object ShowLoading : CompleteInformationNavigation()
    }

    private val _event: MutableLiveData<Event<CompleteInformationNavigation>> =
        MutableLiveData()

    val event: LiveData<Event<CompleteInformationNavigation>> get() = _event

    fun updateAuthCurrentUser(username: String, image: String?) {
        _event.value = Event(CompleteInformationNavigation.ShowLoading)
        getAuthCurrentUser()?.let { id ->
            val userDomain = UserDomain(id = id, nickname = username, image = image)
            fireStoreCloudUserCases.updateAuthCurrentUser.invoke(
                userDomain, object : OnFireStoreCloudListener {
                    override fun addOnSuccessListener() {
                        _event.value =
                            Event(CompleteInformationNavigation.HideLoading)
                        _event.value =
                            Event(CompleteInformationNavigation.VerificationSuccess)
                    }

                    override fun addOnFailureListener(exception: Exception) {
                        _event.value =
                            Event(CompleteInformationNavigation.HideLoading)
                        _event.value =
                            Event(CompleteInformationNavigation.VerificationError("The is an error saving the user information"))
                    }
                }
            )
        }
    }

    private fun getAuthCurrentUser(): String? {
        return authenticationUserCases.getAuthCurrentUser.invoke()
    }
}