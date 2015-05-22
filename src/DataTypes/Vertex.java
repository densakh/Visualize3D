package DataTypes;

/**
 * Created by emxot_000 on 22.05.2015.
 */

public class Vertex {
    private Point2D p;
    private int id;
    private HalfEdge halfEdge;

    public Vertex(Point2D p, int id) {
        this.setP(p);
        this.setId(id);
    }

    public Vertex(int id){
        this.setId(id);
    }

    public void setP(Point2D p) {
        this.p = p;
    }

    public Point2D getP() {
        return p;
    }

    public boolean pointIsVertex(Point2D p) {
        return (p.distance(this.p) == 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHalfEdge(HalfEdge halfEdge) {
        this.halfEdge = halfEdge;
    }

    public HalfEdge getHalfEdge() {
        return halfEdge;
    }

    public double getAngle(Vertex v1, Vertex v2){
        double x, y, x1, x2, y1, y2;

        x = this.p.getX();
        y = this.p.getY();

        x1 = v1.getP().getX();
        y1 = v1.getP().getY();

        x2 = v2.getP().getX();
        y2 = v2.getP().getY();
                /*
                double a, b, c, cos;

                a = Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
                b = Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
                c = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

                cos = (a*a+b*b-c*c)/(2*a*b);

                return Math.acos(cos);
                */
        double theta1, theta2, theta;

        theta1 = Math.atan2(x-x1, y-y1);
        theta2 = Math.atan2(x-x2, y-y2);
        theta = theta2 - theta1;

        while ( theta < 0 ){
            theta = theta + 2*3.141592646952213;
        }
        while ( theta > 2*3.141592646952213 ){
            theta = theta - 2*3.141592646952213;
        }

        return theta;
    }

    public boolean crossHorizontal(HalfEdge heTmp) {

        Point2D p1, p2, p;
        p1 = heTmp.getOrigin().getP();
        p2 = heTmp.getTwin().getOrigin().getP();
        p = this.getP();

        boolean isBetween = (p1.getY() > p.getY()) && (p.getY() > p2.getY()) || (p1.getY() < p.getY()) && (p.getY() < p2.getY());
        boolean isAtLeft = (p.getX() > p1.getX()) || (p.getX() > p2.getX());

        return ( isBetween && isAtLeft );
    }

    public double horizontalDistance(HalfEdge heTmp) {
        Point2D p1, p2, p;
        double scale, newX;

        p = this.getP();
        p1 = heTmp.getOrigin().getP();
        p2 = heTmp.getTwin().getOrigin().getP();

        scale = (p.getY()-p1.getY())/(p2.getY()-p1.getY());
        newX = scale*(p2.getX()-p1.getX())+p1.getX();

        return p.getX()-newX;
    }

}