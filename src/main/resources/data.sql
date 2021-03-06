-- TO_DO default값 setting

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(1, '가사일', SYSDATE, null, true, '가사일');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(2, '설겆이', SYSDATE, null, true, '설겆이 @1');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(3, '청소', SYSDATE, null, true, '청소 @1');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(4, '빨래', SYSDATE, null, true, '빨래 @1');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(5, '숙제', SYSDATE, null, false, '숙제');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(6, '여행계획', SYSDATE, null, false, '여행계획 @5');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(7, '게임', SYSDATE, null, true, '게임');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(8, 'TV시청', SYSDATE, null, true, 'TV시청');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(9, '영화관람', SYSDATE, null, true, '영화관람');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(10, '데이트', SYSDATE, null, true, '데이트');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(11, '취업', SYSDATE, null, true, '취업');

INSERT INTO TO_DO(ID, WHAT_TO_DO, REGISTER_DATE, LAST_UPDATE_DATE, IS_PROGRESS, WHAT_TO_DO_WITH_LINK)
VALUES(12, '엔빵', SYSDATE, null, true, '엔빵');

-- TO_DO default값 setting END


-- PARENT_MAPPING default값 setting

INSERT INTO PARENT_MAPPING(TO_DO_ID, PARENT_ID, IS_PROGRESS)
VALUES(2, 1, true);

INSERT INTO PARENT_MAPPING(TO_DO_ID, PARENT_ID, IS_PROGRESS)
VALUES(3, 1, true);

INSERT INTO PARENT_MAPPING(TO_DO_ID, PARENT_ID, IS_PROGRESS)
VALUES(4, 1, true);

INSERT INTO PARENT_MAPPING(TO_DO_ID, PARENT_ID, IS_PROGRESS)
VALUES(6, 5, false);

-- PARENT_MAPPING default값 setting END