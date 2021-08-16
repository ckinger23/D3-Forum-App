package hu.ait.android.saaforumapp.data;

import java.io.Serializable;

import hu.ait.android.saaforumapp.R;

public class User implements Serializable {

    public enum UserTeam {
        RHODES(0, R.drawable.rhodes),
        SEWANEE(1, R.drawable.sewanee),
        CENTRE(2, R.drawable.centre),
        BSC(3, R.drawable.bsc),
        HENDRIX(4, R.drawable.hendrix),
        OGLETHORPE(5, R.drawable.oglethorpe),
        MILLSAPS(6, R.drawable.millsaps),
        BERRY(7, R.drawable.berry);

        private int value;
        private int iconId;

        private UserTeam(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static UserTeam fromInt(int value) {
            for (UserTeam s : UserTeam.values()) {
                if (s.value == value) {
                    return s;
                }
            }
            return RHODES;
        }
    }


    private String uid;
    private String author;
    private int team;
    private int position;

    public User(String uid, String author, int team, int position) {
        this.uid = uid;
        this.author = author;
        this.team = team;
        this.position = position;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public UserTeam getUserTeamAsEnum() {
        return UserTeam.fromInt(team);
    }
}
