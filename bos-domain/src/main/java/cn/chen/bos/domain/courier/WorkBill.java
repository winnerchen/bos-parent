package cn.chen.bos.domain.courier;
// Generated 2017-7-27 15:37:16 by Hibernate Tools 3.2.2.GA


import cn.chen.bos.domain.bc.Staff;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * WorkBill generated by hbm2java
 */
@Entity
@Table(name="qp_workbill"
    ,catalog="mavenbos"
)
public class WorkBill  implements java.io.Serializable {


     private String id;
     private NoticeBill noticeBill;
     private Staff staff;
     private String type;
     private String pickstate;
     private Date buildtime;
     private Integer attachbilltimes;
     private String remark;

    public WorkBill() {
    }


    public WorkBill(Date buildtime) {
        this.buildtime = buildtime;
    }
    public WorkBill(NoticeBill noticeBill, Staff staff, String type, String pickstate, Date buildtime, Integer attachbilltimes, String remark) {
       this.noticeBill = noticeBill;
       this.staff = staff;
       this.type = type;
       this.pickstate = pickstate;
       this.buildtime = buildtime;
       this.attachbilltimes = attachbilltimes;
       this.remark = remark;
    }

     @GenericGenerator(name="generator", strategy="uuid")@Id @GeneratedValue(generator="generator")

    @Column(name="ID", unique=true, nullable=false, length=32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="NOTICEBILL_ID")
    public NoticeBill getNoticeBill() {
        return this.noticeBill;
    }

    public void setNoticeBill(NoticeBill noticeBill) {
        this.noticeBill = noticeBill;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STAFF_ID")
    public Staff getStaff() {
        return this.staff;
    }
    
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    @Column(name="TYPE", length=20)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="PICKSTATE", length=20)
    public String getPickstate() {
        return this.pickstate;
    }
    
    public void setPickstate(String pickstate) {
        this.pickstate = pickstate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="BUILDTIME", nullable=false, length=0)
    public Date getBuildtime() {
        return this.buildtime;
    }
    
    public void setBuildtime(Date buildtime) {
        this.buildtime = buildtime;
    }
    
    @Column(name="ATTACHBILLTIMES", precision=8, scale=0)
    public Integer getAttachbilltimes() {
        return this.attachbilltimes;
    }
    
    public void setAttachbilltimes(Integer attachbilltimes) {
        this.attachbilltimes = attachbilltimes;
    }
    
    @Column(name="REMARK")
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }




}

