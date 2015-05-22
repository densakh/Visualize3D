package DataStructures;

import DataTypes.*;

import java.util.ArrayList;


public class DCEL {
    private ArrayList<Vertex> vertexList;
    private ArrayList<HalfEdge> halfEdgeList;
    private ArrayList<Face> faceList;

    public DCEL() {
        this.setVertexList(new ArrayList<Vertex>());
        this.setHalfEdgeList(new ArrayList<HalfEdge>());
        this.setFaceList(new ArrayList<Face>());
    }

    public void setVertexList(ArrayList<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public ArrayList<Vertex> getVertexList() {
        return vertexList;
    }


    public void addVertex(Point2D p) {

        int nbVertex = this.vertexList.size();

        for (int i = 0; i < nbVertex; i++) {
              if ( this.vertexList.get(i).getP().distance(p) == 0 ){
                  return;
              }
        }

        Vertex v = new Vertex(p, vertexList.size());
        vertexList.add(v);
    }

    public void addVertex(int id) {
        Vertex v = new Vertex(id);
        vertexList.add(v);
    }

    public Vertex pointToVertex(Point2D p) {
        int nbVertex = this.vertexList.size();

        for (int i = 0; i < nbVertex; i++) {
            if (this.vertexList.get(i).pointIsVertex(p)) {
                return this.vertexList.get(i);
            }
        }
        return this.vertexList.get(0);
    }

    public void addHalfEdge(HalfEdge halfEdge) {
        this.halfEdgeList.add(halfEdge);
    }

    public void addHalfEdge(int id) {
        HalfEdge he = new HalfEdge(id);
        this.halfEdgeList.add(he);
    }

    public void setHalfEdgeList(ArrayList<HalfEdge> halfEdgeList) {
        this.halfEdgeList = halfEdgeList;
    }

    public ArrayList<HalfEdge> getHalfEdgeList() {
        return halfEdgeList;
    }

    public void addFaceList(Face face) {
        this.faceList.add(face);
    }

    public void addFaceList(int id) {
        Face f = new Face(id);

        if (id == 0)
            f.setIsOuter(true);

        this.faceList.add(f);
    }

    public void setFaceList(ArrayList<Face> faceList) {
        this.faceList = faceList;
    }

    public ArrayList<Face> getFaceList() {
        return faceList;
    }
}