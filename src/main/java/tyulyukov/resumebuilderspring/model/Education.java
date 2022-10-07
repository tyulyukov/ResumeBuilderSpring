package tyulyukov.resumebuilderspring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Education {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String institution;
  private String degree;
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_education_id")
  private Resume resume;

  @CreationTimestamp
  private Date createdAt;
}
