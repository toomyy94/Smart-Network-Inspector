package tomasr.ua.pt.networkinspector.modules;

/**
 * Created by reytm on 02/11/2016.
 */

public class MarkerInfo {
    private Integer id;
    private Double lat;
    private Double lon;
    private String info;


    public MarkerInfo(Integer id, Double lat, Double lon, String info) {
        super();
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.info = info;
    }

    public Integer getID() {
        return id;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLon() {
        return lon;
    }
    public String getInfo() {
        return info;
    }

    public void setId(Integer id){
        this.id=id;
    }
    public void setLat(Double lat){
        this.lat=lat;
    }
    public void setLon(Double lon){
        this.lon=lon;
    }
    public void setInfo(String info){
        this.info=info;
    }

}
