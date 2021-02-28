package nl.bryanderidder.ornaguide.shared.di

import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import nl.bryanderidder.ornaguide.MainViewModel
import nl.bryanderidder.ornaguide.characterclass.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.CharacterClassViewModel
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.shared.network.NetworkLoggingInterceptor
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.network.OrnaService
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@JvmField
val appModule: Module = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(NetworkLoggingInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://orna.guide/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

    single { Moshi.Builder().build() }

    single { get<Retrofit>().create(OrnaService::class.java) }

    single { OrnaClient(get()) }

    single { CharacterClassRepository(get()) }

    single { MainViewModel(get()) }

    single { SessionViewModel(get()) }

    single { (sessionVM: SessionViewModel) -> CharacterClassViewModel(get(), sessionVM) }

}