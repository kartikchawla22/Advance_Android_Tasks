package net.kartikchawla.tasks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase() {
    abstract val taskDao: Tasks_DAO

    companion object {
        @Volatile
        private var INSTANCE: TaskDataBase? = null

        fun getInstance(context: Context): TaskDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, TaskDataBase::class.java, "Tasks_DataBase").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}