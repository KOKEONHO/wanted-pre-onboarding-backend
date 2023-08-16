SET foreign_key_checks = 0;

-- member 테이블 더미 데이터 추가
INSERT INTO `member` (`name`, `email`, `password`)
VALUES ('고건호', 'rhrjsgh97@gmail.com', 'El7NskRkb35bPuRxUHkJPA=='),
       ('서혜선', 'hseon0927@naver.com', '9K5p0OexR4R0LyCEDpL99A=='),
       ('홍길동', 'honggildong@kakao.com', 'qHmrbLcKbgeIUSn4wn9oXw=='),
       ('정호영', 'honux@aws.com', 'uLaIUImVS824mSpLEELi8g=='),
       ('테스트', 'test@test.com', 'WlXRI9s1LuGx+itEhHNizw==');

-- article 테이블 더미 데이터 추가
INSERT INTO `article` (`member_id`, `title`, `contents`, `posted_at`)
VALUES (1, '1번 게시글', '1번 내용', '2023-08-16 21:05:54'),
       (1, '2번 게시글', '2번 내용', '2023-08-16 21:05:57'),
       (1, '3번 게시글', '3번 내용', '2023-08-16 21:05:59'),
       (2, '4번 게시글', '4번 내용', '2023-08-16 21:06:54'),
       (2, '5번 게시글', '5번 내용', '2023-08-16 21:06:55'),
       (2, '6번 게시글', '6번 내용', '2023-08-16 21:06:57'),
       (3, '7번 게시글', '7번 내용', '2023-08-16 21:06:59'),
       (3, '8번 게시글', '8번 내용', '2023-08-16 21:07:10'),
       (3, '9번 게시글', '9번 내용', '2023-08-16 21:07:13'),
       (4, '10번 게시글', '10번 내용', '2023-08-16 21:07:14'),
       (4, '11번 게시글', '11번 내용', '2023-08-16 21:07:17'),
       (4, '12번 게시글', '12번 내용', '2023-08-16 21:07:20'),
       (5, '13번 게시글', '13번 내용', '2023-08-16 21:07:25'),
       (5, '14번 게시글', '14번 내용', '2023-08-16 21:07:30'),
       (5, '15번 게시글', '15번 내용', '2023-08-16 21:07:31');

SET foreign_key_checks = 1;