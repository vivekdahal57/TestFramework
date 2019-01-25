SELECT DISTINCT
   s.nmemid
FROM
   co_hcci_services s
   join
      co_sfw_124339 sfw
      on s.nlvl1id = sfw.nlvl1id
      and s.nlvl2id = sfw.nlvl2id
      and s.nlvl3id = sfw.nlvl3id
      and s.nlvl4id = sfw.nlvl4id
   JOIN
      co_analysisdates c
      on 1 = 1
      AND s.nlvl1id = c.nlvl1id
      AND s.nlvl2id = c.nlvl2id
      AND s.nlvl3id = c.nlvl3id
      AND s.nlvl4id = c.nlvl4id
      AND s.end_date >= c.startdate
      AND s.end_date <= c.enddate
      AND c.analysistype = 'F'
WHERE
   s.clmtype = 'MED'
   AND s.sub_det_service_cat_code = :subDetServiceCatCode