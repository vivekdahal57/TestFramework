SELECT DISTINCT y.nmemid AS nmemid
FROM dx_riskdriver_ccgroup y, dx_riskdriver_riskwt w, (SELECT *
                                                       FROM dx_output m
                                                       WHERE 1 = 1 AND
                                                             m.nLvl1Id =
                                                             :m_nLvl1Id AND
                                                             m.nLvl2Id =
                                                             :m_nLvl2Id AND
                                                             m.nLvl3Id IN
                                                             (:m_nLvl3Id) AND
                                                             m.nLvl4Id =
                                                             :m_nLvl4Id) m
WHERE y.model_no = :modelNo AND m.nid = y.nmemid AND y.model_no = w.model_no AND
      y.cccode = w.cccode AND m.nid IN (SELECT nid nMemID
                                        FROM dx_output z
                                        WHERE 1 = 1 AND
                                              z.pred_13 BETWEEN :pctLower AND :pctUpper)
      AND w.acccode = :accCode AND w.rcccode = :rhccCode