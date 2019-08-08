package bss.intern.planb.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface AgendaEventDao {

    @Query("Select * from AgendaEvent")
    List<AgendaEvent> getAll();

    @Insert(onConflict = IGNORE)
    void insertAgenda(AgendaEvent agendaEvent);

    @Query("Select * from AgendaEvent where id like :id")
    AgendaEvent findById(int id);

//    @Query("Update AgendaEvent set ")

    @Delete
    void deleteAgenda(AgendaEvent agendaEvent);
}
