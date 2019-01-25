SELECT y.nmemid
FROM   (WITH refinfo
              AS (SELECT Last_day(Add_months( cycleenddate, -2 )) + 1 AS
                         PrevRefMonth,
                         Last_day(Add_months( cycleenddate, -1 )) + 1 AS
                         CycleEndMonth
                  FROM   co_rundates) SELECT nmemid
                                      FROM   co_memexpensesbymth a
                                             join co_sfw_124339 sfw
                                               ON a.nlvl1id = sfw.nlvl1id
                                                  AND a.nlvl2id = sfw.nlvl2id
                                                  AND a.nlvl3id = sfw.nlvl3id
                                                  AND a.nlvl4id = sfw.nlvl4id,
                                             refinfo b
                                      WHERE  a.curflag = 'Y'
                                             AND a.refdate = b.prevrefmonth
         MINUS
         SELECT nmemid
         FROM   co_memexpensesbymth a
                join co_sfw_124339 sfw
                  ON a.nlvl1id = sfw.nlvl1id
                     AND a.nlvl2id = sfw.nlvl2id
                     AND a.nlvl3id = sfw.nlvl3id
                     AND a.nlvl4id = sfw.nlvl4id,
                refinfo b
         WHERE  a.curflag = 'Y'
                AND a.refdate = b.cycleendmonth) x
        join mv_301_expensesbymem y
          ON x.nmemid = y.nmemid
        join co_sfw_124339 sfw
          ON y.nlvl1id = sfw.nlvl1id
             AND y.nlvl2id = sfw.nlvl2id
             AND y.nlvl3id = sfw.nlvl3id
             AND y.nlvl4id = sfw.nlvl4id
WHERE  1 = 1
       AND y.nmemid IN (SELECT COHORTMEMBER.nmemid
                        FROM   co_members COHORTMEMBER
                               join (SELECT nmemid
                                     FROM   co_members a
                                     WHERE  1 = 1
                                            AND ( Floor(a.age) > 10 )) b
                                 ON COHORTMEMBER.nmemid = b.nmemid
                        WHERE  1 = 1
                               AND COHORTMEMBER.nlvl1id = :COHORTMEMBER_nLvl1Id
                               AND COHORTMEMBER.nlvl2id = :COHORTMEMBER_nLvl2Id
                               AND COHORTMEMBER.nlvl3id = :COHORTMEMBER_nLvl3Id
                               AND COHORTMEMBER.nlvl4id = :COHORTMEMBER_nLvl4Id)