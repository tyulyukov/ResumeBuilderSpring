package tyulyukov.resumebuilderspring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Language {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String level;

  @ManyToOne
  @JoinColumn(name = "user_language_id")
  private Resume resume;

  @CreationTimestamp
  private Date createdAt;
}
