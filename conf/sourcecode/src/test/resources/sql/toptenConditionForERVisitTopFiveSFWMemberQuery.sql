SELECT DISTINCT y.nmemid AS nmemid
FROM   dx_riskdriver_ccgroup y,
       dx_riskdriver_riskwt w,
       (SELECT *
        FROM   dx_output m
               JOIN co_sfw_124339 sfw
                 ON m.nlvl1id = sfw.nlvl1id
                    AND m.nlvl2id = sfw.nlvl2id
                    AND m.nlvl3id = sfw.nlvl3id
                    AND m.nlvl4id = sfw.nlvl4id
        WHERE  1 = 1
               AND m.nlvl1id = :m_nLvl1Id
               AND m.nlvl2id = :m_nLvl2Id
               AND m.nlvl3id IN ( :m_nLvl3Id )
               AND m.nlvl4id = :m_nLvl4Id) m,
       (SELECT DISTINCT nmemid
        FROM   (SELECT b.er_id                    AS er_id,
                       b.nmemid                   AS nmemid,
                       b.memid,
                       Max(rank_erid)
                         OVER (
                           partition BY b.nmemid) AS noofervisit
                FROM   (SELECT a.er_id             AS er_id,
                               a.nmemid            AS nmemid,
                               a.memid,
                               Dense_rank()
                                 OVER (
                                   partition BY a.nmemid
                                   ORDER BY er_id) AS rank_erid
                        FROM   co_ervisitclaims a
                               JOIN co_sfw_124339 sfw
                                 ON a.nlvl1id = sfw.nlvl1id
                                    AND a.nlvl2id = sfw.nlvl2id
                                    AND a.nlvl3id = sfw.nlvl3id
                                    AND a.nlvl4id = sfw.nlvl4id
                        WHERE  1 = 1
                               AND a.servicedate BETWEEN :startDate AND :endDate
                               AND a.nlvl1id = :a_nLvl1Id
                               AND a.nlvl2id = :a_nLvl2Id
                               AND a.nlvl3id IN ( :a_nLvl3Id )
                               AND a.nlvl4id = :a_nLvl4Id) b) a
        WHERE  1 = 1)e
WHERE  y.model_no = :modelNo
       AND m.nid = y.nmemid
       AND m.nid = e.nmemid
       AND y.model_no = w.model_no
       AND y.cccode = w.cccode
       AND w.acccode = :accCode
       AND w.rcccode = :rhccCode