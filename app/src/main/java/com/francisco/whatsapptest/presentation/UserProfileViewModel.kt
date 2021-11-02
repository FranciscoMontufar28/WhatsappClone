package com.francisco.whatsapptest.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.domain.*
import com.francisco.domain.usercases.AuthenticationUserCases
import com.francisco.domain.usercases.FireBaseStorageUserCases
import com.francisco.domain.usercases.FireStoreDatabaseUserCases
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.ImageCode
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    val fireStoreDatabaseUserCases: FireStoreDatabaseUserCases,
    val authenticationUserCases: AuthenticationUserCases,
    val fireBaseStorageUserCases: FireBaseStorageUserCases
) :
    ViewModel() {

    sealed class UserProfileNavigation {
        data class UserData(val userDomain: UserDomain) : UserProfileNavigation()
        data class Error(val message: String) : UserProfileNavigation()
        object ImageDeleted : UserProfileNavigation()
        object ImageSaved: UserProfileNavigation()
        object HideLoading : UserProfileNavigation()
        object ShowLoading : UserProfileNavigation()
        object NameSaved : UserProfileNavigation()
        object AboutSaved : UserProfileNavigation()
        data class ImageSavedState(val imageCode: ImageCode, val data: String?) :
            UserProfileNavigation()
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

    fun updateUserProfileImage(url: String) {
        val currentUser = authenticationUserCases.getAuthCurrentUser.invoke()
        currentUser?.let {
            val userDomain = UserDomain(id = it,  image = url)
            fireStoreDatabaseUserCases.updateAuthCurrentUser.invoke(
                userDomain, object : OnFireStoreCloudListener {
                    override fun addOnSuccessListener(state: OnFireStoreCloudResponse) {
                        _event.value =
                            Event(UserProfileNavigation.HideLoading)
                        _event.value =
                            Event(UserProfileNavigation.ImageSaved)
                    }

                    override fun addOnFailureListener(exception: Exception) {
                        _event.value =
                            Event(UserProfileNavigation.HideLoading)
                        _event.value =
                            Event(UserProfileNavigation.Error("The is an error saving the user information"))
                    }
                }
            )
        }
    }

    fun deleteUserProfileImage() {
        val currentUser = authenticationUserCases.getAuthCurrentUser.invoke()
        currentUser?.let {
            fireStoreDatabaseUserCases.getUserInformation.invoke(it, object :
                OnGetUserInformationResponse {
                override fun addOnSuccessListener(userDomain: UserDomain) {
                    userDomain.image?.let { url ->
                        fireBaseStorageUserCases.deleteImageUrlFromStorage
                            .invoke(url, object : OnFireBaseStorageListener {
                                override fun addOnSuccessListener(url: String) {
                                    deleteImageFromDataBase(it)
                                }

                                override fun addOnFailureListener(exception: Exception) {
                                    _event.value = Event(UserProfileNavigation.HideLoading)
                                    _event.value =
                                        Event(UserProfileNavigation.Error("There is an error deleting the image from storage"))
                                }
                            })
                    }
                }

                override fun addOnFailureListener(exception: Exception) {
                    _event.value = Event(UserProfileNavigation.HideLoading)
                    _event.value =
                        Event(UserProfileNavigation.Error("There is an error getting the information"))
                }

            })


        }
    }

    private fun deleteImageFromDataBase(id: String) {
        fireStoreDatabaseUserCases.deleteProfileImage.invoke(id, object :
            OnFireStoreCloudListener {
            override fun addOnSuccessListener(state: OnFireStoreCloudResponse) {
                _event.value = Event(UserProfileNavigation.HideLoading)
                _event.value =
                    Event(UserProfileNavigation.ImageDeleted)
            }

            override fun addOnFailureListener(exception: Exception) {
                _event.value = Event(UserProfileNavigation.HideLoading)
                _event.value =
                    Event(UserProfileNavigation.Error("There is an error deleting image from database"))
            }
        })
    }

    fun saveImageInCloudStore(context: Context, file: File) {
        viewModelScope.launch {
            fireBaseStorageUserCases.saveImageInCloudStore.invoke(context, file, object :
                OnFireBaseStorageListener {
                override fun addOnSuccessListener(url: String) {
                    _event.value =
                        Event(
                            UserProfileNavigation.ImageSavedState(
                                ImageCode.SAVEDIMAGE,
                                null
                            )
                        )
                }

                override fun addOnFailureListener(exception: java.lang.Exception) {
                    _event.value =
                        Event(UserProfileNavigation.Error("Imagen not loaded in storage"))
                }
            })
        }
    }

    fun getImageUrl() {
        fireBaseStorageUserCases.getImageUrl.invoke(object : OnFireBaseStorageListener {
            override fun addOnSuccessListener(url: String) {
                _event.value =
                    Event(
                        UserProfileNavigation.ImageSavedState(ImageCode.IMAGEURL, url)
                    )
            }

            override fun addOnFailureListener(exception: java.lang.Exception) {
                _event.value =
                    Event(UserProfileNavigation.Error("Error getting the url"))
            }
        })
    }

    fun updateUserName(name: String) {
        val currentUser = authenticationUserCases.getAuthCurrentUser.invoke()
        currentUser?.let {
            val userDomain = UserDomain(id = it,  nickname = name)
            fireStoreDatabaseUserCases.updateAuthCurrentUser.invoke(
                userDomain, object : OnFireStoreCloudListener {
                    override fun addOnSuccessListener(state: OnFireStoreCloudResponse) {
                        _event.value =
                            Event(UserProfileNavigation.HideLoading)
                        _event.value =
                            Event(UserProfileNavigation.NameSaved)
                    }

                    override fun addOnFailureListener(exception: Exception) {
                        _event.value =
                            Event(UserProfileNavigation.HideLoading)
                        _event.value =
                            Event(UserProfileNavigation.Error("The is an error saving the name information"))
                    }
                }
            )
        }
    }

    fun updateAboutInformation(description: String) {
        val currentUser = authenticationUserCases.getAuthCurrentUser.invoke()
        currentUser?.let {
            val userDomain = UserDomain(id = it,  about = description)
            fireStoreDatabaseUserCases.updateAuthCurrentUser.invoke(
                userDomain, object : OnFireStoreCloudListener {
                    override fun addOnSuccessListener(state: OnFireStoreCloudResponse) {
                        _event.value =
                            Event(UserProfileNavigation.HideLoading)
                        _event.value =
                            Event(UserProfileNavigation.AboutSaved)
                    }

                    override fun addOnFailureListener(exception: Exception) {
                        _event.value =
                            Event(UserProfileNavigation.HideLoading)
                        _event.value =
                            Event(UserProfileNavigation.Error("The is an error saving the about information"))
                    }
                }
            )
        }
    }
}