package DataTypes;

/**
 * Created by emxot_000 on 22.05.2015.
 */
import java.util.ArrayList;

public class Face {
    private int id;
    private HalfEdge outerComponent;
    private ArrayList<HalfEdge> innerComponent;
    private boolean isOuter;
    private Face outerFace;
    private HalfEdge outerHalfEdge;


    public Face(HalfEdge outer, int id){
        this(id);
        this.setOuterComponent(outer);
        this.setInnerComponent(new ArrayList<HalfEdge>());
    }

    public Face(int id){
        this.setId(id);
        this.setIsOuter(false);
        this.setInnerComponent(new ArrayList<HalfEdge>());
        this.setOuterComponent(null);
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public void setOuterComponent(HalfEdge outerComponent) {
        this.outerComponent = outerComponent;
    }


    public HalfEdge getOuterComponent() {
        return outerComponent;
    }


    public void setInnerComponent(ArrayList<HalfEdge> innerComponent) {
        this.innerComponent = innerComponent;
    }


    public ArrayList<HalfEdge> getInnerComponent() {
        return innerComponent;
    }

    public void addInnerComponent(HalfEdge he) {
        this.innerComponent.add(he);
    }

    public void setIsOuter(boolean isOuter) {
        this.isOuter = isOuter;
    }

    public boolean getIsOuter() {
        return isOuter;
    }

    public void setOuterFace(Face outerFace) {
        this.outerFace = outerFace;
    }

    public Face getOuterFace() {
        return outerFace;
    }

    public void setOuterHalfEdge(HalfEdge outerHalfEdge) {
        this.outerHalfEdge = outerHalfEdge;
    }

    public HalfEdge getOuterHalfEdge() {
        return outerHalfEdge;
    }

    public void analyseFace(){
        Vertex v;
        HalfEdge he, heTmp;
        double angle;

        heTmp = this.outerComponent.getNext();
        he = this.outerComponent;
        v = he.getOrigin();

        while ( !(heTmp.equals(this.outerComponent)) ){
            if ( v.getP().getX() > heTmp.getOrigin().getP().getX() ){
                he = heTmp;
                v = he.getOrigin();
            }
            heTmp = heTmp.getNext();
        }

        angle = v.getAngle(he.getNext().getOrigin(), he.getPrev().getOrigin());

        if ( angle == 0 || angle > 3.141592646952213 )
            this.setIsOuter(true);
        this.setOuterHalfEdge(he);

    }

}