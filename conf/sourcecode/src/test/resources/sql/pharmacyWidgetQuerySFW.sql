SELECT DISTINCT
   a.nmemid
FROM
   co_claims a
   join
      co_sfw_124339 sfw
      on a.nlvl1id = sfw.nlvl1id
      and a.nlvl2id = sfw.nlvl2id
      and a.nlvl3id = sfw.nlvl3id
      and a.nlvl4id = sfw.nlvl4id
   JOIN
      co_analysisdates c
      on 1 = 1
      AND a.nlvl1id = c.nlvl1id
      AND a.nlvl2id = c.nlvl2id
      AND a.nlvl3id = c.nlvl3id
      AND a.nlvl4id = c.nlvl4id
      AND a.servicedate >= c.startdate
      AND a.servicedate <= c.enddate
      AND c.analysistype = :analysisType
WHERE
   a.clmtype = 'RX'
   AND a.nprocdrugcode = :drugCode
   AND a.drugtypedesc = :drugTypeDesc