package layoutvisualizer.model.network;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackSection {

    @XmlAttribute(name="id")
    private String id;

    @XmlAttribute(name="type")
    private String type;

    @XmlAttribute(name="length")
    private String length;

    @XmlElement(name="neighbor")
    private List<Neighbor> neighbors;
 
    @XmlTransient
    private Neighbor up = new Neighbor("", "");
    @XmlTransient
    private Neighbor down = new Neighbor("", "");
    @XmlTransient
    private Neighbor stem = new Neighbor("", "");
    @XmlTransient
    private Neighbor plus = new Neighbor("", "");

    @XmlTransient
    private Neighbor minus = new Neighbor("", "");

    @XmlTransient
    private MarkerBoard leftMarker;

    @XmlTransient
    private MarkerBoard rightMarker;

    @XmlTransient
    public boolean punto_di_separazione = false;

    public TrackSection(){
        leftMarker = new MarkerBoard();
        rightMarker = new MarkerBoard();
        neighbors = new ArrayList<Neighbor>();
        type = "";
        id = "";

    }

    public Neighbor getUp() {
        return up;
    }
    public void setUp(Neighbor up) {
        this.up.setRef(up.getRef());
        this.up.setSide(up.getSide());
    }
    public Neighbor getDown() {
        return down;
    }
    public void setDown(Neighbor down) {
        this.down.setRef(down.getRef());
        this.down.setSide(down.getSide());
    }
    public Neighbor getStem() {
        return stem;
    }
    public void setStem(Neighbor stem) {
        this.stem.setRef(stem.getRef());
        this.stem.setSide(stem.getSide());
    }
    public MarkerBoard getLeftMarker() {
        return leftMarker;
    }
    public void setLeftMarker(MarkerBoard marker) {
        this.leftMarker = marker;
    }
    public MarkerBoard getRightMarker() {
        return rightMarker;
    }
    public void setRightMarker(MarkerBoard marker) {
        this.rightMarker = marker;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public List<Neighbor> getNeighbors() {
        return neighbors;
    }
    public void setNeighbors(List<Neighbor> neighbors) {
        this.neighbors = neighbors;
    }

    public Neighbor getPlus() {
        return plus;
    }

    public void setPlus(Neighbor plus) {
        this.plus = plus;
    }

    public Neighbor getMinus() {
        return minus;
    }

    public void setMinus(Neighbor minus) {
        this.minus = minus;
    }

    public void assignNeighbors(){
        for (Neighbor neighbor : this.neighbors) {
            if(neighbor.getSide().equalsIgnoreCase("up")){
                this.setUp(neighbor);
            }else if(neighbor.getSide().equalsIgnoreCase("down")){
                this.setDown(neighbor);
            }
            if(isPoint()){
                if(neighbor.getSide().equalsIgnoreCase("stem")){
                    this.setStem(neighbor);
                }else if(neighbor.getSide().equalsIgnoreCase("plus")){
                    this.setPlus(neighbor);
                }else if(neighbor.getSide().equalsIgnoreCase("minus")){
                    this.setMinus(neighbor);
                }
            }
        }

    }

    public boolean isPoint(){
        if(this.type.equalsIgnoreCase("point")){
            return true;
        }
        return false;
    }

    public static TrackSection deepCopy(TrackSection old) {
        TrackSection newTrack = new TrackSection();
        if(old == null){
            return null;
        }
        newTrack.setId(old.getId());
        newTrack.setType(old.getType());
        newTrack.setLength(old.getLength());
        
        List<Neighbor> ns = new ArrayList<Neighbor>();
        for(Neighbor n : old.getNeighbors()){
            ns.add(Neighbor.deepCopy(n));
        }
     
        newTrack.setUp(Neighbor.deepCopy(old.getUp()));
        newTrack.setDown(Neighbor.deepCopy(old.getDown()));
        newTrack.setPlus(Neighbor.deepCopy(old.getPlus()));
        newTrack.setMinus(Neighbor.deepCopy(old.getMinus()));
        newTrack.setStem(Neighbor.deepCopy(old.getStem()));


        newTrack.setNeighbors(ns);

        newTrack.setLeftMarker(MarkerBoard.deepCopy(old.getLeftMarker()));
        newTrack.setRightMarker(MarkerBoard.deepCopy(old.getRightMarker()));

        newTrack.punto_di_separazione = old.punto_di_separazione;
        return newTrack;
    }

    public String relazioneTrackPunto(TrackSection traccia) {
        String relazione = "";
        if(this.isPoint()){
            if(traccia.getId().equals(this.getPlus().getRef())){
                relazione = "plus";
                return relazione;
            }
            if(traccia.getId().equals(this.getMinus().getRef())){
                relazione = "minus";
                return relazione;
            }
            if(traccia.getId().equals(this.getStem().getRef())){
                relazione = "stem";
                return relazione;
            }
        }
        return relazione;
    }

    public void pulisciTrack(){
        up = null;
        down = null;
        stem = null;
        plus = null;
        minus = null;
        leftMarker = null;
        rightMarker = null;
        neighbors.clear();
    }

}

