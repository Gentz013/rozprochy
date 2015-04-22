package logic;

import java.io.Serializable;

/**
 * Created by Jan on 4/19/2015.
 */
public class Player implements Serializable{
    private String nickname;

    public Player(String nickname){
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return !(nickname != null ? !nickname.equals(player.nickname) : player.nickname != null);

    }

    @Override
    public int hashCode() {
        return nickname != null ? nickname.hashCode() : 0;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}