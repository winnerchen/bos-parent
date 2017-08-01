package cn.chen.bos.domain.auth;
// Generated 2017-7-30 17:01:27 by Hibernate Tools 3.2.2.GA


import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Menu generated by hbm2java
 */
@Entity
@Table(name = "auth_menu"
        , catalog = "mavenbos"
        , uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Menu implements java.io.Serializable {


    private String id;
    private Menu menu;
    private String name;
    private String description;
    private String page;
    private String generatemenu;
    private Integer zindex;
    private Set<Menu> menus = new HashSet<Menu>(0);
    private Set<Role> roles = new HashSet<Role>(0);

    public Menu() {
    }

    public Menu(Menu menu, String name, String description, String page, String generatemenu,
                Integer zindex, Set<Menu> menus, Set<Role> roles) {
        this.menu = menu;
        this.name = name;
        this.description = description;
        this.page = page;
        this.generatemenu = generatemenu;
        this.zindex = zindex;
        this.menus = menus;
        this.roles = roles;
    }

    @GenericGenerator(name = "generator", strategy = "uuid")
    @Id
    @GeneratedValue(generator = "generator")

    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "page")
    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Column(name = "generatemenu")
    public String getGeneratemenu() {
        return this.generatemenu;
    }

    public void setGeneratemenu(String generatemenu) {
        this.generatemenu = generatemenu;
    }

    @Column(name = "zindex", precision = 8, scale = 0)
    public Integer getZindex() {
        return this.zindex;
    }

    public void setZindex(Integer zindex) {
        this.zindex = zindex;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    @JSONField(serialize = false)
    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "role_menu", catalog = "mavenbos", joinColumns = {
            @JoinColumn(name = "menu_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
            @JoinColumn(name = "role_id", nullable = false, updatable = false)})
    @JSONField(serialize = false)
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public String getPId() {
        if (menu == null) {
            return "0";
        }
        return menu.getId();
    }


}


