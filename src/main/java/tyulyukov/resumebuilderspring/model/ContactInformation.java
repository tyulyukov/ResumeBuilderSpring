package tyulyukov.resumebuilderspring.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class ContactInformation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String firstName;
  private String lastName;
  private String phone;
  private String email;
  private String country;
  private String city;
  private Date dateOfBirth;

  @OneToOne(mappedBy = "contactInformation")
  private Resume resume;

  @CreationTimestamp
  private Date createdAt;
}
