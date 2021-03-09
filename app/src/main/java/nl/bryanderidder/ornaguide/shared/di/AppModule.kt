package nl.bryanderidder.ornaguide.shared.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.achievement.ui.detail.AchievementDetailViewModel
import nl.bryanderidder.ornaguide.achievement.ui.list.AchievementListViewModel
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.ui.detail.CharacterClassDetailViewModel
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListViewModel
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailViewModel
import nl.bryanderidder.ornaguide.item.ui.list.ItemListViewModel
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.monster.ui.detail.MonsterDetailViewModel
import nl.bryanderidder.ornaguide.monster.ui.list.MonsterListViewModel
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.npc.ui.detail.NpcDetailViewModel
import nl.bryanderidder.ornaguide.npc.ui.list.NpcListViewModel
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.pet.ui.detail.PetDetailViewModel
import nl.bryanderidder.ornaguide.pet.ui.list.PetListViewModel
import nl.bryanderidder.ornaguide.shared.database.OrnaDatabase
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.CachingInterceptor
import nl.bryanderidder.ornaguide.shared.network.NetworkLoggingInterceptor
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.network.OrnaService
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchViewModel
import nl.bryanderidder.ornaguide.shared.util.SharedPrefsUtil
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.skill.ui.detail.SkillDetailViewModel
import nl.bryanderidder.ornaguide.skill.ui.list.SkillListViewModel
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository
import nl.bryanderidder.ornaguide.specialization.ui.detail.SpecializationDetailViewModel
import nl.bryanderidder.ornaguide.specialization.ui.list.SpecializationListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@JvmField
val appModule: Module = module {

    single { androidContext().getSharedPreferences(androidContext().packageName, Context.MODE_PRIVATE) }
    single { SharedPrefsUtil(get()) }


    single {
        OkHttpClient.Builder()
            .addInterceptor(NetworkLoggingInterceptor())
            .addInterceptor(CachingInterceptor(get(), get()))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
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

    single(named("appContext")) { androidContext() }

    single { get<Retrofit>().create(OrnaService::class.java) }

    single { OrnaClient(get()) }

    viewModel { SkillListViewModel(get()) }
    viewModel { SkillDetailViewModel(get(), get()) }
    viewModel { SpecializationListViewModel(get()) }
    viewModel { SpecializationDetailViewModel(get(), get()) }
    viewModel { PetListViewModel(get()) }
    viewModel { PetDetailViewModel(get(), get()) }
    viewModel { ItemListViewModel(get()) }
    viewModel { ItemDetailViewModel(get(), get()) }
    viewModel { MonsterListViewModel(get()) }
    viewModel { MonsterDetailViewModel(get(), get()) }
    viewModel { NpcListViewModel(get()) }
    viewModel { NpcDetailViewModel(get(), get()) }
    viewModel { AchievementListViewModel(get()) }
    viewModel { AchievementDetailViewModel(get(), get()) }
    viewModel { CharacterClassListViewModel(get()) }
    viewModel { CharacterClassDetailViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    // DB:

    single { Moshi.Builder().build() }
    single { OrnaTypeConverters(get()) }

    single {
        Room.databaseBuilder(androidApplication(), OrnaDatabase::class.java, "Orna.db")
            .createFromAsset("databases/Orna.db")
            .addTypeConverter(get<OrnaTypeConverters>())
            .build()
    }

    single { get<OrnaDatabase>().characterClassDao() }
    single { get<OrnaDatabase>().skillDao() }
    single { get<OrnaDatabase>().specializationDao() }
    single { get<OrnaDatabase>().petDao() }
    single { get<OrnaDatabase>().itemDao() }
    single { get<OrnaDatabase>().monsterDao() }
    single { get<OrnaDatabase>().npcDao() }
    single { get<OrnaDatabase>().achievementDao() }
    single { CharacterClassRepository(get(), get()) }
    single { SkillRepository(get(), get())}
    single { SpecializationRepository(get(), get())}
    single { PetRepository(get(), get())}
    single { ItemRepository(get(), get()) }
    single { MonsterRepository(get(), get()) }
    single { NpcRepository(get(), get()) }
    single { AchievementRepository(get(), get()) }

}