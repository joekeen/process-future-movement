package au.id.keen.pfm.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String recordCode;
    /*private String clientType;
    private String clientNumber;
    private String accountNumber;*/
    private LocalDate expirationDate;
    private String filler;

}
