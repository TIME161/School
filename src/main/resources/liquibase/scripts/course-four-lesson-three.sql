-- changeSet id="addIndexToStudentName" author="TIME"
CREATE INDEX idx_student_name ON student (name);

-- changeSet id="addIndexesToFacultyNameAndColor" author="TIME"
CREATE INDEX idx_faculty_name_and_color ON faculty (name, color);