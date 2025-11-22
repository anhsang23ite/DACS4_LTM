CREATE DATABASE rmi_system;
USE rmi_system;

-- Bảng User
CREATE TABLE users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    phoneNumber VARCHAR(20)
);

-- Bảng Admin
CREATE TABLE admins (
    adminId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

-- Bảng Location (Tách ra để đúng quan hệ 1-1 trong Diagram)
CREATE TABLE locations (
    locationId INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE
);

-- Bảng Report
CREATE TABLE reports (
    reportId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    title VARCHAR(255),
    description TEXT,
    image VARCHAR(255), -- Lưu đường dẫn ảnh hoặc Base64
    locationId INT,
    status VARCHAR(50) DEFAULT 'PENDING',
    feedback TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(userId),
    FOREIGN KEY (locationId) REFERENCES locations(locationId)
);

-- Bảng Notification
CREATE TABLE notifications (
    notificationId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT,
    message TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    isRead BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

-- Insert mẫu 1 admin mặc định
INSERT INTO admins (name, email, password) VALUES ('Admin System', 'admin@test.com', 'admin123');