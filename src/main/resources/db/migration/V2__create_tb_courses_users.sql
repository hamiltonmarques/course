CREATE TABLE tb_courses_users
(
    id        UUID PRIMARY KEY,
    course_id UUID NOT NULL,
    user_id   UUID NOT NULL,

    CONSTRAINT fk_course_users FOREIGN KEY (course_id)
        REFERENCES tb_courses (id)
);