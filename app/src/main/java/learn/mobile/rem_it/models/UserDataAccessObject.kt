package learn.mobile.rem_it.models;

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.math.abs

class UserDataAccessObject( context:Context )
    : SQLiteOpenHelper( context, DATABASE_NAME,null, DATABASE_VERSION )
{

    companion object{
        private const val DATABASE_NAME: String = "rem_it.db"
        private const val DATABASE_VERSION: Int = 4
        private const val TABLE_NAME: String = "users"
        private const val _ID: String = "_id"
        private const val USER_ID: String = "user_id"
        private const val USER_NAME: String = "user_name"
        private const val USER_EMAIL: String = "user_email"
        private const val USER_PASSWORD: String = "user_password"
//        private const val USER_IS_LOGGED_IN: String = "user_is_logged_in"
    }

    override fun onCreate(db:SQLiteDatabase){
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $USER_ID TEXT UNIQUE NOT NULL,
                $USER_NAME TEXT NOT NULL,
                $USER_EMAIL TEXT NOT NULL,
                $USER_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int){
        if( oldVersion != newVersion ) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        }
        onCreate(db)
    }

    fun insertUser(userName:String, userEmail:String, userPassword: String): Long {
        val db = super.getWritableDatabase();
        val values = ContentValues().apply {
            put(USER_ID, String.format("%08x", abs( userName.hashCode())) ) //REM: TODO .|. Make an alternative
            put(USER_NAME,userName)
            put(USER_EMAIL,userEmail)
            put(USER_PASSWORD,userPassword)
        }
        val id = db.insert( TABLE_NAME,null, values )
        db.close()
        return id
    }

    fun getAllUsers() : List<User> {
        val db = super.getReadableDatabase();
        val cursor=db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        val users=mutableListOf<User>()

        while(cursor.moveToNext()) {

            val _id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
            val user_id = cursor.getString(cursor.getColumnIndexOrThrow(USER_ID))
            val user_name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME))
            val user_email = cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL))

            users.add( User(_id, user_id, user_name, user_email) )
        }

        cursor.close()
        db.close()
        return users
    }

    fun verifyUser( userEmail: String, password: String ): User? {

        val db = super.getReadableDatabase();
        //REM: TODO .|. Oh my....
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $USER_EMAIL = ? AND $USER_PASSWORD = ?",arrayOf(userEmail, password))

        if(cursor.moveToFirst()){
            val user=User(
                cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL))
            )
            cursor.close()
            db.close()
            return user
        }

        cursor.close()
        db.close()
        return null;
    }

    fun getUserByEmail(userEmail : String? ):User?{
        if( userEmail == null ) return null;
        val db = super.getReadableDatabase();
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $USER_EMAIL = ?",arrayOf(userEmail))

        if(cursor.moveToFirst()){
            val user=User(
                cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_EMAIL)),
                cursor.getString(cursor.getColumnIndexOrThrow(USER_PASSWORD))
            )
            cursor.close()
            db.close()
            return user
        }

        cursor.close()
        db.close()
        return null
    }

    fun updateUserById(_id:Int, userName:String, userEmail:String, userPassword:String ):Int{
        val db = super.getWritableDatabase();
        val values = ContentValues().apply{
            put(USER_NAME,userName)
            put(USER_EMAIL,userEmail)
            put(USER_PASSWORD,userPassword)
        }
        val rowsAffected = db.update(TABLE_NAME, values,"$_ID = ?", arrayOf(_id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteUserByEmail(_id:Int):Int{
        val db = super.getWritableDatabase();
        val rowsDeleted=db.delete(TABLE_NAME,"$_ID = ?",arrayOf(_id.toString()))
        db.close()
        return rowsDeleted
    }
}