SELECT
    adm.nmemid
FROM
    co_admissions adm JOIN co_sfw_124339 sfw
        ON adm.nlvl1id = sfw.nlvl1id
    AND adm.nlvl2id = sfw.nlvl2id
    AND adm.nlvl3id = sfw.nlvl3id
    AND adm.nlvl4id = sfw.nlvl4id
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
    AND adm.nLvl1Id = : adm_nLvl1Id
    AND adm.nLvl2Id = : adm_nLvl2Id
    AND adm.nLvl3Id IN(
        : adm_nLvl3Id
    )
    AND adm.nLvl4Id = : adm_nLvl4Id