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
}