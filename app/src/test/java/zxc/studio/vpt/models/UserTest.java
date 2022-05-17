package zxc.studio.vpt.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import zxc.studio.vpt.utilities.DateFunctions;

public class UserTest {

    private User testUser;
    private String club = "N/A";
    private String coach = "Q1RSVORJV4ONRbGg9btr6Lpg4p43";
    private String email = "fakeuseremail@gmail.com";
    private String firebase_id = "36dgjtV1DRYznTh74zbI3ZxIeCE3";
    private String firstName = "Byron";
    private String id = "36dgjtV1DRYznTh74zbI3ZxIeCE3";
    private String lastName = "McGregor";
    private String sex = "Male";
    private Date signup = DateFunctions.getCurrentDateAndTime();
    private String clubReplacement = "Q1RSVORJV4ONRbGg9btr6Lpg4p43";
    private String coachReplacement = "36dgjtV1DRYznTh74zbI3ZxIeCE3";
    private String emailReplacement = "replacementemail@gmail.com";
    private String firebase_idReplacement = "36dgjtV1DRYznTh74zbI3ZxIeCCC";
    private String firstNameReplacement = "Big";
    private String idReplacement = "36dXjtV1DRYznTh74zbI3ZxXXXE3";
    private String lastNameReplacement = "Dog";
    private String sexReplacement = "Female";
    private Date signupReplacement = DateFunctions.getCurrentDateAndTime();

    @Before
    public void setUp() {
        testUser = new User(club,coach,email,firebase_id,firstName,id,lastName,sex,signup);
    }


    @Test
    public void canGetValues(){
        get_club();
        get_coach();
        get_email();
        get_firebaseID();
        get_firstName();
        get_id();
        get_lastName();
        get_sex();
        get_signup();
    }

    public void get_club(){
        String clubGetterValue = testUser.getUser_club();
        assertTrue(clubGetterValue != null);
        assertTrue(clubGetterValue == club);
    }
    public void get_coach(){
        String coachGetterValue = testUser.getUser_coach();
        assertTrue(coachGetterValue != null);
        assertTrue(coachGetterValue == coach);
    }
    public void get_email(){
        String emailGetterValue = testUser.getUser_email();
        assertTrue(emailGetterValue != null);
        assertTrue(emailGetterValue == email);
    }
    public void get_firebaseID(){
        String firebaseIdGetterValue = testUser.getUser_firebase_id();
        assertTrue(firebaseIdGetterValue != null);
        assertTrue(firebaseIdGetterValue == firebase_id);
    }
    public void get_firstName(){
        String firstNameGetterValue = testUser.getUser_firstName();
        assertTrue(firstNameGetterValue != null);
        assertTrue(firstNameGetterValue == firstName);
    }
    public void get_id(){
        String idGetterValue = testUser.getUser_id();
        assertTrue(idGetterValue != null);
        assertTrue(idGetterValue == id);
    }
    public void get_lastName(){
        String lastNameGetterValue = testUser.getUser_lastName();
        assertTrue(lastNameGetterValue != null);
        assertTrue(lastNameGetterValue == lastName);
    }
    public void get_sex(){
        String sexGetterValue = testUser.getUser_sex();
        assertTrue(sexGetterValue != null);
        assertTrue(sexGetterValue == sex);
    }
    public void get_signup(){
        Date signupGetterValue = testUser.getUser_signup_date();
        assertTrue(signupGetterValue != null);
        assertTrue(signupGetterValue == signup);
    }

    //setters
    @Test
    public void canSetValues(){
        set_club();
        set_coach();
        set_email();
        set_firebaseID();
        set_id();
        set_firstName();
        set_lastName();
        set_sex();
        set_signUp();
    }

    public void set_club (){
        testUser.setUser_club(clubReplacement);
        String userClub = testUser.getUser_club();
        assertTrue(userClub != null);
        assertTrue(userClub != club);
        assertTrue(userClub == clubReplacement);
    }
    public void set_coach (){
        testUser.setUser_coach(coachReplacement);
        String userCoach = testUser.getUser_coach();
        assertTrue(userCoach != null);
        assertTrue(userCoach != coach);
        assertTrue(userCoach == coachReplacement);
    }
    public void set_email (){
        testUser.setUser_email(emailReplacement);
        String userEmail = testUser.getUser_email();
        assertTrue(userEmail != null);
        assertTrue(userEmail != email);
        assertTrue(userEmail == emailReplacement);
    }
    public void set_firebaseID (){
        testUser.setUser_firebase_id(firebase_idReplacement);
        String userFirebase = testUser.getUser_firebase_id();
        assertTrue(userFirebase != null);
        assertTrue(userFirebase != firebase_id);
        assertTrue(userFirebase == firebase_idReplacement);
    }
    public void set_firstName (){
        testUser.setUser_firstName(firstNameReplacement);
        String userFirst = testUser.getUser_firstName();
        assertTrue(userFirst != null);
        assertTrue(userFirst != firstName);
        assertTrue(userFirst == firstNameReplacement);
    }
    public void set_id (){
        testUser.setUser_id(idReplacement);
        String userID = testUser.getUser_id();
        assertTrue(userID != null);
        assertTrue(userID != id);
        assertTrue(userID == idReplacement);
    }
    public void set_lastName (){
        testUser.setUser_lastName(lastNameReplacement);
        String userLast = testUser.getUser_lastName();
        assertTrue(userLast != null);
        assertTrue(userLast != lastName);
        assertTrue(userLast == lastNameReplacement);
    }
    public void set_sex (){
        testUser.setUser_sex(sexReplacement);
        String userSex = testUser.getUser_sex();
        assertTrue(userSex != null);
        assertTrue(userSex != sex);
        assertTrue(userSex == sexReplacement);
    }
    public void set_signUp (){
        testUser.setUser_signup_date(signupReplacement);
        Date userSign = testUser.getUser_signup_date();
        assertTrue(userSign != null);
        assertTrue(userSign != signup);
        assertTrue(userSign == signupReplacement);
    }

}