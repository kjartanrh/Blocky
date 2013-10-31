package is.ru.Blocky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 31.10.2013
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class PuzzleMenuAdapter {
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    PuzzleMenuAdapter( Context c ) {
        context = c;
    }

    public PuzzleMenuAdapter openToRead() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public PuzzleMenuAdapter openToWrite() {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertPuzzleSolved( int puzzleId, boolean completed) {
        String[] cols = DBHelper.TablePuzzlesSolvedCol;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer) puzzleId).toString() );
        contentValues.put( cols[2], completed ? "1" : "0" );
        openToWrite();
        long value = db.insert( DBHelper.TablePuzzlesSolved, null, contentValues);
        close();
        return value;
    }

    public long updatePuzzleSolved( int puzzleId, boolean completed ) {
        String[] cols = DBHelper.TablePuzzlesSolvedCol;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer) puzzleId).toString() );
        contentValues.put( cols[2], completed ? "1" : "0" );
        openToWrite();
        long value = db.update( DBHelper.TablePuzzlesSolved,contentValues, cols[1] + "=" + puzzleId, null);
        close();
        return value;
    }

    public Cursor queryPuzzlesSolved() {
        openToRead();
        Cursor cursor = db.query( DBHelper.TablePuzzlesSolved,
                                  DBHelper.TablePuzzlesSolvedCol, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryPuzzleSolved( int puzzleId ) {
        openToRead();
        String[] cols = DBHelper.TablePuzzlesSolvedCol;
        Cursor cursor = db.query( DBHelper.TablePuzzlesSolved,
                cols, cols[1] + "=" + puzzleId, null, null, null, null);
        return cursor;
    }
}
