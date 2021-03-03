package nl.bryanderidder.ornaguide.shared.di

import com.huma.room_for_asset.RoomAsset
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import nl.bryanderidder.ornaguide.MainViewModel
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.achievement.ui.AchievementListViewModel
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.ui.CharacterClassViewModel
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.ItemListViewModel
import nl.bryanderidder.ornaguide.monster.persistence.MonsterRepository
import nl.bryanderidder.ornaguide.monster.ui.MonsterListViewModel
import nl.bryanderidder.ornaguide.npc.persistence.NpcRepository
import nl.bryanderidder.ornaguide.npc.ui.NpcListViewModel
import nl.bryanderidder.ornaguide.pet.persistence.PetRepository
import nl.bryanderidder.ornaguide.pet.ui.PetListViewModel
import nl.bryanderidder.ornaguide.shared.SessionViewModel
import nl.bryanderidder.ornaguide.shared.database.OrnaDatabase
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.NetworkLoggingInterceptor
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.network.OrnaService
import nl.bryanderidder.ornaguide.skill.persistence.SkillRepository
import nl.bryanderidder.ornaguide.skill.ui.SkillListViewModel
import nl.bryanderidder.ornaguide.specialization.persistence.SpecializationRepository
import nl.bryanderidder.ornaguide.specialization.ui.SpecializationListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@JvmField
val appModule: Module = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(NetworkLoggingInterceptor())
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

    single { MainViewModel(get()) }
    single { SessionViewModel(get()) }
    single { SkillListViewModel(get()) }
    single { SpecializationListViewModel(get()) }
    single { PetListViewModel(get()) }
    single { ItemListViewModel(get()) }
    single { MonsterListViewModel(get()) }
    single { NpcListViewModel(get()) }
    single { AchievementListViewModel(get()) }
    single { (sessionVM: SessionViewModel) -> CharacterClassViewModel(get(), sessionVM) }


    // DB:

    single { Moshi.Builder().build() }
    single { OrnaTypeConverters(get()) }

    single {
        RoomAsset.databaseBuilder(androidApplication(), OrnaDatabase::class.java, "Orna.db")
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