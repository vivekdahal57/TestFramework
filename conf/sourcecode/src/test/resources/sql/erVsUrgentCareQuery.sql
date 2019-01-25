SELECT
	nmemid
FROM
	co_ervisitclaims a
WHERE
	1 = 1
	AND visittype IN(
		:visitType
	)
	AND To_Char(
		servicedate,
		'YYYYMM'
	)=:serviceDate
	AND a.nLvl1Id =:a_nLvl1Id
	AND a.nLvl2Id =:a_nLvl2Id
	AND a.nLvl3Id IN(
		:a_nLvl3Id
	)
	AND a.nLvl4Id =:a_nLvl4Id