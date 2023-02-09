package com.javaunit3.springmvc;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Entity
@Table(name = "movies")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name="maturity_rating")
    private String maturityRating;

    @Column(name="genre")
    private String genre;

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addVote(VoteEntity newVote) {
    }

    public VoteEntity[] getVotes() {
    }

    @Configuration
    public class HibernateConfig {
        SessionFactory factory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(MovieEntity.class)
                .buildSessionFactory();
    }

    @Entity
    @Table(name = "votes")
    public class VoteEntity
    {
        @Id
        @GeneratedValue
        private Integer id;

        @Column(name = "voter_Name")
        private String voterName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getVoterName() {
            return voterName;
        }

        public void setVoterName(String voterName) {
            this.voterName = voterName;
        }
    }
}
