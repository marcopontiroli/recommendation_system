INSERT INTO genre (id, name) VALUES (1, 'Adventure');
INSERT INTO genre (id, name) VALUES (2, 'Animation');
INSERT INTO genre (id, name) VALUES (3, 'Children');
INSERT INTO genre (id, name) VALUES (4, 'Comedy');
INSERT INTO genre (id, name) VALUES (5, 'Fantasy');
INSERT INTO genre (id, name) VALUES (6, 'Romance');
INSERT INTO genre (id, name) VALUES (7, 'Action');
INSERT INTO genre (id, name) VALUES (8, 'Thriller');
INSERT INTO genre (id, name) VALUES (9, 'Sci-Fi');
INSERT INTO genre (id, name) VALUES (10, 'Drama');
INSERT INTO genre (id, name) VALUES (11, 'Musical');
INSERT INTO genre (id, name) VALUES (12, 'Crime');
INSERT INTO genre (id, name) VALUES (13, 'Biography');


INSERT INTO movie (id, title) VALUES (1, 'Toy Story');
INSERT INTO movie (id, title) VALUES (2, 'Grumpier Old Men');
INSERT INTO movie (id, title) VALUES (3, 'Die Hard');
INSERT INTO movie (id, title) VALUES (4, 'Star Wars: Return of the Jedi');
INSERT INTO movie (id, title) VALUES (5, 'The Lion King');
INSERT INTO movie (id, title) VALUES (6, 'Pulp Fiction');
INSERT INTO movie (id, title) VALUES (7, 'Forrest Gump');
INSERT INTO movie (id, title) VALUES (8, 'The Matrix');
INSERT INTO movie (id, title) VALUES (9, 'Goodfellas');
INSERT INTO movie (id, title) VALUES (10, 'Jurassic Park');


INSERT INTO user (id, username) VALUES (1, 'Alice');
INSERT INTO user (id, username) VALUES (2, 'Bob');
INSERT INTO user (id, username) VALUES (3, 'Charlie');


INSERT INTO movies_genres (movie_id, genre_id) VALUES (1, 1);  
INSERT INTO movies_genres (movie_id, genre_id) VALUES (1, 2);  
INSERT INTO movies_genres (movie_id, genre_id) VALUES (1, 3); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (1, 4);
INSERT INTO movies_genres (movie_id, genre_id) VALUES (1, 5); 

INSERT INTO movies_genres (movie_id, genre_id) VALUES (2, 4);  
INSERT INTO movies_genres (movie_id, genre_id) VALUES (2, 6); 


INSERT INTO movies_genres (movie_id, genre_id) VALUES (3, 7);  
INSERT INTO movies_genres (movie_id, genre_id) VALUES (3, 8);

INSERT INTO movies_genres (movie_id, genre_id) VALUES (4, 7);
INSERT INTO movies_genres (movie_id, genre_id) VALUES (4, 1); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (4, 5); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (4, 9);  

INSERT INTO movies_genres (movie_id, genre_id) VALUES (5, 1);  
INSERT INTO movies_genres (movie_id, genre_id) VALUES (5, 2); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (5, 3);  
INSERT INTO movies_genres (movie_id, genre_id) VALUES (5, 10); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (5, 11); 

INSERT INTO movies_genres (movie_id, genre_id) VALUES (6, 12); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (6, 10); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (6, 8);  

INSERT INTO movies_genres (movie_id, genre_id) VALUES (7, 4); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (7, 10); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (7, 6);  

INSERT INTO movies_genres (movie_id, genre_id) VALUES (8, 7); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (8, 9); 

INSERT INTO movies_genres (movie_id, genre_id) VALUES (9, 13); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (9, 12); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (9, 10);

INSERT INTO movies_genres (movie_id, genre_id) VALUES (10, 1);
INSERT INTO movies_genres (movie_id, genre_id) VALUES (10, 9); 
INSERT INTO movies_genres (movie_id, genre_id) VALUES (10, 8); 

INSERT INTO interaction ( date, type, rating, view_percentage, movie_id, user_id, rating_type, is_current) 
VALUES ( '2024-12-07 10:00:00', 'RATING', 4, 85, 1, 1, 'EXPLICIT', true),
 ( '2024-12-07 11:00:00', 'RATING', 5, NULL, 2, 1, 'EXPLICIT', true),
 ( '2024-12-07 12:00:00', 'VIEW', 5, 90, 1, 2, 'IMPLICIT', true),
 ( '2024-12-07 13:00:00', 'RATING', 3, NULL, 3, 2, 'EXPLICIT', true),
 ( '2024-12-07 14:00:00', 'VIEW', 4, 70, 4, 3, 'IMPLICIT', true),
 ( '2024-12-07 15:00:00', 'RATING', 2, NULL, 5, 3, 'EXPLICIT', true);


