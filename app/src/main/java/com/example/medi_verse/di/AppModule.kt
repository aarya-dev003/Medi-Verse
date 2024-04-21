package com.example.medi_verse.di

import com.example.medi_verse.RetrofitBuilder.provideApiService
import com.example.medi_verse.RetrofitBuilder.provideMoshi
import com.example.medi_verse.RetrofitBuilder.provideRemoteRepo
import com.example.medi_verse.repository.RemoteRepo
import com.example.medi_verse.utils.SessionManager
import com.example.medi_verse.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { provideMoshi() }
    single { provideApiService(get()) }
    single { SessionManager(get()) }
    single<RemoteRepo> { provideRemoteRepo(get(), get()) }
}
val viewModelModule = module {
    viewModel { (remoteRepo: RemoteRepo) -> UserViewModel(remoteRepo) }
}
