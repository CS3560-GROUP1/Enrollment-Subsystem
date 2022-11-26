# Enrollment-Subsystem

## Local SQL Server Setup

1. CREATE DATABASE enrollmentdb;
2. CREATE User 'EnrollmentUser'@'localhost';
3. GRANT ALL ON enrollmentdb.* TO 'EnrollmentUser'@'localhost';

## Debug Mode

- When Active Opens a Panel that has access to all panels 

## Regular Run

- Opens Login Panel and connects to local SQL server
    - On Login Panel it is possible to:
        1. Create Account
        2. Recover Password
        3. Sign In
- After a successfull login the home panel will be displayed
    - Shows the User's name and current term
    - Change Term Button
    - Menu Holding: Search, Cart, Enroll

### Required Tasks
    - [ ] Password Recovery
    - [ ] Class Searching
    - [ ] User Course Cart
    - [ ] User Enrollment
    - [ ] Account Claiming (Sign Up)
    - [x] User Login
    - [ ] User Schedule
    - [ ] User Schedule Conflicts Check
    - [ ]
    
    - [ ] Course Mock Data
    - [ ] Professors Mock Data
    - [ ] Rooms Mock Data
    - [ ] Sections Mock Data
    - [ ] Students Mock Data

LOGIN PASSWORD is NAME with Initial Capital

