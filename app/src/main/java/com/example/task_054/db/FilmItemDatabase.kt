package com.example.task_054.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.task_054.models.Film
import com.example.task_054.models.FilmItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [FilmItem::class], version = 1, exportSchema = false)
abstract class FilmItemDatabase: RoomDatabase() {
    abstract fun itemDao(): FilmItemDao

    companion object {
        private var INSTANCE: FilmItemDatabase? = null

        fun getInstance(context: Context, filmList: List<Film>): FilmItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FilmItemDatabase::class.java,
                    "film_item_dtb"
                ).addCallback(object: RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        INSTANCE?.let {database ->
                            CoroutineScope(Dispatchers.IO).launch{
                                filmList.forEach{
                                    if (it.nameOriginal != null)
                                    {
                                        database.itemDao().insert(
                                            FilmItem(0,
                                                it.posterUrlPreview as String,
                                                it.nameOriginal as String,
                                                it.type as String
                                            )
                                        )
                                    }
                                    else if (it.nameEn != null)
                                    {
                                        database.itemDao().insert(
                                            FilmItem(0,
                                                it.posterUrlPreview as String,
                                                it.nameEn as String,
                                                it.type as String
                                            )
                                        )
                                    }
                                    else if (it.nameRu != null)
                                    {
                                        database.itemDao().insert(
                                            FilmItem(0,
                                                it.posterUrlPreview as String,
                                                it.nameRu as String,
                                                it.type as String
                                            )
                                        )
                                    }
                                    else {
                                        database.itemDao().insert(
                                            FilmItem(0,
                                                it.posterUrlPreview as String,
                                                "Кино",
                                                it.type as String
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }).build()

                INSTANCE = instance
                instance
            }
        }
    }
}