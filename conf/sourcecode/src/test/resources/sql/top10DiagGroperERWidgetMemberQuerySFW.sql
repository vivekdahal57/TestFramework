SELECT
	DISTINCT a.nmemid
FROM
	(
		SELECT
			b.er_id AS er_id,
			b.diagcode AS diagcode,
			b.nmemid AS nmemid,
			Max( rank_erid ) OVER(
				PARTITION BY b.nmemid
			) AS noofervisit
		FROM
			(
				SELECT
					a.er_id AS er_id,
					a.diagcode AS diagcode,
					a.nmemid AS nmemid,
					DENSE_RANK() OVER(
						PARTITION BY a.nmemid
					ORDER BY
						ER_ID
					) AS rank_erid
				FROM
					co_ervisitclaims a join co_sfw_124339 sfw on
					a.nlvl1id = sfw.nlvl1id
					and a.nlvl2id = sfw.nlvl2id
					and a.nlvl3id = sfw.nlvl3id
					and a.nlvl4id = sfw.nlvl4id
				WHERE
					1 = 1
					AND a.servicedate BETWEEN:startDate and:endDate
					AND a.nLvl1Id =:a_nLvl1Id
					AND a.nLvl2Id =:a_nLvl2Id
					AND a.nLvl3Id IN(
						:a_nLvl3Id
					)
					AND a.nLvl4Id =:a_nLvl4Id
			) b
	) a
WHERE
	1 = 1
	AND a.diagcode =:diagCode