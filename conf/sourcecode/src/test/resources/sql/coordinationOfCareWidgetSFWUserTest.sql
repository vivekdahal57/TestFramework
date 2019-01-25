SELECT
    a.nmemid
FROM
    (
        SELECT
            DISTINCT a.nmemid
        FROM
            co_claims a JOIN CO_SFW_SCHEME_41224 sfw
                ON a.nlvl1id = sfw.nlvl1id
            AND a.nlvl2id = sfw.nlvl2id
            AND a.nlvl3id = sfw.nlvl3id
            AND a.nlvl4id = sfw.nlvl4id JOIN(
                SELECT
                    /*+ NO_MERGE */
                    nLVL1ID ,
                    nLVL2ID ,
                    nLVL3ID ,
                    nLVL4ID ,
                    MIN(STARTDATE_CCY) STARTDATE_CP ,
                    MAX(ENDDATE_CCY) ENDDATE_CP
                FROM
                    CO_CURCONTRACTDETALL
                GROUP BY
                    nLVL1ID ,
                    nLVL2ID ,
                    nLVL3ID ,
                    nLVL4ID
            ) p
                ON 1 = 1
            AND a.nlvl1id = p.nlvl1id
            AND a.nlvl2id = p.nlvl2id
            AND a.nlvl3id = p.nlvl3id
            AND a.nlvl4id = p.nlvl4id
            AND a.servicedate BETWEEN p.startdate_cp AND p.enddate_cp
        WHERE
            a.clmtype = 'MED'
    ) a JOIN co_members b
        ON a.nmemid = b.nmemid JOIN co_uscities c
        ON substr(
        b.zip ,
        1 ,
        5
    ) = c.zip
WHERE
    1 = 1
    AND c.statecode IN(
        'AL' ,
        'AK' ,
        'AZ' ,
        'AR' ,
        'CA' ,
        'CO' ,
        'CT' ,
        'DE' ,
        'DC' ,
        'FL' ,
        'GA' ,
        'HI' ,
        'ID' ,
        'IL' ,
        'IN' ,
        'IA' ,
        'KS' ,
        'KY' ,
        'LA' ,
        'ME' ,
        'MD' ,
        'MA' ,
        'MI' ,
        'MN' ,
        'MS' ,
        'MO' ,
        'MT' ,
        'NE' ,
        'NV' ,
        'NH' ,
        'NJ' ,
        'NM' ,
        'NY' ,
        'NC' ,
        'ND' ,
        'OH' ,
        'OK' ,
        'OR' ,
        'PA' ,
        'RI' ,
        'SC' ,
        'SD' ,
        'TN' ,
        'TX' ,
        'UT' ,
        'VT' ,
        'VA' ,
        'WA' ,
        'WV' ,
        'WI' ,
        'WY'
    )
    AND c.countyfips = : countyfips