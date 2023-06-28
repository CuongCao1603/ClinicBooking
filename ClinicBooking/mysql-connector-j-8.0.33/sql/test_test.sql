-- use doctris_system
-- getPatientByDoctor(int doctor_id)
select distinct users.name, users.phone, users.email, a.pdate, patient.DOB, patient.patient_id
	as lastbooking from appointments 
	inner join patient on appointments.patient_id = patient.patient_id 
	inner join users on patient.username = users.username 
    inner join 
	( select patient_id as pid , max(date) as pdate from appointments group by patient_id ) as a 
    on a.pid = appointments.patient_id
    where appointments.doctor_id = '19'
    -- search 
SELECT DISTINCT users.name, users.phone, users.email, a.pdate, patient.DOB, patient.patient_id AS lastbooking FROM appointments
            INNER JOIN patient ON appointments.patient_id = patient.patient_id 
            INNER JOIN users ON patient.username = users.username INNER JOIN (
            SELECT patient_id AS pid , MAX(date) AS pdate FROM appointments GROUP BY patient_id
            ) AS a ON a.pid = appointments.patient_id 
            WHERE appointments.doctor_id = 19 AND users.email LIKE 'tr'
 -- search 2 
SELECT DISTINCT users.name, users.phone, users.email, a.pdate, patient.DOB, patient.patient_id AS lastbooking FROM appointments
                INNER JOIN patient ON appointments.patient_id = patient.patient_id 
                INNER JOIN users ON patient.username = users.username INNER JOIN (
                SELECT patient_id AS pid , MAX(date) AS pdate FROM appointments GROUP BY patient_id
                ) AS a ON a.pid = appointments.patient_id 
                WHERE appointments.doctor_id = 19 AND users.name LIKE '%H%'
-- getAllAppointment(int id)
    SELECT a.appointment_id, p.patient_id,  u.name, a.date, a.time, a.status from appointments a
                INNER JOIN patient p ON a.patient_id = p.patient_id
                 INNER JOIN users u ON p.username = u.username
                 WHERE a.doctor_id = '19'
                 group by a.appointment_id, p.patient_id, u.name,a.date, a.time,a.status
                 order by CAST(a.date AS DATETIME) + CAST(a.time AS DATETIME) desc
				

-- getPatientbyid(int patient_id)
SELECT u.name, u.email, u.phone, u.gender, p.DOB FROM users u 
inner join patient p on u.username = p.username where p.patient_id = '9'

-- getAppointmentByPatient(int doctor_id, int patient_id)
select a.date,a.time,a.status from appointments a 
inner join patient p on a.patient_id = p.patient_id 
where a.doctor_id = '19' and p.patient_id = '5' ORDER BY a.date ASC



SELECT * FROM patient WHERE username IN (SELECT username FROM users WHERE name LIKE 'HÃ  Há»¯u Viá»t')
-- booking 
INSERT INTO `appointments` (`doctor_id`, `patient_id`, `staff`, `date`, `time`, `description`, `status`, `fee`, `payment`) 
VALUES (19, 1, 'lamtt5', STR_TO_DATE('18-08-2022','%d-%m-%Y'), '12:00', 'Bác sĩ ơi ', 'Complete', 400000, 'ahaha');
                
INSERT INTO `appointments` (`doctor_id`, `patient_id`, `staff`, `date`, `time`, `description`, `status`, `fee`, `payment`) 
VALUES (19, 9, 'lamtt5', STR_TO_DATE('2022-09-11', '%Y-%m-%d'), '11:00', 'Bác sĩ ơi cứu em', 'Complete', 365365, 'hihi');
    
    
    
    
    
    
    
    
    
    
    
    
    