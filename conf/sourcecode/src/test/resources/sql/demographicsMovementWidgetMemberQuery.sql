SELECT y.nmemid
FROM   (WITH refinfo
              AS (SELECT Last_day(Add_months( cycleenddate, -2 )) + 1 AS
                         PrevRefMonth,
                         Last_day(Add_months( cycleenddate, -1 )) + 1 AS
                         CycleEndMonth
                  FROM   co_rundates) SELECT nmemid
                                      FROM   co_memexpensesbymth a,
                                             refinfo b
                                      WHERE  a.curflag = 'Y'
                                             AND a.refdate = b.prevrefmonth
         MINUS
         SELECT nmemid
         FROM   co_memexpensesbymth a,
                refinfo b
         WHERE  a.curflag = 'Y'
                AND a.refdate = b.cycleendmonth) x
        join mv_301_expensesbymem y
          ON x.nmemid = y.nmemid
WHERE  1 = 1
       AND y.nmemid IN(SELECT nmemid
                       FROM   co_members a
                       WHERE  1 = 1
                              AND ( Floor(a.age) BETWEEN 10 AND 50 ))