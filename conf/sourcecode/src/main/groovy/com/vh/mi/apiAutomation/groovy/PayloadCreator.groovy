package com.vh.mi.apiAutomation.groovy

import com.vh.mi.apiAutomation.groovy.jsonObjects.Payload
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamnsInpatientTrend
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsAvoidableERVisitNYU
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsConditionDrivingAdmission
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsCoordinationOfCare
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsDemographics
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsERvsUrgentCare
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsErVisitByFrequency
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsErVisitUtilization
import com.vh.mi.apiAutomation.groovy.jsonObjects.FilterOptions
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsForTopTenERVisit
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsMedicalCost
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsForTopTenNonPBMDrugs
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsPharmacyAnalysis
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsPqiPdiAcscAdmission
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsPredictedCost
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsRiskDistribution
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsTop10DiagnosisGrouperER
import com.vh.mi.apiAutomation.groovy.jsonObjects.chartParams.ChartParamsTopTenDrugs
import com.vh.mi.automation.api.config.ExecutionContext
import groovy.json.JsonBuilder
import com.vh.mi.apiAutomation.groovy.jsonObjects.BusinessLevels
import com.vh.mi.apiAutomation.groovy.jsonObjects.BusinessMap
import com.vh.mi.apiAutomation.groovy.jsonObjects.DomainParams

/**
 * Created by i82325 on 11/9/2017.
 */
class PayloadCreator {
    protected final ExecutionContext context

    PayloadCreator() {
        context = ExecutionContext.forEnvironment(System.getProperty("environment"))

    }

    private ArrayList getLevelWisePayload(levelNumbers) {
        def businessMap
        int businessLevel = 1
        def array = new ArrayList<>()
        for (def i = 6; i > 0; i--) {
            array.add({})
        }
        if (levelNumbers[0] != null || levelNumbers[0] != '') {
            for (def arg : levelNumbers) {
                businessMap = new HashMap<String, BusinessMap>()
                for (String levelNumber : arg) {
                    Map level = new APIConfigBuilder().getLevel(levelNumber)
                    def unitLevel = new BusinessMap(numericId: level.get("nlvlId"), id: level.get("nlvlId"), nlevelId: level.get("nlvlId"),
                            levelId: level.get("levelId"), selected: false, description: level.get("levelDesc"), strId: level.get("levelId"), levelDesc: level.get("levelDesc"))
                    businessMap.put(level.get("nlvlId"), unitLevel)
                }
                array.set(businessLevel - 1, businessMap)
                businessLevel++
            }

        }
        return array
    }

    int lowestLevelSelectedGetter(ArrayList alist) {
        def count = 0
        for (HashMap arg : alist) {
            if (arg.isEmpty()) {
                count = count
            } else {
                count++
            }
        }
        return count
    }

