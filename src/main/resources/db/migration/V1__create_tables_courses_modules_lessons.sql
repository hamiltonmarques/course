CREATE TABLE tb_courses
(
    id            UUID PRIMARY KEY,
    instructor_id UUID         NOT NULL,
    name          VARCHAR(150) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    image_url     VARCHAR(255),
    status        VARCHAR(20)  NOT NULL,
    level         VARCHAR(20)  NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP    NOT NULL
);

CREATE TABLE tb_modules
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(150) NOT NULL,
    description VARCHAR(255) NOT NULL,
    course_id   UUID         NOT NULL,
    created_at  TIMESTAMP    NOT NULL,

    CONSTRAINT fk_modules_course
        FOREIGN KEY (course_id)
            REFERENCES tb_courses (id)
            ON DELETE CASCADE
);

CREATE TABLE tb_lessons
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(150) NOT NULL,
    description VARCHAR(255) NOT NULL,
    video_url   VARCHAR(255) NOT NULL,
    module_id   UUID         NOT NULL,
    created_at  TIMESTAMP    NOT NULL,

    CONSTRAINT fk_lessons_module
        FOREIGN KEY (module_id)
            REFERENCES tb_modules (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_modules_course_id
    ON tb_modules (course_id);

CREATE INDEX idx_lessons_module_id
    ON tb_lessons (module_id);
