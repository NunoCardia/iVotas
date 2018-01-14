package common;

/**
 * Created by cyberfox21 on 22/10/17.
 */
public class Sinal {

    private boolean status;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Sinal(boolean s){
        this.status = s;
    }

    public void setStatus(boolean s){
        this.status = s;
    }

    public boolean getStatus(){
        return this.status;
    }

}
