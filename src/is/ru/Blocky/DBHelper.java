package is.ru.Blocky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 31.10.2013
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "BLOCKY_DB";
    public static final int DB_VERSION = 1;

    public static final String TablePuzzlesSolved = "puzzles_solved";
    public static final String[] TablePuzzlesSolvedCol = {"_id", "puzzleid", "completed"};

    private static final String sqlCreateTablePuzzlesSolved =
            "CREATE TABLE PUZZLES_SOLVED (" +
                    "_id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "puzzleid  INTEGER NOT NULL," +
                    "completed INTEGER" +
                    ");";

    private static final String sqlDropTablePuzzlesSolved =
            "DROP TABLE IF EXISTS PUZZLES_SOLVED;";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( sqlCreateTablePuzzlesSolved);

     for( int i = 1; i <=40; i++) {
            sqLiteDatabase.execSQL("INSERT INTO PUZZLES_SOLVED VALUES (null, " + i + ",0);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        sqLiteDatabase.execSQL( sqlDropTablePuzzlesSolved );
        onCreate(sqLiteDatabase);
    }
}
