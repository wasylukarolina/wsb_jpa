-- Dodanie adresów
INSERT INTO ADDRESS (city, address_line1, address_line2, postal_code) VALUES
                                                                          ('Warsaw', 'Main Street 1', 'Apartment 12', '00-001'),
                                                                          ('Krakow', 'Market Square 5', NULL, '31-002'),
                                                                          ('Gdansk', 'Seaside Avenue 20', NULL, '80-803');

-- Dodanie lekarzy z różnymi specjalizacjami
INSERT INTO DOCTOR (first_name, last_name, telephone_number, email, doctor_number, specialization) VALUES
                                                                                                       ('John', 'Doe', '123456789', 'john.doe@email.com', 'D001', 'SURGEON'),
                                                                                                       ('Jane', 'Smith', '987654321', 'jane.smith@email.com', 'D002', 'GP'),
                                                                                                       ('Bob', 'Johnson', '456123789', 'bob.johnson@email.com', 'D003', 'DERMATOLOGIST'),
                                                                                                       ('Alice', 'Brown', '321654987', 'alice.brown@email.com', 'D004', 'OCULIST');

-- Dodanie pacjentów
INSERT INTO PATIENT (first_name, last_name, date_of_birth, telephone_number, email, patient_number, address_id) VALUES
                                                                                                                    ('Tom', 'Williams', '1990-01-15', '666777888', 'tom.williams@email.com', 'P001', 1),
                                                                                                                    ('Eva', 'Miller', '1985-03-22', '555666777', 'eva.miller@email.com', 'P002', 2);

-- Dodanie wizyt
INSERT INTO VISIT (description, time, doctor_id, patient_id) VALUES
                                                                 ('Consultation', '2024-12-08 10:00:00', 1, 1),
                                                                 ('Follow-up check-up', '2024-12-08 12:00:00', 2, 2);

-- Dodanie leczenia
INSERT INTO MEDICAL_TREATMENT (description, type, visit_id) VALUES
                                                                ('Ultrasound of the abdomen', 'USG', 1),
                                                                ('Electrocardiogram', 'EKG', 2);
