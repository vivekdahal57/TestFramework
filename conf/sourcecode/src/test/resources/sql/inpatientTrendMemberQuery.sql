SELECT
    adm.nmemid
FROM
    co_admissions adm
WHERE
    1 = 1
    AND adm.eligreadmission_yn = 1
    AND adm.dischrgdate BETWEEN TO_DATE(
        '2016/2/1' ,
        'YYYY/MM/DD'
    ) AND TO_DATE(
        '2016/2/29' ,
        'YYYY/MM/DD'
    )
    AND adm.nLvl1Id IN(
        : adm_nLvl1Id
    )
    AND adm.nLvl2Id = : adm_nLvl2Id
    AND adm.nLvl3Id = : adm_nLvl3Id