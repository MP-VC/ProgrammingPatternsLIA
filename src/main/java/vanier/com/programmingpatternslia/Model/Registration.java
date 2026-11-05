package vanier.com.programmingpatternslia.Model;

public class Registration {
    private int registrationId;
    private int conferenceId;
    private int userId;
    private int waitingList;
    private String status;
    private int waitingListPosition;


    public Registration(int registrationId, int conferenceId, int userId, int waitingList, String status, int waitingListPosition) {
        this.registrationId = registrationId;
        this.conferenceId = conferenceId;
        this.userId = userId;
        this.waitingList = waitingList;
        this.status = status;
        this.waitingListPosition = waitingListPosition;
    }

    public int getRegistrationId() { return registrationId; }
    public int getConferenceId() { return conferenceId; }
    public int getUserId() { return userId; }
    public String getStatus() { return status; }
}