SELECT DISTINCT nmemid
FROM   co_claims a
WHERE  1 = 1
       AND a.paiddate BETWEEN :startDate AND :endDate
       AND a.nlvl1id = :a_nLvl1Id
       AND a.nlvl2id = :a_nLvl2Id
       AND a.nlvl3id IN ( :a_nLvl3Id )
       AND a.nlvl4id = :a_nLvl4Id
       AND a.clmtype = 'MED'
       AND a.nsrcprocdrugcode = :nSrcProcDrugCode
       AND a.posrxcode = :posRxCode