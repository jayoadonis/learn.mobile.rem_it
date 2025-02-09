package learn.mobile.rem_it.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDataAccessObject( context: Context )
    : SQLiteOpenHelper( context, "rem_it.db", null, 1 )  {

    public companion object {
        private const val _ID = "_id";
        private const val TABLE_NAME = "todos";
        private const val TITLE = "title";
        private const val DESCRIPTION = "description";
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val STMT_CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TITLE TEXT UNIQUE NOT NULL,
                $DESCRIPTION TEXT NOT NULL
            )
        """.trimIndent();

        db?.execSQL( STMT_CREATE_TABLE );
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME");

        this.onCreate(db);
    }


    public fun insert( title:String, description:String ): Long {

        val DB = super.getWritableDatabase();

        val VALUES = ContentValues().apply {
            put(TITLE, title);
            put(DESCRIPTION, description);
        }

        val ID = DB.insert( TABLE_NAME, null, VALUES );

        DB.close();

        return ID;
    }

    public fun retrieve( _id: Int ): Todo? {

        val DB = super.getReadableDatabase();

        val CURSOR = DB.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $_ID = ?", arrayOf<String>(_id.toString())
        );

        if( CURSOR.moveToFirst() ) {
            return Todo(
                CURSOR.getInt( CURSOR.getColumnIndexOrThrow(_ID) ),
                CURSOR.getString( CURSOR.getColumnIndexOrThrow(TITLE) ),
                CURSOR.getString( CURSOR.getColumnIndexOrThrow(DESCRIPTION) )
            )
        }

        CURSOR.close();
        DB.close();

        return null;
    }

    public fun search( title: String ): List<Todo> {

        val DB = super.getReadableDatabase();

        val CURSOR = DB.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $TITLE = ?",
            arrayOf( title )
        );

        val TODOS = mutableListOf<Todo>()

        while( CURSOR.moveToNext() ) {

            TODOS.add(
                Todo(
                    CURSOR.getInt(CURSOR.getColumnIndexOrThrow(_ID)),
                    CURSOR.getString(CURSOR.getColumnIndexOrThrow(TITLE)),
                    CURSOR.getString(CURSOR.getColumnIndexOrThrow(DESCRIPTION))
                )
            )
        }

        CURSOR.close();
        DB.close();

        return TODOS;
    }

    public fun retrieveAll(): List<Todo> {

        val TODOS = mutableListOf<Todo>()
        val DB = super.getReadableDatabase();
        val CURSOR = DB.rawQuery( "SELECT * FROM $TABLE_NAME", null);

        while( CURSOR.moveToNext() ) {
            TODOS.add(
                Todo(
                    CURSOR.getInt( CURSOR.getColumnIndexOrThrow(_ID) ),
                    CURSOR.getString( CURSOR.getColumnIndexOrThrow(TITLE) ),
                    CURSOR.getString( CURSOR.getColumnIndexOrThrow(DESCRIPTION) )
                )
            )
        }

        CURSOR.close();
        DB.close();

        return TODOS;
    }


}