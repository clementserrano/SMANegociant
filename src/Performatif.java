import java.util.Date;

public class Performatif {
    private Billet billet;
    private Date deadLine;
    private Action action;

    public Billet getBillet() {
        return billet;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Performatif{" +
                "billet=" + billet +
                ", deadLine=" + deadLine +
                ", action=" + action +
                '}';
    }
}
