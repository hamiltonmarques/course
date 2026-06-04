-- COURSE 1
INSERT INTO tb_courses (id, instructor_id, name, description, image_url, status, level, created_at, updated_at)
VALUES ('b3c3ef6f-3d4d-4c6a-a0f0-4aaf4d17d7d1',
        '8684603c-e3df-411c-ae7f-b0f89a1521dc', -- isis.instrutora
        'Curso de Spring Boot',
        'Curso completo de Spring Boot e Microsservicos',
        'https://meusite.com/imagens/spring-boot.png',
        'IN_PROGRESS',
        'BEGINNER',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- MODULES COURSE 1
INSERT INTO tb_modules (id, title, description, course_id, created_at)
VALUES ('6d5c77c8-c2ef-4af2-b2f8-1d67b0db9f9e',
        'Introducao',
        'Apresentacao do curso',
        'b3c3ef6f-3d4d-4c6a-a0f0-4aaf4d17d7d1',
        CURRENT_TIMESTAMP),

       ('f47d5c8d-8f7e-4f0d-96c2-cf07e77d89bb',
        'Spring Boot',
        'Configurando o projeto',
        'b3c3ef6f-3d4d-4c6a-a0f0-4aaf4d17d7d1',
        CURRENT_TIMESTAMP);

-- LESSONS COURSE 1
INSERT INTO tb_lessons (id, title, description, video_url, module_id, created_at)
VALUES ('3f4f4a75-11de-4b5d-b98e-6f6df6e87ef3',
        'Boas Vindas',
        'Apresentacao do instrutor',
        'https://youtube.com/video1',
        '6d5c77c8-c2ef-4af2-b2f8-1d67b0db9f9e',
        CURRENT_TIMESTAMP),

       ('5c0f0c2d-57f5-4ea1-bd91-8e72f91bfa45',
        'Criando Projeto',
        'Criacao do primeiro projeto Spring Boot',
        'https://youtube.com/video2',
        'f47d5c8d-8f7e-4f0d-96c2-cf07e77d89bb',
        CURRENT_TIMESTAMP),

       ('d42d2c1b-c0d8-4a69-bf61-7d0af43f82a1',
        'Primeiro Controller',
        'Criando endpoints REST',
        'https://youtube.com/video3',
        'f47d5c8d-8f7e-4f0d-96c2-cf07e77d89bb',
        CURRENT_TIMESTAMP);

-- COURSE 2
INSERT INTO tb_courses (id, instructor_id, name, description, image_url, status, level, created_at, updated_at)
VALUES ('8e4f8b49-9b5d-49f7-a6ef-0cf9d94c2c71',
        '9fc5008a-9ef3-4581-b859-26663b8697ef', -- fernanda.instrutora
        'Curso de Docker e Containers',
        'Aprenda Docker, Docker Compose e conceitos de conteinerizacao',
        'https://meusite.com/imagens/docker.png',
        'FINISHED',
        'ADVANCED',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- MODULES COURSE 2
INSERT INTO tb_modules (id, title, description, course_id, created_at)
VALUES ('4f5f4c1a-9f6e-4f4c-a3df-4d1e9f5d2e81',
        'Fundamentos do Docker',
        'Introducao ao Docker e seus conceitos',
        '8e4f8b49-9b5d-49f7-a6ef-0cf9d94c2c71',
        CURRENT_TIMESTAMP),

       ('7a8d9b12-0e4a-4e5f-bf4c-8e2f1d6a7c93',
        'Docker Compose',
        'Orquestrando containers com Docker Compose',
        '8e4f8b49-9b5d-49f7-a6ef-0cf9d94c2c71',
        CURRENT_TIMESTAMP);

-- LESSONS COURSE 2
INSERT INTO tb_lessons (id, title, description, video_url, module_id, created_at)
VALUES ('9d3a5e71-3e62-44a7-a2db-2d6c9c6d4f01',
        'O que e Docker?',
        'Visao geral sobre containers',
        'https://youtube.com/docker-video-1',
        '4f5f4c1a-9f6e-4f4c-a3df-4d1e9f5d2e81',
        CURRENT_TIMESTAMP),

       ('2b7f4d98-6c81-4f2f-a8a1-5f4b3e7d1c52',
        'Criando o Primeiro Container',
        'Executando containers na pratica',
        'https://youtube.com/docker-video-2',
        '7a8d9b12-0e4a-4e5f-bf4c-8e2f1d6a7c93',
        CURRENT_TIMESTAMP),

       ('5e9c7f23-8a6d-47d4-b4c7-1d3e8f6a9b84',
        'Subindo Aplicacoes com Compose',
        'Utilizando docker-compose para gerenciar servicos',
        'https://youtube.com/docker-video-3',
        '7a8d9b12-0e4a-4e5f-bf4c-8e2f1d6a7c93',
        CURRENT_TIMESTAMP);