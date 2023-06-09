package com.example.board.domain.entity;

import com.example.board.domain.type.RankType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;


@Entity
@Getter
@Setter
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


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdUserId")
    private List<RankUpStandard> rankUpStandards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "createdUserId")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<Comment> comments = new ArrayList<>();
}
