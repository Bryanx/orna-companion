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
        }
    }
}