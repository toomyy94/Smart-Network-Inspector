package tomasr.ua.pt.networkinspector.modules;

/**
 * Created by reytm on 02/11/2016.
 */

public class MarkerInfo {
    private Integer id;
    private Double lat;
    private Double lon;
    private String info;
    private String msg_time;


    public MarkerInfo(Integer id, Double lat, Double lon, String info, String msg_time) {
        super();
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.info = info;
        this.msg_time = msg_time;
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
    public String getMsg_time() {
        return msg_time;
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
    public void setMsg_time(String msg_time){
        this.msg_time=msg_time;
    }

}
