SELECT
    DISTINCT adm.nmemid
FROM
    co_admissions adm JOIN co_sfw_124339 sfw
        ON adm.nlvl1id = sfw.nlvl1id
    AND adm.nlvl2id = sfw.nlvl2id
    AND adm.nlvl3id = sfw.nlvl3id
    AND adm.nlvl4id = sfw.nlvl4id JOIN co_ahrq_indicators ahrq
        ON adm.admit_id = ahrq.admit_id JOIN co_analysisdates c
        ON 1 = 1
    AND c.nlvl1id = adm.nlvl1id
    AND c.nlvl2id = adm.nlvl2id
    AND c.nlvl3id = adm.nlvl3id
    AND c.nlvl4id = adm.nlvl4id
    AND adm.dischrgdate >= c.startdate
    AND adm.dischrgdate <= c.enddate
    AND c.analysistype = 'F'
WHERE
    adm.ahrqmem_yn = 'Y'
    AND ahrq.pqi1_yn = 'Y'
    AND adm.nmemid IN(
        SELECT
            nMemId
        FROM
            co_members a
        WHERE
            1 = 1
            AND(
                FLOOR( a.age ) BETWEEN 10 AND 50
            )
    )