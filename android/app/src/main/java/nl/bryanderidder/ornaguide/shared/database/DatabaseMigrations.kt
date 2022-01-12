package nl.bryanderidder.ornaguide.shared.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    // DB Migration version 2 to 3
    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Specialization ADD COLUMN price TEXT DEFAULT '' NOT NULL")
        }
    }
    // DB Migration version 3 to 4
    val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Item ADD COLUMN crit INTEGER DEFAULT 0 NOT NULL")
            database.execSQL("ALTER TABLE Item ADD COLUMN gives TEXT DEFAULT '' NOT NULL")
            database.execSQL("ALTER TABLE Item ADD COLUMN immunities TEXT DEFAULT '' NOT NULL")
        }
    }
    // DB Migration version 4 to 5
    val MIGRATION_4_5: Migration = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Monster ADD COLUMN immuneToStatus TEXT DEFAULT '' NOT NULL")
            database.execSQL("ALTER TABLE Monster ADD COLUMN vulnerableToStatus TEXT DEFAULT '' NOT NULL")
        }
    }
    // DB Migration version 5 to 6
    val MIGRATION_5_6: Migration = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("UPDATE Save SET type = UPPER(SUBSTR(type, 1, 1)) || SUBSTR(type, 2)")
        }
    }
    // DB Migration version 6 to 7
    val MIGRATION_6_7: Migration = object : Migration(6, 7) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Skill ADD COLUMN cures TEXT DEFAULT '' NOT NULL")
            database.execSQL("ALTER TABLE Skill ADD COLUMN buffedBy TEXT DEFAULT '' NOT NULL")
            database.execSQL("ALTER TABLE Item ADD COLUMN cures TEXT DEFAULT '' NOT NULL")
            database.execSQL("ALTER TABLE Item ADD COLUMN causes TEXT DEFAULT '' NOT NULL")
            database.execSQL("ALTER TABLE Item ADD COLUMN viewDistance INTEGER DEFAULT 0 NOT NULL")
        }
    }
    // DB Migration version 7 to 8
    val MIGRATION_7_8: Migration = object : Migration(7, 8) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `ItemAssess` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `itemId` INTEGER NOT NULL, `name` TEXT NOT NULL, `quality` TEXT NOT NULL, `requestBody` TEXT NOT NULL, `creationDate` INTEGER NOT NULL, `attackBase` INTEGER NOT NULL, `attackValues` TEXT NOT NULL, `defenseBase` INTEGER NOT NULL, `defenseValues` TEXT NOT NULL, `dexterityBase` INTEGER NOT NULL, `dexterityValues` TEXT NOT NULL, `hpBase` INTEGER NOT NULL, `hpValues` TEXT NOT NULL, `magicBase` INTEGER NOT NULL, `magicValues` TEXT NOT NULL, `manaBase` INTEGER NOT NULL, `manaValues` TEXT NOT NULL, `resistanceBase` INTEGER NOT NULL, `resistanceValues` TEXT NOT NULL, `wardBase` INTEGER NOT NULL, `wardValues` TEXT NOT NULL, `critBase` INTEGER NOT NULL, `critValues` TEXT NOT NULL)")
        }
    }
}