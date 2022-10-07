package tyulyukov.resumebuilderspring.model;

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
  @JoinColumn(name = "contact_information_id", referencedColumnName = "id")
  private ContactInformation contactInformation;

  @OneToMany
  @JoinColumn(name = "user_education_id")
  private Set<Education> educations;

  @OneToMany
  @JoinColumn(name = "user_skill_id")
  private Set<Skill> skills;

  @OneToMany
  @JoinColumn(name = "user_language_id")
  private Set<Language> languages;

  @ManyToOne
  @JoinColumn(name = "user_resume_id")
  private User user;

  @CreationTimestamp
  private Date createdAt;
}
