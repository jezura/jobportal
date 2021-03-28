INSERT INTO `administrators` (`id`, `first_name`, `last_name`, `email`, `password`, `role`) VALUES ('1', 'Jaroslav', 'Ježek', 'administrator@jobportal.cz', '$2y$12$FZ2nqZkH1GlOjKaEw3Dnu.T8jxaZzAqdJwlLA2Cw56K/ue/PwamfG', 'ADMINISTRATOR');


ALTER TABLE `titles` CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT;

INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Bc', 'Bc', 'Vysokoskolske_bakalarske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('bc', 'Bc', 'Vysokoskolske_bakalarske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('BC', 'Bc', 'Vysokoskolske_bakalarske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('BcA', 'BcA', 'Vysokoskolske_bakalarske', 'Umeni');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('bca', 'BcA', 'Vysokoskolske_bakalarske', 'Umeni');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('Bca', 'BcA', 'Vysokoskolske_bakalarske', 'Umeni');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('BCA', 'BcA', 'Vysokoskolske_bakalarske', 'Umeni');

INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Ing', 'Ing', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('ing', 'Ing', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('ING', 'Ing', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('Ing\\. arch', 'Ing. arch', 'Vysokoskolske_magisterske', 'Stavebnictvi');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('ing\\. arch', 'Ing. arch', 'Vysokoskolske_magisterske', 'Stavebnictvi');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('Ing\\.arch', 'Ing. arch', 'Vysokoskolske_magisterske', 'Stavebnictvi');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('ing\\.arch', 'Ing. arch', 'Vysokoskolske_magisterske', 'Stavebnictvi');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Mgr', 'Mgr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('mgr', 'Mgr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('MGR', 'Mgr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MgA', 'MgA', 'Vysokoskolske_magisterske', 'Umeni');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('mga', 'MgA', 'Vysokoskolske_magisterske', 'Umeni');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MGA', 'MgA', 'Vysokoskolske_magisterske', 'Umeni');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MUDr', 'MUDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MUDR', 'MUDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MDDr', 'MDDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MDDR', 'MDDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MVDr', 'MVDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('MVDR', 'MVDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');

INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('JUDr', 'JUDr', 'Vysokoskolske_magisterske', 'Prava');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('JUDR', 'JUDr', 'Vysokoskolske_magisterske', 'Prava');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('PhDr', 'PhDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('PHDr', 'PhDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('PHDR', 'PhDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('RNDr', 'RNDr', 'Vysokoskolske_magisterske', 'Prirodni_vedy,chemie_a_fyzika');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('RnDr', 'RNDr', 'Vysokoskolske_magisterske', 'Prirodni_vedy,chemie_a_fyzika');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('RNDR', 'RNDr', 'Vysokoskolske_magisterske', 'Prirodni_vedy,chemie_a_fyzika');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PharmDr', 'PharmDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PHARMDr', 'PharmDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('ThLic', 'ThLic', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THLic', 'ThLic', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THLIC', 'ThLic', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('ThDr', 'ThDr', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THDr', 'ThDr', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THDR', 'ThDr', 'Vysokoskolske_magisterske', 'Teologie');

INSERT INTO titles (title_variant, official_version, degree)
VALUES ('MSDr', 'MSDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('MsDr', 'MSDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('MSDR', 'MSDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PaedDr', 'PaedDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PAEDDr', 'PaedDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PAEDDR', 'PaedDr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PhMr', 'PhMr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PHMr', 'PhMr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('PHMR', 'PhMr', 'Vysokoskolske_magisterske', 'Zdravotnictvi_a_medicina');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('RCDr', 'RCDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('RCDR', 'RCDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('RSDr', 'RSDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('RSDR', 'RSDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('RTDr', 'RTDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('RTDR', 'RTDr', 'Vysokoskolske_magisterske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('ThMgr', 'ThMgr', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THMgr', 'ThMgr', 'Vysokoskolske_magisterske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THMGR', 'ThMgr', 'Vysokoskolske_magisterske', 'Teologie');

INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Ph\\.D', 'Ph.D', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('PH\\.D', 'Ph.D', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('PhD', 'Ph.D', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DSc', 'DSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DSC', 'DSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('CSc', 'CSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('csc', 'CSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('CSC', 'CSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Dr', 'Dr', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('dr', 'Dr', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DR', 'Dr', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DrSc', 'DrSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DRSc', 'DrSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DRSC', 'DrSc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('Th\\.D', 'Th.D', 'Vysokoskolske_doktorske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('ThD', 'Th.D', 'Vysokoskolske_doktorske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('THD', 'Th.D', 'Vysokoskolske_doktorske', 'Teologie');
INSERT INTO titles (title_variant, official_version, degree, title_edu_field)
VALUES ('TH\\.D', 'Th.D', 'Vysokoskolske_doktorske', 'Teologie');

INSERT INTO titles (title_variant, official_version, degree)
VALUES ('doc', 'doc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Doc', 'doc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('DOC', 'doc', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('prof', 'prof', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('Prof', 'prof', 'Vysokoskolske_doktorske');
INSERT INTO titles (title_variant, official_version, degree)
VALUES ('PROF', 'prof', 'Vysokoskolske_doktorske');




ALTER TABLE `czech_names` CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT;

INSERT INTO czech_names (name, gender)
VALUES ('Dalimil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Dalemil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kašpar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Melichar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Baltazar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Čestmír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vladan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Břetislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Pravoslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Radovan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Česlav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ctirad', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Drahoslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vladislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Slavomír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Zdeněk', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Zdenek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Zdenko', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Miloš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Robin', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Hynek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Mojmír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Věnceslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Valentin', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Valentýn', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Miloslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Patrik', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Oldřich', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Petr', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Svatopluk', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Matyáš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Matej', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Matěj', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alexandr', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alexander', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alexis', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Lumír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Horymír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Rufin', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Bedřich', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kamil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kazimír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Miroslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Mirek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Thomas', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Tom', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Tomáš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Gregor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Řehoř', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Herbert', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vlastimil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Edvard', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Eduard', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jozef', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Josef', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Radomil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Radek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Gabriel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Marian', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Marius', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Marián', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Emanuel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Arnošt', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kvido', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Quido', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Hugo', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Richard', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Heřman', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Herman', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Dušan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Julius', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Július', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Julian', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Aleš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vincent', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vincenc', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Rolf', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Rudolf', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vojtěch', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('George', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Juraj', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jiří', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Marek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ota', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Otto', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Oto', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jaroslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vlastislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Robert', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Blahoslav', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Zikmund', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alex', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alexej', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Květoslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Radoslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Stanislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ctibor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Stibor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Pankrác', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Servác', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bonifác', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Přemysl', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ivo', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ivoš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Zbyšek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Emil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vladimír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Filip', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Waldemar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Valdemar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vilém', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Maxmilián', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Maxmilian', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Maximilian', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ferdinand', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jarmil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Dalibor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Dobroslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Norbert', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Medard', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Otta', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bruno', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Antonín', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Antonin', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Roland', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vít', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vítek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Zbyněk', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Adolf', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Milan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Leoš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alojz', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Aloys', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alois', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ivan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ladislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Lubomír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Lubomil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Petr', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Pavel', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Radomír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Prokop', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Cyril', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Metoděj', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bořek', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jindřich', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Liboslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Luboslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Luboš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Čeněk', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vítězslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Libor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jakub', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Věroslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Viktor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Victor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bořivoj', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ignác', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Oskar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Gustav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Dominik', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Milivoj', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Křišťan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kristián', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Soběslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Roman', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vavřinec', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Alan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jáchym', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Joachim', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ludvík', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bernard', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Božislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bohuslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bartolomej', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bartoloměj', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Radim', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Radimír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Luděk', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Otokar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Otakar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Augustýn', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Augustin', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Samuel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bronislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Boris', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Boleslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Denis', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Lubor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kryštof', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Krištof', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Oleg', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Matouš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jaromír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jonáš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Václav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Michal', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Michael', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jeroným', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jeronym', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Jarolím', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Igor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Oliver', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bohumil', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('František', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Justin', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Štefan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Andrej', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Marcel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Havel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Lukáš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vendelín', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Theodor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Teodor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ervín', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Erik', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Tadeáš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Felix', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Hubert', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Karel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bohumír', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Bohdan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Evžen', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Eugen', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Martin', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Benedikt', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Tibor', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Sáva', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Leopold', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Otmar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Otomar', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Albert', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Klement', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Kliment', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Artur', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('René', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ruben', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ondřej', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Svatoslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Světoslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Mikoláš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Mikuláš', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Nikolas', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Benjamin', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Ambrož', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Vratislav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Daniel', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Dan', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Miloslav', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Simeon', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Simon', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Šimon', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Adam', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Štepán', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Štěpán', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Judita', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('David', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Silvestr', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Silvester', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Sylvestr', 'muz');

INSERT INTO czech_names (name, gender)
VALUES ('Francesco', 'muz');
INSERT INTO czech_names (name, gender)
VALUES ('Frančesko', 'muz');









INSERT INTO czech_names (name, gender)
VALUES ('Karina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Karin', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Karína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Radmila', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Diana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Vilma', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Wilhelmina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Bohdana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Edita', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Alice', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Doubravka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ilona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Běla', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Milena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zora', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ingrid', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Otilie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Otýlie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zdeslava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zdislava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Marika', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Nela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Blažej', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jarmila', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dobromila', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Wanda', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Vanda', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Veronika', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Milada', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Apolena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Božena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Slavěna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Valentýna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jiřina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jorga', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ljuba', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Gisela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Gizela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lenka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Eleonora', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lilian', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Liliana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lily', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dorotea', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dorota', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Anežka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Agnes', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ines', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Stella', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Stela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Gabriela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Francesca', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Františka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Viktorie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Andělína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Angelina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Anděla', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Růžena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Rosita', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Rozálie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Rút', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Matylda', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Rut', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ida', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Elena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Světlana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Leona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Yvona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ivona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ditta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dita', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Sonja', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Sonia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Soňa', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Tatiana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Taťána', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Erika', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ivanka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ivana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Mirka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Miroslava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Vendula', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Vendulka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Hermína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ema', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Emma', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dušana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Darya', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Darja', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Daria', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Isabel', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Izabela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Anastázie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Anastazia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Irena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Irini', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Valérie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Valeria', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Valerie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Rostislava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Marcela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Alexandra', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Evžénie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Evženie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Klaudia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Klaudie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Claudia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Blažena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Svatava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Žofie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Aneta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Anetta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Nataša', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Monika', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Viola', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Kamila', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Laura', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Tamara', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Iveta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Yveta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Stanislava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Antonie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Antonina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Kveta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Květa', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Pavla', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zdena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zdenka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zdeňka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Adriana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Šárka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jaroslava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Patricie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Bohuslava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Nora', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Drahoslava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Amálie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Libuše', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Olga', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Markéta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Margit', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Margareta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Karla', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Karolina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Karolína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Martina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Drahomíra', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ilja', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Magdaléna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Magdalena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Kristýna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Kristen', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Kristina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Anna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Martha', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Marta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Miluše', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Oldřiška', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lada', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Susana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zuzana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Clara', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Klára', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Alena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Hanka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Hana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Hannah', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Petronila', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Petra', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Helena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Johana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Sandra', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Evelina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Evelin', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Evelína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Vladěna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Paulína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Pavlína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Linda', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Adéla', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Adela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Adléta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Henrieta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jindřiška', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Regína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Regina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Mariana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Daniela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Irma', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Denisa', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Marie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Radka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jolana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ludmila', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Naděžda', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zita', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Darina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Berta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Bertina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zlata', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Andrea', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ondřejka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Václava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Olívie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Olivia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Eliška', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Elsa', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Hanuš', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Justina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Justýna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Viera', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Věra', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Sára', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Marina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Marína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Renata', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Renáta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Agáta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Tereza', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Terezie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Terezia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Hedvika', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Michaela', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Michala', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Bridget', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Brigita', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Sabina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Nina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Beáta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Beata', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zoe', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Šarlota', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zoja', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Silva', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Sylva', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Silvie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Štepánka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Stefanie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Miriam', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Liběna', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Saskie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Mahulena', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Romana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Elisabeth', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Alžběta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Nikola', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Cecílie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Emílie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Emilia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Kateřina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Katka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Katarína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Xenie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Renée', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('René', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Zina', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Iva', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Blanka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Bára', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Barbara', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Barbora', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Jitka', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Květoslava', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Julie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Juliana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Julia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Simona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Šimona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Simeona', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lucie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lucia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Luciana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lydia', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lýdie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Lydie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Radana', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Albína', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Ester', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dáša', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dagmar', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Dagmara', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Natálie', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Vlasta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Eva', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Žaneta', 'zena');
INSERT INTO czech_names (name, gender)
VALUES ('Bohumila', 'zena');



ALTER TABLE `edu_levels` CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT;

INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (0, 'Zakladni', 'Základní vzdělání');
INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (1, 'Vyuceni_nebo_Stredoskolske_bez_maturity', 'Vyučení nebo středoškolské bez maturity');
INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (2, 'Stredoskolske_s_maturitou', 'Středoškolské s maturitou');
INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (3, 'Vyssi_odborne', 'Vyšší odborné');
INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (4, 'Vysokoskolske_bakalarske', 'Vysokoškolské bakalářské');
INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (5, 'Vysokoskolske_magisterske', 'Vysokoškolské magisterské');
INSERT INTO edu_levels (ann_code, name, pretty_name)
VALUES (6, 'Vysokoskolske_doktorske', 'Vysokoškolské doktorské');



ALTER TABLE `edu_general_fields` CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT;

INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (0, 'Gymnazium_ZS', 'Všeobecné vzdělání (Gymnázium, ZŠ)');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (1, 'Informatika_a_management', 'Informatika a management');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (2, 'Obchod_a_ekonomie', 'Obchod a ekonomie');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (3, 'Pedagogika_ucitelstvi_a_telovychova', 'Pedagogika, učitelství a tělovýchova');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (4, 'Filosofie_politologie_historie_psychologie_sociologie_verejna_sprava', 'Sociální a společenské obory - filosofie, politologie, historie, psychologie, sociologie, veřejná správa a podobné');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (5, 'Doprava', 'Doprava a logistika');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (6, 'Prirodni_vedy_chemie_fyzika_matematika', 'Prirodní vědy - chemie, fyzika, matematika a podobné');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (7, 'Lingvistika', 'Lingvistika');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (8, 'Elektrotechnika_technika_mechanika', 'Elektrotechnika, technika, mechanika a podbné');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (9, 'Zdravotnictvi_a_medicina', 'Zdravotnictví a medicína, včetně veteriny');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (10, 'Stavebnictvi', 'Stavebnictví');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (11, 'Zemedelstvi_a_lesnictvi', 'Zemědelství a lesnictví');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (12, 'Pravo_obrana_a_ochrana', 'Právo, obrana a ochrana');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (13, 'Umeni', 'Umění');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (14, 'Sluzby', 'Služby');
INSERT INTO edu_general_fields (ann_code, name, pretty_name)
VALUES (15, 'Remeslna_vyroba', 'Řemeslná výroba a ostatní do služeb nezařaditelné');