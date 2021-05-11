package hellojpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MBR")
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    // 기본키 생성을 데이터베이스에 위임 - mysql
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // 오라클에서 사용
//    @SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")

    // table 을 사용해서 키 관리하는 전략 - 장점 모든 디비에 다 쓸수 있다. 단점 성능
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate testDate;
    private LocalDateTime testDateTime;

    @Lob
    private String description;

    @Transient
    private int temp;
}
