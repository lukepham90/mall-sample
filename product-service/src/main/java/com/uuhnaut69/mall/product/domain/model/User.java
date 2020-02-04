package com.uuhnaut69.mall.product.domain.model;

import com.uuhnaut69.mall.product.domain.enums.RoleName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author uuhnaut
 * @project mall
 */
@Data
@Entity
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@ApiModel(value = "User Model", description = "User's info")
public class User extends AbstractEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value = "User's id")
    private UUID id;

    @Column(nullable = false)
    @Size(min = 3, max = 50)
    @ApiModelProperty(value = "User's name")
    private String name;

    @ApiModelProperty(value = "User's username")
    @Column(nullable = false, unique = true, updatable = false)
    @Size(min = 3, max = 50)
    private String username;

    @NaturalId
    @Size(max = 50)
    @Email
    @Column(nullable = false, unique = true, updatable = false)
    @ApiModelProperty(value = "User's email")
    private String email;

    @ApiModelProperty(value = "User's password")
    @Column(nullable = false)
    @Size(min = 6, max = 100)
    private String password;

    @ApiModelProperty(value = "User's status active or not")
    @Column(nullable = false)
    private boolean isEnabled = false;

    @ApiModelProperty(value = "User's role")
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @ApiModelProperty(value = "User's avatar")
    private String avatar = "https://cdn.steemitimages.com/DQmbQGsqqhgTgZK2Wh4o3o9pALrNqPVryT3AH17J4WExoqS/no-image-available-grid.jpg";

    @ApiModelProperty(value = "User's stripe customer id")
    private String customerStripeId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_product", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tag", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
