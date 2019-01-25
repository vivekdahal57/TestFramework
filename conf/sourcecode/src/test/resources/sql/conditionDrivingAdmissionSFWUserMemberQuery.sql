SELECT
    DISTINCT y.nmemid
FROM
    dx_riskdriver_ccgroup y ,
    dx_riskdriver_riskwt w ,
    dx_output m JOIN co_sfw_124339 sfw
        ON m.nlvl1id = sfw.nlvl1id
    AND m.nlvl2id = sfw.nlvl2id
    AND m.nlvl3id = sfw.nlvl3id
    AND m.nlvl4id = sfw.nlvl4id
WHERE
    y.model_no = 71
    AND m.nid = y.nmemid
    AND y.model_no = w.model_no
    AND y.cccode = w.cccode
    AND m.nid IN(
        (
            SELECT
                nid
            FROM
                dx_output z
            WHERE
                1 = 1
                AND z.pred_4 BETWEEN(
                    SELECT
                        DISTINCT percentile_cont(0.99) WITHIN GROUP(
                        ORDER BY
                            pred_4 ASC
                        ) OVER() AS rrspctlower
                    FROM
                        mv_330_arihist
                    WHERE
                        cycleenddate =(
                            SELECT
                                cycleenddate
                            FROM
                                co_rundates
                        )
                ) AND(
                    SELECT
                        DISTINCT percentile_cont(1.0) WITHIN GROUP(
                        ORDER BY
                            pred_4 ASC
                        ) OVER() AS rrspctupper
                    FROM
                        mv_330_arihist
                    WHERE
                        cycleenddate =(
                            SELECT
                                cycleEndDate
                            FROM
                                co_runDates
                        )
                )
        )
    )
    AND w.rcccode = : rccCode
    AND m.nLvl1Id = : m_nLvl1Id
    AND m.nLvl2Id = : m_nLvl2Id
    AND m.nLvl3Id IN(
        : m_nLvl3Id
    )
    AND m.nLvl4Id = : m_nLvl4Id