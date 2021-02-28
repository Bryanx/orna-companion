package nl.bryanderidder.ornaguide.shared.di

import androidx.room.Room
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import nl.bryanderidder.ornaguide.MainViewModel
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassViewModel
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.shared.database.OrnaDatabase
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.NetworkLoggingInterceptor
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.network.OrnaService
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.skill.ui.SkillListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
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

    single { get<Retrofit>().create(OrnaService::class.java) }

    single { OrnaClient(get()) }

    single { MainViewModel(get()) }
    single { SessionViewModel(get()) }
    single { SkillListViewModel(get()) }
    single { (sessionVM: SessionViewModel) -> CharacterClassViewModel(get(), sessionVM) }


    // DB:

    single { Moshi.Builder().build() }
    single { OrnaTypeConverters(get()) }

    single {
        Room.databaseBuilder(androidApplication(), OrnaDatabase::class.java, "Orna.db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(get<OrnaTypeConverters>())
            .build()
    }

    single { get<OrnaDatabase>().characterClassDao() }
    single { get<OrnaDatabase>().skillDao() }
    single { CharacterClassRepository(get(), get()) }
    single { SkillRepository(get(), get())}

}