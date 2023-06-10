package pl.coderslab.driver.entity.security;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import pl.coderslab.driver.entity.UserParams;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition="varchar(30)", name = "user_name")
  private String username;

  @Column(columnDefinition="text")
  private String password;

  private Boolean active;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  @ToString.Exclude
  private UserParams userParams;
}
