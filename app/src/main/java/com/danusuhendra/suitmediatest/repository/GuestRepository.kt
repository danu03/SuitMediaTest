package com.danusuhendra.suitmediatest.repository

import com.danusuhendra.suitmediatest.model.response.UserResponse
import com.danusuhendra.suitmediatest.network.ApiInterface
import com.danusuhendra.suitmediatest.utils.ApiObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class GuestRepository @Inject constructor(private val apiInterface: ApiInterface) {
    private val compositeDisposable = CompositeDisposable()

    fun getGuest(onResult: (UserResponse?) -> Unit, onError : (Throwable)->Unit) {
        apiInterface.guest().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<Response<UserResponse>>(compositeDisposable) {
                override fun onApiSuccess(data: Response<UserResponse>) {
                    if(data.code() == 200) {
                        data.body()?.let {
                            onResult(it)
                        }
                    }
                }

                override fun onApiError(er: Throwable) {
                    onError(er)
                }

            })
    }

    fun onDestroy(){
        compositeDisposable.clear()
    }
}