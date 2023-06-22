select distinct users.img, users.name, a.pdate, users.phone, users.email, patient.DOB, patient.patient_id
	as lastbooking from appointments 
	inner join patient on appointments.patient_id = patient.patient_id 
	inner join users on patient.username = users.username inner join 
	(
	select patient_id as pid , max(date) as pdate from appointments group by patient_id
	) 
	as a on a.pid = appointments.patient_id where appointments.doctor_id = ?
    
    
    
    
    