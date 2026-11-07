import org.junit.jupiter.api.Test;
import vanier.com.programmingpatternslia.DataBase.Conference.ConferenceDAO;
import vanier.com.programmingpatternslia.Model.Conference;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ConferenceDAOTest {

    @Test
    public void testAddValidConference(){
        ConferenceDAO cd = new ConferenceDAO();
        Conference c = new Conference("Computer Science Conference", 3, "2026-12-24", "11:05", "12:15", "Vanier College Auditorium");

        cd.addConference(c);

        assertNotNull(c, "Conference object should be initialized");
    }

    @Test
    public void testRetrieveAll(){

        ConferenceDAO cd = new ConferenceDAO();
        Conference c = new Conference("Modern Art Conference", 2, "2026-12-17", "19:05", "21:15", null);

        ArrayList<Conference> conferences = cd.getAllConferences();

        assertFalse(conferences.isEmpty(), "The list should contain items");

    }

    @Test
    public void testRetriveID(){
        ConferenceDAO cd = new ConferenceDAO();
        Conference c = new Conference("Medieval History Conference", 2, "2026-12-17", "10:05", "12:15", "Downtown Museum");
        cd.addConference(c);

        int id = cd.retrieveID("Medieval History Conference");

        assertTrue(id > 0, "The ID should be a valid positive number");
    }

    @Test
    public void testUpdateConference(){
        ConferenceDAO cd = new ConferenceDAO();
        Conference c = new Conference("Archeology Conference", 3, "2026", "12:45", "14:15", null);

        cd.addConference(c);

        int id = cd.retrieveID("Archeology Conference");

        cd.updateConference(id, "End_Time", "13:15");

        Conference updated = cd.findByID(id);

        assertEquals("13:15", updated.getEnd_time());
    }

    @Test
    public void testFindById(){
        ConferenceDAO cd = new ConferenceDAO();

        Conference c = new Conference(7,"Computer Science Conference",	3,	"2026-12-24", "11:05", 	"12:15", "Vanier College Auditorium");
        Conference found = cd.findByID(7);

        assertEquals(c.toString(), found.toString(), "The two conferences should match.");

    }

    @Test
    public void testRemoveConference(){
        ConferenceDAO cd  = new ConferenceDAO();
        int id = 13;

        cd.removeConference(id);

        Conference deleted = cd.findByID(id);

        assertNull(deleted, "This conference shouldn't exist");
    }
}
