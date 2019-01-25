SELECT
	nmemid
FROM
	co_ervisitclaims a join co_sfw_124339 sfw on
	a.nlvl1id = sfw.nlvl1id
	and a.nlvl2id = sfw.nlvl2id
	and a.nlvl3id = sfw.nlvl3id
	and a.nlvl4id = sfw.nlvl4id
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