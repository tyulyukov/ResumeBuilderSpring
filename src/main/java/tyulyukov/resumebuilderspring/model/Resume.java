package tyulyukov.resumebuilderspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Resume {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String description;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contact_information_id")
  private ContactInformation contactInformation;

  @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "resume")
  private Set<Education> educations;

  @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "resume")
  private Set<Skill> skills;

  @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "resume")
  private Set<Language> languages;

  @ManyToOne
  @JoinColumn(name = "user_resume_id")
  @JsonBackReference
  private User user;

  @CreationTimestamp
  private Date createdAt;
}
