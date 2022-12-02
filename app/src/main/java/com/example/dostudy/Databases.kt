package com.example.dostudy

import android.content.Context
import androidx.room.*

//@Entity
//data class AModePackageData (
//    @PrimaryKey var packageName: String,
//    var appName: String,
//    var isChecked: Boolean
//) {}
//
//@Dao
//interface AModePackageDataDao {
//    @Insert
//    fun insert(data: AModePackageData)
//
//    @Update
//    fun update(data: AModePackageData)
//
//    @Delete
//    fun delete(data: AModePackageData)
//
//    @Query("SELECT * FROM AModePackageData")
//    fun getAll(): List<AModePackageData>
//
//    @Query("DELETE FROM AModePackageData WHERE packageName = :pkgName")
//    fun deleteDataByPackageName(pkgName: String)
//
//    @Query("SELECT EXISTS(SELECT * FROM AModePackageData WHERE packageName = :pkgName)")
//    fun isPackageExists(pkgName: String): Boolean
//}
//
//@Database(entities = [AModePackageData::class], version = 1)
//abstract class AModeDatabase : RoomDatabase() {
//    abstract fun aModePackageDataDao() : AModePackageDataDao
//
//    companion object {
//        private var instance: AModeDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): AModeDatabase? {
//            if (instance == null) {
//                synchronized(AModeDatabase::class) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AModeDatabase::class.java,
//                        "AMode-Database"
//                    ).build()
//                }
//            }
//            return instance
//        }
//    }
//}

@Entity
data class BModePackageData (
    @PrimaryKey var packageName: String,
    var appName: String,
    var isChecked: Boolean,
    var limitTimeHour: Int,
    var limitTimeMinute: Int,
    var limitTimeSecond: Int
) {}

@Dao
interface BModePackageDataDao {
    @Insert
    fun insert(data: BModePackageData)

    @Update
    fun update(data: BModePackageData)

    @Delete
    fun delete(data: BModePackageData)

    @Query("SELECT * FROM BModePackageData ORDER BY appName")
    fun getAll(): List<BModePackageData>

    @Query("DELETE FROM BModePackageData WHERE packageName = :pkgName")
    fun deleteDataByPackageName(pkgName: String)

    @Query("SELECT EXISTS(SELECT * FROM BModePackageData WHERE packageName = :pkgName)")
    fun isPackageExists(pkgName: String): Boolean
}

@Database(entities = [BModePackageData::class], version = 1)
abstract class BModeDatabase : RoomDatabase() {
    abstract fun bModePackageDataDao() : BModePackageDataDao

    companion object {
        private var instance: BModeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BModeDatabase? {
            if (instance == null) {
                synchronized(BModeDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BModeDatabase::class.java,
                        "BMode-Database"
                    ).build()
                }
            }
            return instance
        }
    }
}



