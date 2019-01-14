import java.util.Date;

public class Performatif {
    private Billet billet;
    private Date deadLine;
    private Action action;

    public Performatif() {
        this.billet = new Billet();
        this.deadLine = new Date();
        this.action = Action.ACTION1;
    }

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
}
