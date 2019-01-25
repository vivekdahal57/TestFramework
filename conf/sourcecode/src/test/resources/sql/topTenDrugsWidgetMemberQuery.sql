SELECT
    DISTINCT(a.nmemid)
FROM
    co_Claims a JOIN co_analysisdates b
        ON 1 = 1
    AND a.nlvl1id = b.nlvl1id
    AND a.nlvl2id = b.nlvl2id
    AND a.nlvl3id = b.nlvl3id
    AND a.nlvl4id = b.nlvl4id
    AND a.servicedate >= b.startdate
    AND a.servicedate <= b.enddate
    AND b.analysistype = : analysisType
WHERE
    1 = 1
    AND a.clmtype = 'MED'
    AND a.nsrcprocdrugcode = : nSrcProcDrugCode
    AND a.nLvl1Id = : a_nLvl1Id
    AND a.nLvl2Id = : a_nLvl2Id
    AND a.nLvl3Id IN(
        : a_nLvl3Id
    )
    AND a.nLvl4Id = : a_nLvl4Id