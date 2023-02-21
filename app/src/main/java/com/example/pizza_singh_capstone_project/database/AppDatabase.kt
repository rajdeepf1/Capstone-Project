package com.example.pizza_singh_capstone_project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizza_singh_capstone_project.interfaces.CartDAO
import com.example.pizza_singh_capstone_project.models.CartModel

@Database(entities = [CartModel::class],version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao():CartDAO

    companion object{
        @Volatile
        var instance:AppDatabase?=null
        private const val DATABASE_NAME="User"

        fun getInstance(context: Context):AppDatabase?
        {
            if(instance == null)
            {
                synchronized(AppDatabase::class.java)
                {
                    if(instance == null)
                    {
                        instance= Room.databaseBuilder(context,AppDatabase::class.java,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }

            return instance
        }

    }
}