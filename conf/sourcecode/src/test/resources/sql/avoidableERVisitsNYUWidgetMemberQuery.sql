SELECT
	nmemid
FROM
	co_ervisitclaims_op_nyu a
WHERE
	1 = 1
	AND a.servicedate BETWEEN:startDate and:endDate
	AND a.nLvl1Id =:a_nLvl1Id
	AND a.nLvl2Id =:a_nLvl2Id
	AND a.nLvl3Id IN(
		:a_nLvl3Id
	)
	AND a.nLvl4Id =:a_nLvl4Id
	AND a.PC_TREAT > 0
GROUP BY
	a.nmemid