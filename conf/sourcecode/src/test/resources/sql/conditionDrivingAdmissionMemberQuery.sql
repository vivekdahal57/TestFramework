SELECT
    DISTINCT y.nmemid
FROM
    dx_riskdriver_ccgroup y ,
    dx_riskdriver_riskwt w ,
    dx_output m
WHERE
    y.model_no = 71
    AND m.nid = y.nmemid
    AND y.model_no = w.model_no
    AND y.cccode = w.cccode
    AND m.nid IN(
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