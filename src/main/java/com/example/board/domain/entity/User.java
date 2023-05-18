package com.example.board.domain.entity;

import com.example.board.domain.type.RankType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditOverride;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, columnDefinition = "VARCHAR(30) NOT NULL")
    private String loginId;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String userNickName;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String userPassword;

    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    @Enumerated(EnumType.STRING)
    private RankType userRank;
}
