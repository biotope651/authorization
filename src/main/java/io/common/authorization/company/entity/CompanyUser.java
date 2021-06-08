package io.common.authorization.company.entity;

import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.user.entity.User;
import lombok.*;

import javax.persistence.*;

/**
 * 회사 유저 조인 테이블
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CompanyUser extends JpaEntityDateAudity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
