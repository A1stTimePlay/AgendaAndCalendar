package bss.intern.planb.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AgendaEvent.class}, version = 1, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static AgendaDatabase INSTANCE;

    public abstract AgendaEventDao agendaEventDao();

    public static AgendaDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AgendaDatabase.class, "agenda-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyINSTANCE(){
        INSTANCE = null;
    }

}
