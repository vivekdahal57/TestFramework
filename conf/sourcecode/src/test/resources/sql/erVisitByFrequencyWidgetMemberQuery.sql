select
	nmemid
from
	co_ervisitclaims a
JOIN(
		SELECT
			/*+ NO_MERGE */
			nLVL1ID,
			nLVL2ID,
			nLVL3ID,
			nLVL4ID,
			MIN( STARTDATE_CCY ) STARTDATE_CP,
			MAX( ENDDATE_CCY ) ENDDATE_CP
		from
			CO_CURCONTRACTDETALL
		GROUP BY
			nLVL1ID,
			nLVL2ID,
			nLVL3ID,
			nLVL4ID
	) p on
	1 = 1
	AND a.nlvl1id = p.nlvl1id
	AND a.nlvl2id = p.nlvl2id
	AND a.nlvl3id = p.nlvl3id
	AND a.nlvl4id = p.nlvl4id
where
	a.visittype =:visitType
	AND a.nLvl1Id =:a_nLvl1Id
	AND a.nLvl2Id =:a_nLvl2Id
	AND a.servicedate BETWEEN p.STARTDATE_CP AND p.ENDDATE_CP
group by
	a.nmemid
having
	COUNT( distinct a.er_id )>:countVal