    JsonBuilder rxWidgetPayload(levelNumbers, analysisPeriod, reportingBy, analyzeBy, analyticOption, cohortId) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(businessMaps: alist, anyKeySelected: false, lowestBusinessLevelKeySelected: lowestLevelSelected, partitionLevel: null,
                partitioned: false, partitionKey: null, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterOptionsAnalyzeBy = new FilterOptions(filterName: 'analyzeBy', filterValue: (analyzeBy == '' ? null : analyzeBy), operatorSelected: 'SINGLE')
        def filterOptionsAnalyticOption = new FilterOptions(filterName: 'analyticOption', filterValue: (analyticOption == '' ? null : analyticOption), operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptionsAnalyzeBy, filterOptionsAnalyticOption], domainParams: domainParams)
        return jsonBuilder
    }


    JsonBuilder inpatientTrendPayload(levelNumbers, analysisPreiod, analyticOption) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(businessMaps: alist, anyKeySelected: false, lowestBusinessLevelKeySelected: lowestLevelSelected, partitionLevel: null,
                partitioned: false, partitionKey: null, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, businessLevels: businessLevels, analysisPeriod: analysisPreiod)
        def filterOptionsAnalyticOptions = new FilterOptions(filterName: 'analyticOption', filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterOptionsAnalyticOptions], domainParams: domainParams)
    }

    JsonBuilder erVisitbyFrequencyWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, analyticOption, analyzeBy, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def filterAnaOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, filterAnaBy], domainParams: domainParams)
    }

    JsonBuilder topConditionForERVisitWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, erBand, populationSelection, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterAnaBy = new FilterOptions(filterName: "erBand", filterValue: erBand, operatorSelected: 'SINGLE')
        def filterAnaOptions = new FilterOptions(filterName: "populationSelection", filterValue: populationSelection, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, filterAnaBy], domainParams: domainParams)
    }

    JsonBuilder erVisitUtilizationWidgetPayload(levelNumbers, analysisPeriod, reportingBy, analyzeBy, analyticOption, cohortId) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(businessMaps: alist, anyKeySelected: false, lowestBusinessLevelKeySelected: lowestLevelSelected, partitionLevel: null,
                partitioned: false, partitionKey: null, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterOptionsAnalyzeBy = new FilterOptions(filterName: 'analyzeBy', filterValue: (analyzeBy == '' ? null : analyzeBy), operatorSelected: 'SINGLE')
        def filterOptionsAnalyticOption = new FilterOptions(filterName: 'analyticOption', filterValue: (analyticOption == '' ? null : analyticOption), operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptionsAnalyzeBy, filterOptionsAnalyticOption], domainParams: domainParams)
        return jsonBuilder
    }

    JsonBuilder demographicsWigdetPayload(levelNumbers, analysisPeriod, reportingBy, analyticOption, cohortId) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(businessMaps: alist, anyKeySelected: true, lowestBusinessLevelKeySelected: lowestLevelSelected, partitionLevel: null,
                partitioned: false, partitionKey: null, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterOptionsAnalyticOption = new FilterOptions(filterName: 'analyticOption', filterValue: (analyticOption == '' ? null : analyticOption), operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptionsAnalyticOption], domainParams: domainParams)
        return jsonBuilder
    }

    JsonBuilder riskScoreHeatMapWidgetPayload(levelNumbers, dxcgModel, analysisPeriod, reportingBy, cohortId) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: alist, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterOptionsDxcgModel = new FilterOptions(filterName: (dxcgModel == '' ? 'dxcgModel' : 'analyticOption'), filterValue: (dxcgModel == '' ? null : dxcgModel), operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptionsDxcgModel], domainParams: domainParams)
        return jsonBuilder
    }

    JsonBuilder coordinationOfCareWidgetPayload(levelNumbers, analysisPeriod, reportingBy, cohortId, providerType) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: alist, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def jsonBuilder = new JsonBuilder()
        def filterOptions = new FilterOptions(filterName: 'providerType', filterValue: providerType, operatorSelected: 'SINGLE')
        jsonBuilder(filterOptions: [filterOptions], domainParams: domainParams)
        return jsonBuilder
    }
    JsonBuilder coordinationOfCareWidgetPayloadSFW(levelNumbers, analysisPeriod, reportingBy, cohortId, providerType) {
        def appId = '982-007'
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: alist, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def jsonBuilder = new JsonBuilder()
        def filterOptions = new FilterOptions(filterName: 'providerType', filterValue: providerType, operatorSelected: 'SINGLE')
        jsonBuilder(filterOptions: [filterOptions], domainParams: domainParams)
        return jsonBuilder
    }

    JsonBuilder predictedCostWidgetPayload(levelNumbers, analysisPeriod, reportingBy, analyticOption, cohortId) {
        def appId = context.getAppId()
        ArrayList alist = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(alist)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: alist, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterOptions = new FilterOptions(filterName: "AnalyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptions], domainParams: domainParams)
        return jsonBuilder
    }

    JsonBuilder topTenDrugsWidgetPayload(levelNumbers, analysisPeriod, reportingBy, analyticOption, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList);
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptions], domainParams: domainParams)
        return jsonBuilder
    }

    JsonBuilder topTenDrugsWidgetPayloadPOS(levelNumbers, analysisPeriod, reportingBy, analyticOption, drugs) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList);
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(analysisPeriod: analysisPeriod, appId: appId, reportingBy: reportingBy, businessLevels: businessLevels)
        def filterOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(filterOptions: [filterOptions], domainParams: domainParams, payload: drugs)
        return jsonBuilder
    }

    JsonBuilder costTrendByClaimTypeWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, analyticOption, claimType, analyzeBy, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterClaimtype = new FilterOptions(filterName: "claimType", filterValue: claimType, operatorSelected: 'SINGLE')
        def filterAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def filterAnaOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, filterAnaBy, filterClaimtype], domainParams: domainParams)
    }

    JsonBuilder medicalCostDistributionWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, amountOptions, analyzeBy, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def filterAnaOptions = new FilterOptions(filterName: "amount", filterValue: amountOptions, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, filterAnaBy], domainParams: domainParams)
    }

    JsonBuilder medicalCostMemberQueryPayload(JsonBuilder widgetPayload , chartParmas) {
        def jsonBuilder = new JsonBuilder()
        def chartParam = new ChartParamsMedicalCost(medicalCode: chartParmas)
        return jsonBuilder(widgetOptions: widgetPayload.getContent(), chartParams: chartParam)
    }

    JsonBuilder top10DiagnosisGrouperByErVisitWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, analyticOption, analyzeBy, erVisitType, erBand, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterErVisitType = new FilterOptions(filterName: "erVisitType", filterValue: erVisitType, operatorSelected: 'SINGLE')
        def filterErBand = new FilterOptions(filterName: "erBand", filterValue: erBand, operatorSelected: 'SINGLE')
        def filterAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def filterAnaOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, filterAnaBy, filterErVisitType, filterErBand], domainParams: domainParams)
    }

    JsonBuilder top10DiagnosisGrouperByErVisitWidgetWithDiagListPayLoad(levelNumbers, analysisPeriod, reportingBy, analyticOption, analyzeBy, erVisitType, erBand, diagList) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels)
        def filterErVisitType = new FilterOptions(filterName: "erVisitType", filterValue: erVisitType, operatorSelected: 'SINGLE')
        def filterErBand = new FilterOptions(filterName: "erBand", filterValue: erBand, operatorSelected: 'SINGLE')
        def filterAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def filterAnaOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, filterAnaBy, filterErVisitType, filterErBand], domainParams: domainParams, payload: new Payload(diagList: diagList))
    }

    JsonBuilder erVsUrgentCareWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, analyticOption, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterAnaOptions = new FilterOptions(filterName: "analyticOption", filterValue: analyticOption, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions], domainParams: domainParams)
    }

    JsonBuilder avoidableERVisitNYUWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, analyzeBy, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaBy], domainParams: domainParams)
    }

    JsonBuilder conditionDrivingAdmissionWidgetPayload(levelNumbers, analyzeBy, cohortId) {
        def appid = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appid, businessLevels: businessLevels, cohortId: cohortId)
        def filerAnaBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filerAnaBy], domainParams: domainParams)
    }

    JsonBuilder pqiPdiAcscAdmissionWidgetPayload(levelNumbers, analysisPeriod, cohortId, amount, analyzeBy) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)

        def domainParams = new DomainParams(appId: appId, businessLevels: businessLevels, cohortId: cohortId, analysisPeriod: analysisPeriod)
        def filterAmount = new FilterOptions(filterName: "amount", filterValue: amount, operatorSelected: 'SINGLE')
        def filterAnalyzeBy = new FilterOptions(filterName: "analyzeBy", filterValue: analyzeBy, operatorSelected: 'SINGLE')

        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAmount, filterAnalyzeBy], domainParams: domainParams)
    }

    JsonBuilder posForTopNonPBMWidgetPayLoad(levelNumbers, analysisPeriod, reportingBy, drugType, drugCode, cohortId) {
        def appId = context.getAppId()
        ArrayList arrayList = getLevelWisePayload(levelNumbers)
        def lowestLevelSelected = lowestLevelSelectedGetter(arrayList)
        def businessLevels = new BusinessLevels(partitioned: false, anyKeySelected: false, partitionKey: null, partitionLevel: null,
                businessMaps: arrayList, lowestBusinessLevelKeySelected: lowestLevelSelected, maxLevel: 4)
        def domainParams = new DomainParams(appId: appId, analysisPeriod: analysisPeriod, reportingBy: reportingBy, businessLevels: businessLevels, cohortId: cohortId)
        def filterAnaOptions = new FilterOptions(filterName: "procType", filterValue: drugType, operatorSelected: 'SINGLE')
        def drugCodeOption = new FilterOptions(filterName: "drugCode", filterValue: drugCode, operatorSelected: 'SINGLE')
        def jsonBuilder = new JsonBuilder()
        return jsonBuilder(filterOptions: [filterAnaOptions, drugCodeOption], domainParams: domainParams)
    }

    JsonBuilder pqiPdiAcscAdmissionMemberQueryPayload(JsonBuilder widgetPayload, pqipdi) {
        def jsonBuilder = new JsonBuilder()
        def chartParams = new ChartParamsPqiPdiAcscAdmission(pqipdi: pqipdi)
        return jsonBuilder(widgetOptions: widgetPayload.getContent(), chartParams: chartParams)
    }

    JsonBuilder conditionDrivingAdmissionMemberQueryWidgetPayload(JsonBuilder widgetPayload, rcccode) {
        def jsonBuilder = new JsonBuilder()
        def chartParam = new ChartParamsConditionDrivingAdmission(rcccode: rcccode)
        return jsonBuilder(widgetOptions: widgetPayload.content, chartParams: chartParam)
    }

    JsonBuilder erVisitUtilizationMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, countyFips) {
        def chartParams = new ChartParamsErVisitUtilization(countyfips: countyFips)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder pharmacyAnalysisMemberQueryPayload(JsonBuilder widgetPayload, drugId, drugType) {
        def chartParams = new ChartParamsPharmacyAnalysis(drugCode: drugId, drugType: drugType)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder inpatientTrendMemberQueryPayload(JsonBuilder widgetPayload, date, metrics) {
        def chartParam = new ChartParamnsInpatientTrend(date: date, metrics: metrics)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), chartParams: chartParam)
        return jsonBuilder
    }

    JsonBuilder riskScoreHeatMapMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, countyfips, modelNo) {
        def chartParams = new ChartParamsRiskDistribution(countyfips: countyfips, modelNo: modelNo)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder demographicsMovementMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, category, termed, value) {
        def chartParams = new ChartParamsDemographics(category: category, termed: termed, value: value)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder coordinationOfCareMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, countyfips) {
        def chartParams = new ChartParamsCoordinationOfCare(countyfips: countyfips)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder predictedCostMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, riskRangeName) {
        def chartParams = new ChartParamsPredictedCost(riskrangename: riskRangeName)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder topTenDrugsMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, nsrcprocdrugcode) {
        def chartParams = new ChartParamsTopTenDrugs(nsrcprocdrugcode: nsrcprocdrugcode)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder erVisitByFrequencyMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, category, name) {
        def chartParams = new ChartParamsErVisitByFrequency(category: category, name: name)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder top10DiagnosisGrouperByErVisitMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, diagnosisGrouperCode, erVisitType, erBand) {
        def chartParams = new ChartParamsTop10DiagnosisGrouperER(diagcode: diagnosisGrouperCode, erBand: erBand, erVisitType: erVisitType)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder erVsUrgentCareWidgetMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, metric, serviceDate) {
        def chartParams = new ChartParamsERvsUrgentCare(metric: metric, serviceDate: serviceDate)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder avoidableERVisitNYUWidgetMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, erVisitType) {
        def chartParams = new ChartParamsAvoidableERVisitNYU(erVisitType: erVisitType)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder topConditionForERVisitWidgetMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, accCode, rhccCode) {
        def chartParams = new ChartParamsForTopTenERVisit(accCode: accCode, rhccCode: rhccCode)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

    JsonBuilder posForTopNonPBMWidgetMemberQueryPayload(JsonBuilder widgetPayload, dataUrl, source, drugCode,posRxCode) {
        def chartParams = new ChartParamsForTopTenNonPBMDrugs(drugCode: drugCode,posRxCode: posRxCode)
        def jsonBuilder = new JsonBuilder()
        jsonBuilder(widgetOptions: widgetPayload.getContent(), dataUrl: dataUrl, source: source, chartParams: chartParams)
        return jsonBuilder
    }

}
