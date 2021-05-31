package com.francisco.whatsapptest.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.domain.OnFireBaseStorageListener
import com.francisco.domain.OnFireStoreCloudListener
import com.francisco.domain.OnFireStoreCloudResponse
import com.francisco.domain.UserDomain
import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.FireBaseStorageUserCases
import com.francisco.usercases.FireStoreCloudUserCases
import com.francisco.usercases.SharedPreferencesUserCases
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.ImageCode
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class CompleteInformationViewModel @Inject constructor(
    val authenticationUserCases: AuthenticationUserCases,
    val fireStoreCloudUserCases: FireStoreCloudUserCases,
    val fireBaseStorageUserCases: FireBaseStorageUserCases,
    val sharedPreferencesUserCases: SharedPreferencesUserCases
) : ViewModel() {

    sealed class CompleteInformationNavigation {
        object VerificationSuccess : CompleteInformationNavigation()
        data class VerificationError(val error: String) : CompleteInformationNavigation()
        data class ImageSavedState(val imageCode: ImageCode, val data: String?) :
            CompleteInformationNavigation()

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
                    override fun addOnSuccessListener(state: OnFireStoreCloudResponse) {
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

    fun saveImageInCloudStore(context: Context, file: File) {
        viewModelScope.launch {
            fireBaseStorageUserCases.saveImageInCloudStore.invoke(context, file, object :
                OnFireBaseStorageListener {
                override fun addOnSuccessListener(url: String) {
                    _event.value =
                        Event(
                            CompleteInformationNavigation.ImageSavedState(
                                ImageCode.SAVEDIMAGE,
                                null
                            )
                        )
                }

                override fun addOnFailureListener(exception: java.lang.Exception) {
                    _event.value =
                        Event(CompleteInformationNavigation.VerificationError("Image not saved"))
                }
            })
        }
    }

    fun getImageUrl() {
        fireBaseStorageUserCases.getImageUrl.invoke(object : OnFireBaseStorageListener {
            override fun addOnSuccessListener(url: String) {
                _event.value =
                    Event(CompleteInformationNavigation.ImageSavedState(ImageCode.IMAGEURL, url))
            }

            override fun addOnFailureListener(exception: java.lang.Exception) {
                _event.value =
                    Event(CompleteInformationNavigation.VerificationError("Error getting url"))
            }
        })
    }

    fun setUserLoginAuthStatus(context: Context, isUserAuth: Boolean) {
        sharedPreferencesUserCases.setUserLoginAuthStatus.invoke(context, isUserAuth)
    }
}