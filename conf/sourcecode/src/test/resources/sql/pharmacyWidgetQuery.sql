SELECT
    DISTINCT a.nmemid
FROM
    co_claims a JOIN co_analysisdates c
        ON 1 = 1
    AND a.nlvl1id = c.nlvl1id
    AND a.nlvl2id = c.nlvl2id
    AND a.nlvl3id = c.nlvl3id
    AND a.nlvl4id = c.nlvl4id
    AND a.servicedate >= c.startdate
    AND a.servicedate <= c.enddate
    AND c.analysistype = : analysisType
WHERE
    a.clmtype = 'RX'
    AND a.nprocdrugcode = : drugCode
    AND a.drugtypedesc = : drugTypeDesc
    AND a.nmemid IN(
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