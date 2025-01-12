-- Dodanie adresów
INSERT INTO ADDRESS (city, address_line1, address_line2, postal_code) VALUES
                                                                          ('Warszawa', 'Główna 1', 'Mieszkanie 12', '00-001'),
                                                                          ('Kraków', 'Rynek 5', NULL, '31-002'),
                                                                          ('Gdańsk', 'Nadmorska 20', NULL, '80-803'),
                                                                          ('Poznań', 'Poznańska 10', NULL, '60-001');

-- Dodanie lekarzy z różnymi specjalizacjami
INSERT INTO DOCTOR (first_name, last_name, telephone_number, email, doctor_number, specialization) VALUES
                                                                                                       ('Jan', 'Kowalski', '123456789', 'jan.kowalski@email.com', 'D001', 'SURGEON'),
                                                                                                       ('Anna', 'Nowak', '987654321', 'anna.nowak@email.com', 'D002', 'GP'),
                                                                                                       ('Piotr', 'Wiśniewski', '456123789', 'piotr.wisniewski@email.com', 'D003', 'DERMATOLOGIST'),
                                                                                                       ('Maria', 'Dąbrowska', '321654987', 'maria.dabrowska@email.com', 'D004', 'OCULIST');

-- Dodanie pacjentów
INSERT INTO PATIENT (first_name, last_name, date_of_birth, telephone_number, email, patient_number, address_id, is_insured) VALUES
                                                                                                                                ('Tomasz', 'Wiśniewski', '1990-01-15', '666777888', 'tomasz.wisniewski@email.com', 'P001', 1, TRUE),
                                                                                                                                ('Ewa', 'Miller', '1985-03-22', '555666777', 'ewa.miller@email.com', 'P002', 2, FALSE),
                                                                                                                                ('Adam', 'Nowak', '1982-06-10', '444555666', 'adam.nowak@email.com', 'P003', 3, TRUE),
                                                                                                                                ('Karolina', 'Zielińska', '1995-08-05', '333444555', 'karolina.zielinska@email.com', 'P004', 4, TRUE);

-- Dodanie wizyt
INSERT INTO VISIT (description, time, doctor_id, patient_id) VALUES
                                                                 ('Konsultacja', '2024-12-08 10:00:00', 1, 1),
                                                                 ('Kontrola po zabiegu', '2024-12-08 12:00:00', 2, 2),
                                                                 ('Badanie kontrolne', '2024-12-09 09:00:00', 3, 1),
                                                                 ('Zabieg laserowy', '2024-12-10 14:00:00', 4, 3),
                                                                 ('USG kolana', '2024-12-11 15:30:00', 1, 4);

-- Dodanie leczenia
INSERT INTO MEDICAL_TREATMENT (description, type, visit_id) VALUES
                                                                ('USG jamy brzusznej', 'USG', 1),
                                                                ('Elektrokardiogram', 'EKG', 2),
                                                                ('Badanie wzroku', 'OPTICAL_TEST', 4),
                                                                ('Fizykoterapia', 'PHYSIOTHERAPY', 5);
