package bss.intern.planb.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class AgendaEventDaoTest {

    private AgendaEventDao agendaEventDao;
    private AgendaDatabase db;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AgendaDatabase.class).build();
        agendaEventDao = db.agendaEventDao();
        AgendaEvent temp = new AgendaEvent();
        temp.setName("Initiate");
        agendaEventDao.insertAgenda(temp);
    }

    @After
    public void closeDb() throws IOException{
        db.close();
    }

    @Test
    public void insertAgenda() {
        AgendaEvent temp = new AgendaEvent();
        temp.setName("Insert");
        agendaEventDao.insertAgenda(temp);
        AgendaEvent test = agendaEventDao.findById(2);
        assertEquals("Insert", test.getName());
    }

    @Test
    public void getAll(){
        List<AgendaEvent> list = agendaEventDao.getAll();
        assertEquals(false, list.isEmpty());
    }

    @Test
    public void updateAgenda(){
        AgendaEvent temp = new AgendaEvent();
        temp.setId(1);
        temp.setName("Update");
        agendaEventDao.updateAgenda(temp);
        AgendaEvent test = agendaEventDao.findById(1);
        assertEquals("Update", test.getName());
    }

    @Test
    public void deleteAgenda(){
        AgendaEvent temp =  agendaEventDao.findById(1);
        agendaEventDao.deleteAgenda(temp);
        List<AgendaEvent> list = agendaEventDao.getAll();
        assertEquals(true, list.isEmpty());
    }

    @Test
    public void findById(){
        AgendaEvent test = agendaEventDao.findById(1);
        assertEquals("Initiate", test.getName());
    }
}