package nl.bryanderidder.ornaguide.shared.di

import android.content.Context
import androidx.room.Room
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import nl.bryanderidder.ornaguide.achievement.persistence.AchievementRepository
import nl.bryanderidder.ornaguide.achievement.ui.detail.AchievementDetailViewModel
import nl.bryanderidder.ornaguide.achievement.ui.list.AchievementListViewModel
import nl.bryanderidder.ornaguide.characterclass.persistence.CharacterClassRepository
import nl.bryanderidder.ornaguide.characterclass.ui.detail.CharacterClassDetailViewModel
import nl.bryanderidder.ornaguide.characterclass.ui.list.CharacterClassListViewModel
import nl.bryanderidder.ornaguide.item.persistence.ItemAssessRepository
import nl.bryanderidder.ornaguide.item.persistence.ItemRepository
import nl.bryanderidder.ornaguide.item.ui.detail.ItemDetailViewModel
import nl.bryanderidder.ornaguide.item.ui.detail.assess.ItemAssessViewModel
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
import nl.bryanderidder.ornaguide.save.persistence.SaveRepository
import nl.bryanderidder.ornaguide.save.ui.SaveListViewModel
import nl.bryanderidder.ornaguide.save.ui.button.SaveButtonViewModel
import nl.bryanderidder.ornaguide.shared.database.DatabaseMigrations
import nl.bryanderidder.ornaguide.shared.database.OrnaDatabase
import nl.bryanderidder.ornaguide.shared.database.OrnaTypeConverters
import nl.bryanderidder.ornaguide.shared.network.CachingInterceptor
import nl.bryanderidder.ornaguide.shared.network.NetworkLoggingInterceptor
import nl.bryanderidder.ornaguide.shared.network.OrnaClient
import nl.bryanderidder.ornaguide.shared.network.OrnaService
import nl.bryanderidder.ornaguide.shared.ui.menu.search.SearchViewModel
import nl.bryanderidder.ornaguide.shared.ui.menu.sync.SyncViewModel
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
    single { SharedPrefsUtil(get(), get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(NetworkLoggingInterceptor())
            .addInterceptor(CachingInterceptor(get(), get()))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
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

    viewModel { SkillListViewModel(get(), get()) }
    viewModel { SkillDetailViewModel(get(), get()) }
    viewModel { SpecializationListViewModel(get(), get()) }
    viewModel { SpecializationDetailViewModel(get(), get()) }
    viewModel { PetListViewModel(get(), get()) }
    viewModel { PetDetailViewModel(get(), get()) }
    viewModel { ItemListViewModel(get(), get()) }
    viewModel { ItemDetailViewModel(get(), get()) }
    viewModel { ItemAssessViewModel(get(), get()) }
    viewModel { MonsterListViewModel(get(), get()) }
    viewModel { MonsterDetailViewModel(get(), get()) }
    viewModel { NpcListViewModel(get(), get()) }
    viewModel { NpcDetailViewModel(get(), get()) }
    viewModel { AchievementListViewModel(get(), get()) }
    viewModel { AchievementDetailViewModel(get(), get()) }
    viewModel { CharacterClassListViewModel(get(), get()) }
    viewModel { CharacterClassDetailViewModel(get(), get()) }

    viewModel { SaveListViewModel(get()) }
    viewModel { SaveButtonViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SyncViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    // DB:

    single { Moshi.Builder().build() }
    single { OrnaTypeConverters(get()) }

    single {
        Room.databaseBuilder(androidApplication(), OrnaDatabase::class.java, "Orna.db")
            .createFromAsset("databases/Orna.db")
            .addTypeConverter(get<OrnaTypeConverters>())
            .addMigrations(DatabaseMigrations.MIGRATION_2_3)
            .addMigrations(DatabaseMigrations.MIGRATION_3_4)
            .addMigrations(DatabaseMigrations.MIGRATION_4_5)
            .addMigrations(DatabaseMigrations.MIGRATION_5_6)
            .addMigrations(DatabaseMigrations.MIGRATION_6_7)
            .addMigrations(DatabaseMigrations.MIGRATION_7_8)
            .addMigrations(DatabaseMigrations.MIGRATION_8_9)
            .addMigrations(DatabaseMigrations.MIGRATION_9_10)
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
    single { get<OrnaDatabase>().saveDao() }
    single { get<OrnaDatabase>().itemAssessDao() }
    single { CharacterClassRepository(get(), get(), get()) }
    single { SkillRepository(get(), get(), get())}
    single { SpecializationRepository(get(), get(), get())}
    single { PetRepository(get(), get())}
    single { ItemRepository(get(), get(), get()) }
    single { MonsterRepository(get(), get(), get()) }
    single { NpcRepository(get(), get()) }
    single { AchievementRepository(get(), get()) }
    single { SaveRepository(get()) }
    single { ItemAssessRepository(get(), get(), get()) }

}