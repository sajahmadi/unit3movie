package com.javaunit3.springmvc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringmvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringmvcApplication.class, args);
    }

    @Controller
    public class MovieController {
        @GetMapping("/voteForBestMovieForm")
        public String voteForBestMovieForm() {
            return "voteForBestMovie";
        }

        @RequestMapping("/voteForBestMovieForm")
        public String voteForBestMovieFormPage(Model model)
        {
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            List<MovieEntity> movieEntityList = session.createQuery("from MovieEntity").list();

            session.getTransaction().commit();

            model.addAttribute("movies", movieEntityList);

            return "voteForBestMovie";
        }

        @RequestMapping("/")
        public String getIndexPage() {
            return "index";
        }

        @RequestMapping("/movies")
        public String showMovies() {
            return "movies";
        }

        @RequestMapping("/voteForBestMovie")
        public String voteForBestMovie(HttpServletRequest request, Model model)
        {
            String movieId = request.getParameter("movieId");
            String voterName = request.getParameter("voterName");

            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            MovieEntity movieEntity = (MovieEntity) session.get(MovieEntity.class, Integer.parseInt(movieId));
            MovieEntity.VoteEntity newVote = new MovieEntity.VoteEntity();
            newVote.setVoterName(voterName);
            movieEntity.addVote(newVote);

            session.update(movieEntity);

            session.getTransaction().commit();

            return "voteForBestMovie";
        }
        @RequestMapping("/bestMovie")
        public String getBestMoviePage(Model model)
        {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            List<MovieEntity> movieEntityList = session.createQuery("from MovieEntity").list();
            movieEntityList.sort(Comparator.comparing(movieEntity -> movieEntity.getVotes().size()));

            MovieEntity movieWithMostVotes = movieEntityList.get(movieEntityList.size() - 1);
            List<String> voterNames = new ArrayList<>();

            for (MovieEntity.VoteEntity vote: movieWithMostVotes.getVotes())
            {
                voterNames.add(vote.getVoterName());
            }

            String voterNamesList = String.join(",", voterNames);

            model.addAttribute("bestMovie", movieWithMostVotes.getTitle());
            model.addAttribute("bestMovieVoters", voterNamesList);

            session.getTransaction().commit();

            return "bestMovie";
        }
    }

    @Configuration
    public class HibernateConfig {

        SessionFactory factory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

    return SessionFactory;
    //what is happening in part 3 step 3^
    }
    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping("/addMovieForm")
    public String addMovieForm() {
        return "addMovie";
    }

}

