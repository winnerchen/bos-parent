package cn.chen.bos.domain.city;
// Generated 2017-7-27 15:42:38 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * City generated by hbm2java
 */
@Entity
@Table(name="city"
    ,catalog="mavenbos"
)
public class City implements java.io.Serializable {


     private int id;
     private Integer pid;
     private String name;

    public City() {
    }

	
    public City(int id) {
        this.id = id;
    }
    public City(int id, Integer pid, String name) {
       this.id = id;
       this.pid = pid;
       this.name = name;
    }
   
     @Id 
    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Column(name="pid")
    public Integer getPid() {
        return this.pid;
    }
    
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    
    @Column(name="name", length=10)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


