insert into tags (name) values ('bezpieczeństwo'), ('hamowanie'), ('technika'), ('żywotność'), ('płynność'), ('korki'), ('miasto');

insert into questions (content) values ('Pytanie numer 1'), ('Pytanie numer 2'), ('Pytanie numer 3');

insert into answers (content, question_id) values ('Odpowiedź numer 1', 1), ('Odpowiedź numer 2', 1), ('Odpowiedź numer 3', 1), ('Odpowiedź numer 4', 2), ('Odpowiedź numer 5', 2), ('Odpowiedź numer 6', 2), ('Odpowiedź numer 7', 3), ('Odpowiedź numer 8', 3), ('Odpowiedź numer 9', 3);

update questions set correct_answer_id = case id when 1 then 2 when 2 then 5 when 3 then 8 else correct_answer_id end where id in (1, 2, 3);

insert into media_content (name, type, data) values ('Image', 'image/png', 'data');

insert into media_content (name, type, data) values ('Video', 'video/mp4', 'data');

insert into advices (name, description, media_content_id, shares, likes) values ('Bezpieczne hamowanie silnikiem', 'Hamowanie silnikiem polega na spowalnianiu samochodu na skutek zwalniania pedału gazu i redukcji biegów. Kiedy nie naciskasz na gaz, dochodzi do zamknięcia zaworu, przez który normalnie dostaje się powietrze. Prowadzi to do powstania efektu zasysania – dokładnie tego samego, który występuje w używanych do sprzątania odkurzaczach. Kiedy przepływ powietrza zostaje ograniczony, spada energia, a jednocześnie powstaje siła hamująca. W konsekwencji samochód zaczyna zwalniać.', 1, 0, 0), ('Zasada jazdy na suwak', 'Przede wszystkim ta metoda jazdy jest stosowana w warunkach znacznego zmniejszenia prędkości na jednym lub kilku pasach. Przepisy mówią również, że gdy jeden pas się kończy lub jest nieprzejezdny, wówczas pojazd, który się na nim znajduje, ma pierwszeństwo. Przy czym muszą być spełnione dwa warunki. Jest to wspomniane już znaczne zmniejszenie prędkości. Drugi warunek dotyczy pojazdu, który znajduje się na pasie umożliwiającym kontynuowanie jazdy. Jeśli ten udzielił pierwszeństwa innemu kierowcy, wówczas nie ma obowiązku wpuszczania kolejnego samochodu', 2, 0,0);

insert into advices_tags values (1, 1), (1, 2), (1, 3), (1, 4), (2, 4), (2, 1), (2, 5);

insert into trainings (advice_id) values (1), (2);

update questions set training_id = 1 where id between 1 and 3;

update questions set training_id = 2 where id between 4 and 6;

insert into roles(name) values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (user_name, password, active) values ('Admin', '$2a$10$H8betvRlG91b.P0INAsfnufN4Wc2WClZN9VvR2XpqvlXbRLTnWLpW', true);

insert into users_roles(user_id, role_id) values (1, 2);

insert into user_params (points, user_id) values (0, 1);

insert into users_advices (advice_id, user_id) values (1,1);

