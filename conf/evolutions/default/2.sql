# --- Sample dataset

# --- !Ups

INSERT INTO `service` (`service_name` ) VALUES ('Ventes Entreprises');
INSERT INTO `service` (`service_name`) VALUES ('Renault Net');
INSERT INTO `service` (`service_name`) VALUES ('Renault FR');
INSERT INTO `service` (`service_name`) VALUES ('Renault Web Services');
INSERT INTO `service` (`service_name`,`hidden`) VALUES ('Renault Cars','1');


INSERT INTO `application` (`app_name`, `app_description`, `service_id_service`) VALUES ('B2B', 'Site Internet vente des VO', '1');
INSERT INTO `application` (`app_name`, `app_description`, `service_id_service`) VALUES ('Fleet Manager', 'Gestion des flottes Renault', '1');
INSERT INTO `application` (`app_name`, `app_description`, `service_id_service`) VALUES ('Tomcat', 'Server Web Renault', '3');
INSERT INTO `application` (`app_name`, `app_description`, `service_id_service`) VALUES ('Renault Clio 4', 'Voiture Diesel', '4');
INSERT INTO `application` (`app_name`, `app_description`, `service_id_service`) VALUES ('Renault Zoe', 'Voiture Electrique', '4');
INSERT INTO `application` (`app_name`, `app_description`, `service_id_service`) VALUES ('Promo Renault', 'Des promotion pendant le solde d\'été', '3');

INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('nhatminh', '1234', '1000', '5000', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'NGUYEN', 'Nhat Minh', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('toto', '1234', '200', '544', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'NGUYEN', 'Toto', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('patrick', '1234', '300', '876', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'Messer', 'Patrick', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('roger', '1234', '423', '454', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'Likeng', 'Roger', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('veronique', '1234', '342', '947', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'Doubin', 'Véronique', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('frederic', '1234', '384', '534', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'Lesagge', 'Frédéric', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('christine', '1234', '985', '234', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'Miterrand', 'Christine', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');
INSERT INTO `user` (`user_name`, `password`, `bonus`, `exp`, `email`, `title`, `post_count`, `first_name`, `last_name`, `address`, `home_phone`, `mobile_phone`, `id_service_subscribe`, `postal_code`, `city`, `country`, `civilite`, `role`, `img_url`, `signature`, `service_id_service`, `thread_count_views`) VALUES ('skander', '1234', '432', '564', 'nhatminh1809@gmail.com', 'Chevalier', '232', 'Jemli', 'Skander', '3 rue de l\'Yerres', '423423', '43243', '1', '94190', 'Villeneuve Saint Georges', 'France', '0', 'Admin', 'fsdfqsd', 'nhatminh skander', '1', '0');



INSERT INTO `gift` (`name`, `bonus`, `img_url`, `category`) VALUES ('Iphone', '200', 'raezraz', '1');
INSERT INTO `gift` (`name`, `bonus`, `img_url`, `category`) VALUES ('Ipad', '300', 'fdsdsf', '1');
INSERT INTO `gift` (`name`, `bonus`, `img_url`, `category`) VALUES ('Voyage Madrid', '500', 'fsdfd', '2');
INSERT INTO `gift` (`name`, `bonus`, `img_url`, `category`) VALUES ('Voyage Viet Nam', '1000', 'fdsf', '2');
INSERT INTO `gift` (`name`, `bonus`, `category`) VALUES ('Télévision', '200', '1');




INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('patrick', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('christine', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('veronique', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('frederic', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('patrick', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('roger', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('patrick', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('patrick', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');
INSERT INTO `message` (`user_name_from_user_name`, `user_name_to`, `content`) VALUES ('patrick', 'nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod');


INSERT INTO `notification` (`user_user_name`, `content`) VALUES ('nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatibus, dignissimos, nobis beatae aliquam pariatur perferendis iure velit sint explicabo dolore quo reprehenderit quidem qui nisi nostrum eligendi dicta nulla soluta.');
INSERT INTO `notification` (`user_user_name`, `content`) VALUES ('nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatibus, dignissimos, nobis beatae aliquam pariatur perferendis iure velit sint explicabo dolore quo reprehenderit quidem qui nisi nostrum eligendi dicta nulla soluta.');
INSERT INTO `notification` (`user_user_name`, `content`) VALUES ('nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatibus, dignissimos, nobis beatae aliquam pariatur perferendis iure velit sint explicabo dolore quo reprehenderit quidem qui nisi nostrum eligendi dicta nulla soluta.');
INSERT INTO `notification` (`user_user_name`, `content`) VALUES ('nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatibus, dignissimos, nobis beatae aliquam pariatur perferendis iure velit sint explicabo dolore quo reprehenderit quidem qui nisi nostrum eligendi dicta nulla soluta.');
INSERT INTO `notification` (`user_user_name`, `content`) VALUES ('nhatminh', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Voluptatibus, dignissimos, nobis beatae aliquam pariatur perferendis iure velit sint explicabo dolore quo reprehenderit quidem qui nisi nostrum eligendi dicta nulla soluta.');

INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '2', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '2', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'patrick', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '2', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'veronique', '2');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '2', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'frederic', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'christine', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '2');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '3');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '4', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'roger', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '5', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'christine', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '1');
INSERT INTO `thread` (`thread_name`, `thread_type`, `rating`, `view_count`, `response_count`, `like_count`, `dislike_count`, `favorite_count`, `content`, `author_user_name`, `application_id_app`) VALUES ('Refonte du B2B', 'Business to Business', '3', '0', '0', '0', '0', '0', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quae, commodi, obcaecati, sapiente earum quos suscipit asperiores at non consequuntur soluta aut nulla reprehenderit iste ullam amet dolor harum eaque unde!lorem Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dicta, rem laudantium laborum ducimus ullam in expedita! Voluptatibus, cum, explicabo iure totam nulla aliquid magni consectetur culpa doloremque pariatur soluta nihil?', 'nhatminh', '1');

INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-20 14:54:06', 'patrick', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-21 14:54:06', 'nhatminh', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-22 14:54:06', 'veronique', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-23 14:54:06', 'roger', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-24 14:54:06', 'frederic', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-25 14:54:06', 'christine', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-26 14:54:06', 'patrick', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-07-27 14:54:06', 'nhatminh', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-05-28 14:54:06', 'nhatminh', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-06-28 14:54:06', 'veronique', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-01-28 14:54:06', 'patrick', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-02-28 14:54:06', 'patrick', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2014-03-28 14:54:06', 'patrick', '1');
INSERT INTO `post` (`post_content`, `post_time`, `user_user_name`, `thread_id_thread`) VALUES ('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod', '2013-07-28 14:54:06', 'patrick', '2');



INSERT INTO `bibliography` (`author`, `content`, `lien`, `thread_id_thread`) VALUES ('Albert Eintein', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit.', 'google.com', '1');
INSERT INTO `bibliography` (`author`, `content`, `lien`, `thread_id_thread`) VALUES ('Albert Eintein', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit.', 'google.com', '1');
INSERT INTO `bibliography` (`author`, `content`, `lien`, `thread_id_thread`) VALUES ('Albert Eintein', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit.', 'google.com', '1');
INSERT INTO `bibliography` (`author`, `content`, `lien`, `thread_id_thread`) VALUES ('Albert Eintein', 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus, recusandae, ex, iste quis eligendi esse temporibus aliquam quos dicta atque beatae sapiente incidunt sit minus harum a enim asperiores. Hic.Lorem ipsum dolor sit amet, consectetur adipisicing elit.', 'google.com', '1');

INSERT INTO `bonus_rule` (`name`, `xp`, `bonus`) VALUES ('Parrainer un internaute', '100', '10');
INSERT INTO `bonus_rule` (`name`, `xp`, `bonus`) VALUES ('Poster un article', '50', '5');
INSERT INTO `bonus_rule` (`name`, `xp`, `bonus`) VALUES ('Poster un commentaire', '20', '2');
INSERT INTO `bonus_rule` (`name`, `xp`, `bonus`) VALUES ('Sujet mis en valeur', '50', '5');


INSERT INTO `tag` (`tag_title`, `category`) VALUES ('France', 'pays');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Belgique', 'pays');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Espagne', 'pays');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Portugal', 'pays');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Ergonomie', 'modules');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Protocole', 'modules');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Facturation', 'modules');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Logistique', 'modules');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Vente', 'modules');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Tarification', 'modules');
INSERT INTO `tag` (`tag_title`, `category`) VALUES ('Expertise', 'modules');

INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('1', '1');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('2', '2');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('3', '3');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('4', '4');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('5', '1');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('6', '2');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('7', '3');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('8', '4');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('9', '1');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('10', '2');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('11', '3');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('3', '4');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('13', '1');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('1', '5');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('2', '6');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('3', '7');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('4', '8');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('5', '9');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('6', '10');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('7', '5');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('8', '6');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('9', '7');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('10', '8');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('11', '9');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('3', '10');
INSERT INTO `thread_tag` (`thread_id_thread`, `tag_id_tag`) VALUES ('13', '5');


INSERT INTO `permission` (`permission_name`, `permisson_description`) VALUES ('admin', 'Adminstrateur');
INSERT INTO `permission` (`permission_name`, `permisson_description`) VALUES ('mod', 'Modérateur');
INSERT INTO `permission` (`permission_name`, `permisson_description`) VALUES ('user', 'Utilisateur');

INSERT INTO `title` (`title_name`, `exp`) VALUES ('Villageois', '500');
INSERT INTO `title` (`title_name`, `exp`) VALUES ('Chevalier', '1000');
INSERT INTO `title` (`title_name`, `exp`) VALUES ('Bourgeois', '1500');

UPDATE `user` SET `permission_id_permission`='1' WHERE `user_name`='christine';
UPDATE `user` SET `permission_id_permission`='1' WHERE `user_name`='nhatminh';
UPDATE `user` SET `permission_id_permission`='2' WHERE `user_name`='frederic';
UPDATE `user` SET `permission_id_permission`='2' WHERE `user_name`='skander';
UPDATE `user` SET `permission_id_permission`='2' WHERE `user_name`='patrick';
UPDATE `user` SET `permission_id_permission`='3' WHERE `user_name`='veronique';
UPDATE `user` SET `permission_id_permission`='3' WHERE `user_name`='roger';
UPDATE `user` SET `permission_id_permission`='3' WHERE `user_name`='toto';


INSERT INTO `table_mode` (`app_id_app`, `user_user_name`, `permission_id_permission`) VALUES ('1', 'frederic', '2');
INSERT INTO `table_mode` (`app_id_app`, `user_user_name`, `permission_id_permission`) VALUES ('1', 'skander', '2');
INSERT INTO `table_mode` (`app_id_app`, `user_user_name`, `permission_id_permission`) VALUES ('2', 'patrick', '2');
INSERT INTO `table_mode` (`app_id_app`, `user_user_name`, `permission_id_permission`) VALUES ('2', 'skander', '2');

UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='christine';
UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='frederic';
UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='skander';
UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='nhatminh';
UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='patrick';
UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='roger';
UPDATE `user` SET `avatar`='avatarUser' WHERE `user_name`='veronique';


UPDATE `application` SET `avatar_app`='avatarApp' WHERE `id_app`='1';
UPDATE `application` SET `avatar_app`='avatarApp' WHERE `id_app`='2';
UPDATE `application` SET `avatar_app`='avatarApp' WHERE `id_app`='3';
UPDATE `application` SET `avatar_app`='avatarApp' WHERE `id_app`='4';
UPDATE `application` SET `avatar_app`='avatarApp' WHERE `id_app`='5';
UPDATE `application` SET `avatar_app`='avatarApp' WHERE `id_app`='6';


# --- !Downs
SET REFERENTIAL_INTEGRITY FALSE;
delete from table_mode;
delete from title;
delete from permission;
delete from thread_tag;
delete from tag;
delete from user_application;
delete from permission;
delete from bonus_rule;
delete from bibliography;
delete from post;
delete from thread;
delete from notification;
delete from message;
delete from application;
delete from user;
delete from service;
delete from gift;
SET REFERENTIAL_INTEGRITY TRUE;
