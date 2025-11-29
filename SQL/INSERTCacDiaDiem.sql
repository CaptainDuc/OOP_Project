INSERT INTO flight (fcode, fname, sourcee, destination, classname, price, journey_date, journey_time) 
VALUES 
-- HCM -> SINGAPORE
('SQ178', 'Singapore Airlines', 'HCM', 'Singapore', 'Business', 11500000.00, '2026-03-05', '14:00:00'),
('SQ178', 'Singapore Airlines', 'HCM', 'Singapore', 'First', 17000000.00, '2026-03-05', '14:00:00'),

-- HCM -> SYDNEY
('QF060', 'Qantas', 'HCM', 'Sydney', 'Business', 18000000.00, '2026-04-10', '21:30:00'),
('QF060', 'Qantas', 'HCM', 'Sydney', 'First', 28000000.00, '2026-04-10', '21:30:00'),

-- HCM -> PARIS
('AF253', 'Air France', 'HCM', 'Paris', 'Business', 20500000.00, '2026-05-20', '00:05:00'),
('AF253', 'Air France', 'HCM', 'Paris', 'First', 32000000.00, '2026-05-20', '00:05:00'),

-- HA NOI -> DUBAI
('EK394', 'Emirates', 'Ha Noi', 'Dubai', 'Business', 16500000.00, '2026-03-15', '11:20:00'),
('EK394', 'Emirates', 'Ha Noi', 'Dubai', 'First', 25000000.00, '2026-03-15', '11:20:00'),

-- HA NOI -> ISTANBUL
('TK168', 'Turkish Airlines', 'Ha Noi', 'Istanbul', 'Business', 19000000.00, '2026-04-01', '05:45:00'),
('TK168', 'Turkish Airlines', 'Ha Noi', 'Istanbul', 'First', 30500000.00, '2026-04-01', '05:45:00'),

-- HA NOI -> FRANKFURT
('LH772', 'Lufthansa', 'Ha Noi', 'Frankfurt', 'Business', 22000000.00, '2026-05-10', '23:55:00'),
('LH772', 'Lufthansa', 'Ha Noi', 'Frankfurt', 'First', 38000000.00, '2026-05-10', '23:55:00'),

-- HCM -> DUBAI
('EK395', 'Emirates', 'HCM', 'Dubai', 'Business', 17200000.00, '2026-03-20', '01:30:00'),
('EK395', 'Emirates', 'HCM', 'Dubai', 'First', 26000000.00, '2026-03-20', '01:30:00'),

-- HCM -> ISTANBUL
('TK169', 'Turkish Airlines', 'HCM', 'Istanbul', 'Business', 19500000.00, '2026-04-05', '06:00:00'),
('TK169', 'Turkish Airlines', 'HCM', 'Istanbul', 'First', 31500000.00, '2026-04-05', '06:00:00'),

-- HCM -> FRANKFURT
('LH773', 'Lufthansa', 'HCM', 'Frankfurt', 'Business', 22500000.00, '2026-05-15', '23:00:00'),
('LH773', 'Lufthansa', 'HCM', 'Frankfurt', 'First', 39000000.00, '2026-05-15', '23:00:00'),

-- HA NOI -> TOKYO
('VN310', 'Vietnam Airlines', 'Ha Noi', 'Tokyo', 'Business', 12500000.00, '2026-06-01', '09:45:00'),
('VN310', 'Vietnam Airlines', 'Ha Noi', 'Tokyo', 'Economy', 6500000.00, '2026-06-01', '09:45:00'),

-- HA NOI -> CHICAGO
('KE231', 'Korean Air', 'Ha Noi', 'Chicago', 'Business', 35000000.00, '2026-06-15', '23:30:00'),
('KE231', 'Korean Air', 'Ha Noi', 'Chicago', 'Economy', 18000000.00, '2026-06-15', '23:30:00'),

-- HCM -> TOKYO
('JL750', 'Japan Airlines', 'HCM', 'Tokyo', 'Business', 13500000.00, '2026-06-05', '00:00:00'),
('JL750', 'Japan Airlines', 'HCM', 'Tokyo', 'Economy', 7000000.00, '2026-06-05', '00:00:00'),

-- HCM -> CHICAGO
('NH12', 'ANA', 'HCM', 'Chicago', 'Business', 36000000.00, '2026-06-20', '22:00:00'),
('NH12', 'ANA', 'HCM', 'Chicago', 'Economy', 19000000.00, '2026-06-20', '22:00:00'),

-- 6. HA NOI -> BANGKOK (Chuyến thứ 6)
('TG564', 'Thai Airways', 'Ha Noi', 'Bangkok', 'Business', 7800000.00, '2026-07-05', '18:15:00'),
('TG564', 'Thai Airways', 'Ha Noi', 'Bangkok', 'Economy', 4500000.00, '2026-07-05', '18:15:00'),

-- 7. HA NOI -> KUALA LUMPUR (Chuyến thứ 7)
('MH753', 'Malaysia Airlines', 'Ha Noi', 'Kuala Lumpur', 'Business', 8500000.00, '2026-07-15', '13:00:00'),
('MH753', 'Malaysia Airlines', 'Ha Noi', 'Kuala Lumpur', 'Economy', 5200000.00, '2026-07-15', '13:00:00'),

-- 8. HA NOI -> SYDNEY (Chuyến thứ 8)
('VN773', 'Vietnam Airlines', 'Ha Noi', 'Sydney', 'Business', 20000000.00, '2026-07-25', '20:30:00'),
('VN773', 'Vietnam Airlines', 'Ha Noi', 'Sydney', 'Economy', 12000000.00, '2026-07-25', '20:30:00');