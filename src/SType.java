public class SType {
    private int id;
    private String type;

    public SType(int id, String type){
        this.id = id;
        this.type = type;
    }

    public int getID(){
        return id;
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString(){
        return type;
    }
}
